package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.exception.RuleViolationException;
import com.example.railwayticket.model.dto.request.booking.TicketBookingRequest;
import com.example.railwayticket.model.dto.request.booking.TicketConfirmationRequest;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketClassResponse;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketCoachResponse;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSearchResponse;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSeatResponse;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketTrainResponse;
import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Seat;
import com.example.railwayticket.model.entity.Ticket;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.model.entity.TrainJourney;
import com.example.railwayticket.model.entity.User;
import com.example.railwayticket.repository.TicketRepository;
import com.example.railwayticket.repository.TrainJourneyRepository;
import com.example.railwayticket.service.intface.BookingService;
import com.example.railwayticket.service.intface.TicketPrintService;
import com.example.railwayticket.utils.AppDateTimeUtils;
import com.example.railwayticket.utils.MiscUtils;
import com.example.railwayticket.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.railwayticket.constant.ApplicationConstants.BOOKING_VALIDITY;
import static com.example.railwayticket.constant.ApplicationConstants.SERVICE_CHARGE_PER_SEAT;
import static com.example.railwayticket.model.enumeration.SeatStatus.AVAILABLE;
import static com.example.railwayticket.model.enumeration.SeatStatus.BOOKED;
import static com.example.railwayticket.model.enumeration.SeatStatus.SOLD;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final TrainJourneyRepository trainJourneyRepository;
    private final TicketRepository ticketRepository;
    private final TicketPrintService ticketPrintService;

    @Override
    public TicketSearchResponse searchTicket(long fromStationId, long toStationId, LocalDate journeyDate) {
        List<TrainJourney> trainJourneys = trainJourneyRepository.searchSeatForJourney(
                fromStationId, toStationId, journeyDate
        );

        Map<Train, List<Map.Entry<Coach, List<TrainJourney>>>> trainCoachMap = organizeSeats(trainJourneys);

        return convertToResponse(trainCoachMap);
    }

    private Map<Train, List<Map.Entry<Coach, List<TrainJourney>>>> organizeSeats(List<TrainJourney> trainJourneys) {
        Map<Coach, List<TrainJourney>> coachSeatMap = new HashMap<>();

        for (TrainJourney trainJourney : trainJourneys) {
            Coach coach = trainJourney.getSeat().getCoach();
            List<TrainJourney> defaultList = coachSeatMap.getOrDefault(coach, new ArrayList<>());
            defaultList.add(trainJourney);
            coachSeatMap.put(coach, defaultList);
        }

        Map<Train, List<Map.Entry<Coach, List<TrainJourney>>>> trainCoachMap = new HashMap<>();
        for (Map.Entry<Coach, List<TrainJourney>> entry : coachSeatMap.entrySet()) {
            Train train = entry.getKey().getTrain();
            List<Map.Entry<Coach, List<TrainJourney>>> defaultList = trainCoachMap.getOrDefault(train, new ArrayList<>());
            defaultList.add(entry);
            trainCoachMap.put(train, defaultList);
        }
        return trainCoachMap;
    }

    private TicketSearchResponse convertToResponse(Map<Train, List<Map.Entry<Coach, List<TrainJourney>>>> trainCoachMap) {
        TicketSearchResponse ticketSearchResponse = new TicketSearchResponse();
        for (Map.Entry<Train, List<Map.Entry<Coach, List<TrainJourney>>>> trainEntry : trainCoachMap.entrySet()) {
            Train train = trainEntry.getKey();
            TicketTrainResponse trainResponse = new TicketTrainResponse();
            trainResponse.setTrainName(train.getName());

            Map<TicketClassResponse, List<TicketCoachResponse>> classResponseMap = new HashMap<>();

            trainEntry.getValue().sort(Comparator.comparingInt(a -> a.getKey().getOrdering()));
            for (Map.Entry<Coach, List<TrainJourney>> coachEntry : trainEntry.getValue()) {
                Coach coach = coachEntry.getKey();
                List<TrainJourney> trainJourneys = coachEntry.getValue();
                TrainJourney firstTrainJourney = trainJourneys.getFirst();

                trainResponse.setRoute(firstTrainJourney.getTrainRoute().getDescription());
                trainResponse.setDepartureTime(LocalDateTime.of(
                        firstTrainJourney.getJourneyDate(),
                        firstTrainJourney.getJourneyTime()
                ));
                trainResponse.setDestinationArrivalTime(firstTrainJourney.getDestinationArrivalTime());

                Map<Long, TrainJourney> seatForJourneyMap = buildSeatForJourneyMap(trainJourneys);
                if (!seatForJourneyMap.isEmpty()) {
                    TicketCoachResponse coachResponse = new TicketCoachResponse();
                    coachResponse.setCoachName(coach.getName());
                    coachResponse.setSeatOrientation(coach.getSeatOrientation());
                    coachResponse.setAvailableSeats(seatForJourneyMap.size());

                    List<Seat> seats = coach.getSeats()
                            .stream()
                            .sorted(Comparator.comparing(Seat::getOrdering))
                            .toList();

                    for (Seat seat : seats) {
                        TicketSeatResponse seatResponse = new TicketSeatResponse();
                        seatResponse.setSeatNumber(String.format("%s-%s", coach.getName(), seat.getNumber()));
                        if (seatForJourneyMap.containsKey(seat.getId())) {
                            TrainJourney trainJourney = seatForJourneyMap.get(seat.getId());
                            seatResponse.setId(trainJourney.getId());
                            seatResponse.setIdKey(trainJourney.getIdKey());
                            seatResponse.setAvailable(true);
                        }
                        coachResponse.getSeats().add(seatResponse);
                    }
                    TicketClassResponse classResponse = new TicketClassResponse(coach.getTicketClass(), firstTrainJourney.getFare());
                    List<TicketCoachResponse> defaultList = classResponseMap.getOrDefault(classResponse, new ArrayList<>());
                    defaultList.add(coachResponse);
                    classResponseMap.put(classResponse, defaultList);
                    trainResponse.setAvailableSeats(trainResponse.getAvailableSeats() + coachResponse.getAvailableSeats());
                }
            }

            trainResponse.setClasses(convertToClassResponse(classResponseMap));
            ticketSearchResponse.getTrains().add(trainResponse);
        }

        ticketSearchResponse.getTrains().sort(Comparator.comparing(TicketTrainResponse::getDepartureTime));
        return ticketSearchResponse;
    }

    private List<TicketClassResponse> convertToClassResponse(Map<TicketClassResponse, List<TicketCoachResponse>> classResponseMap) {
        List<TicketClassResponse> classResponses = new ArrayList<>();
        for (Map.Entry<TicketClassResponse, List<TicketCoachResponse>> entry : classResponseMap.entrySet()) {
            TicketClassResponse classResponse = entry.getKey();
            List<TicketCoachResponse> coachResponses = entry.getValue();
            classResponse.setCoaches(coachResponses);

            int availableSeats = 0;
            for (TicketCoachResponse coachResponse : coachResponses) {
                availableSeats += coachResponse.getAvailableSeats();
            }
            classResponse.setAvailableSeats(availableSeats);
            classResponses.add(classResponse);
        }

        return classResponses;
    }

    private Map<Long, TrainJourney> buildSeatForJourneyMap(List<TrainJourney> trainJourneys) {
        Map<Long, TrainJourney> seatForJourneyMap = new HashMap<>();
        for (TrainJourney trainJourney : trainJourneys) {
            if (AVAILABLE.equals(trainJourney.getSeatStatus()) || (BOOKED.equals(trainJourney.getSeatStatus()) &&
                    AppDateTimeUtils.nowInBD().minusMinutes(BOOKING_VALIDITY).isAfter(trainJourney.getBookingTime())))
                seatForJourneyMap.put(trainJourney.getSeat().getId(), trainJourney);
        }
        return seatForJourneyMap;
    }

    @Transactional(isolation = REPEATABLE_READ)
    @Override
    public void bookTicket(TicketBookingRequest request) {
        User currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> {
                    log.error("User must be logged in to book a ticket");
                    return new RuleViolationException("LOGGED_IN_USER_NOT_FOUND", "User must be logged in to book a ticket");
                });

        TrainJourney trainJourney = trainJourneyRepository.findById(request.id())
                .filter(tj -> tj.getIdKey().equals(request.idKey()) &&
                        (AVAILABLE.equals(tj.getSeatStatus()) || (BOOKED.equals(tj.getSeatStatus()) &&
                                AppDateTimeUtils.nowInBD().minusMinutes(BOOKING_VALIDITY).isAfter(tj.getBookingTime()))))
                .orElseThrow(() -> {
                    log.error("No available seat found with id: {}", request.id());
                    return new ResourceNotFoundException("AVAILABLE_SEAT_NOT_FOUND", "No ticket found with id: " + request.id());
                });

        LocalDateTime currentTime = AppDateTimeUtils.nowInBD();
        if (currentTime.isAfter(LocalDateTime.of(trainJourney.getJourneyDate(), trainJourney.getJourneyTime()))) {
            throw new RuleViolationException("BOOKING_TIME_EXPIRED", "Ticket booking time already expired");
        }

        trainJourney.setSeatStatus(BOOKED);
        trainJourney.setBookedBy(currentUser);
        trainJourney.setBookingTime(currentTime);

        trainJourneyRepository.save(trainJourney);
    }

    @Transactional(isolation = REPEATABLE_READ)
    @Override
    public ResponseEntity<Resource> confirmAndPrintTicket(TicketConfirmationRequest request) {
        User currentUser = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> {
                    log.error("User must be logged in to confirm a ticket");
                    return new RuleViolationException("LOGGED_IN_USER_NOT_FOUND", "User must be logged in to confirm a ticket");
                });

        List<TrainJourney> trainJourneys = trainJourneyRepository.findByIdIn(request.ids());
        List<String> seats = new ArrayList<>();
        double fare = 0;
        double serviceCharge = 0;
        if (trainJourneys.size() != request.ids().size()) {
            throw new ResourceNotFoundException("INVALID_TICKET_ID", "Some ids are not valid");
        }

        TrainJourney firstTrainJourney = trainJourneys.getFirst();
        for (TrainJourney trainJourney : trainJourneys) {
            if (!BOOKED.equals(trainJourney.getSeatStatus()) || !currentUser.equals(trainJourney.getBookedBy())) {
                throw new RuleViolationException("TICKET_CONFIRMATION_DENIED", "Ticket can be confirmed only by booked user");
            }
            if (AppDateTimeUtils.nowInBD().minusMinutes(BOOKING_VALIDITY).isAfter(trainJourney.getBookingTime())) {
                throw new RuleViolationException("CONFIRMATION_PERIOD_EXPIRED", "Ticket confirmation period expired");
            }

            trainJourney.setSeatStatus(SOLD);
            trainJourney.setSellingTime(AppDateTimeUtils.nowInBD());
            Seat seat = trainJourney.getSeat();
            seats.add(String.format("%s-%s", seat.getCoach().getName(), seat.getNumber()));
            fare += trainJourney.getFare();
            serviceCharge += SERVICE_CHARGE_PER_SEAT;
        }
        trainJourneyRepository.saveAll(trainJourneys);

        String randomFilename = String.format("%s.pdf", MiscUtils.generateIdKey());
        Ticket ticket = new Ticket();
        ticket.setIdKey(MiscUtils.generateIdKey());
        ticket.setTrainName(firstTrainJourney.getSeat().getCoach().getTrain().getName());
        ticket.setTicketClass(firstTrainJourney.getSeat().getCoach().getTicketClass());
        ticket.setSeats(String.join(", ", seats));
        ticket.setJourneyIds(request.ids());
        ticket.setFromStation(firstTrainJourney.getFromStation().getName());
        ticket.setToStation(firstTrainJourney.getToStation().getName());
        ticket.setJourneyDate(LocalDateTime.of(firstTrainJourney.getJourneyDate(), firstTrainJourney.getJourneyTime()));
        ticket.setFare(fare);
        ticket.setServiceCharge(serviceCharge);
        ticket.setPassengerName(currentUser.getName());
        ticket.setPassengerMobileNumber(currentUser.getMobileNumber());
        ticket.setPassengerNid(currentUser.getNid());
        ticket.setFilename(randomFilename);

        Resource resource = ticketPrintService.printTicket(ticketRepository.save(ticket));
        return MiscUtils.convertToFile(resource, randomFilename);
    }
}
