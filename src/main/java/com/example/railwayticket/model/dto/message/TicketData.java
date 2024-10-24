package com.example.railwayticket.model.dto.message;

import com.example.railwayticket.model.entity.Ticket;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

import static com.example.railwayticket.utils.AppDateTimeUtils.DATE_PATTERN_FILE_DIR;

@Getter
@ToString(doNotUseGetters = true)
public class TicketData {
    private final String passengerName;
    private final String passengerEmail;
    private final String fileDirectory;
    private final String filename;

    public TicketData(Ticket ticket) {
        this.passengerName = ticket.getPassengerName();
        this.passengerEmail = ticket.getPassengerEmail();
        this.fileDirectory = ticket.getJourneyDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN_FILE_DIR));
        this.filename = ticket.getFilename();
    }
}
