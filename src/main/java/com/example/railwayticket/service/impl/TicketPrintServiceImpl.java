package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.entity.Ticket;
import com.example.railwayticket.service.intface.TicketPrintService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import static com.example.railwayticket.utils.AppDateTimeUtils.DATE_TIME_PATTERN;
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.WHITE;
import static com.itextpdf.text.Font.FontFamily.TIMES_ROMAN;
import static com.itextpdf.text.Font.NORMAL;

@Slf4j
@Service
public class TicketPrintServiceImpl implements TicketPrintService {

    @Value("${ticket.file.base.directory}")
    private String fileBaseDirectory;

    @Override
    public Resource printTicket(Ticket ticket) {
        float margin = 25f;

        Document document = new Document(new Rectangle(450, 500), margin, margin, margin, margin);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Font font = new Font(TIMES_ROMAN, 11f, NORMAL, BLACK);
        Font headFont = new Font(TIMES_ROMAN, 11f, NORMAL, WHITE);
        BaseColor headerColor = new BaseColor(67, 171, 82);

        try {
            String fileDirectory = String.format("%s/%s", fileBaseDirectory, ticket.getJourneyDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            Files.createDirectories(Paths.get(fileDirectory));
            OutputStream fileOutputStream = Files.newOutputStream(Paths.get(String.format("%s/%s", fileDirectory, ticket.getFilename())));
            PdfWriter.getInstance(document, outputStream); // Return as response.
            PdfWriter.getInstance(document, fileOutputStream); // Save to a file

            document.open();

            PdfPTable journeyInfoTable = new PdfPTable(2);
            journeyInfoTable.setWidthPercentage(90);
            journeyInfoTable.setSpacingAfter(20);
            journeyInfoTable.setWidths(new int[]{1, 1});

            PdfPCell journeyInfo = new PdfPCell(new Phrase("Journey Information", headFont));
            journeyInfo.setBackgroundColor(headerColor);
            journeyInfo.setColspan(2);
            journeyInfoTable.addCell(journeyInfo);
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Issue Date & Time", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(ticket.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Journey Date & Time", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(ticket.getJourneyDate().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Train Name", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(ticket.getTrainName(), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("From Station", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(ticket.getFromStation(), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("To Station", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(ticket.getToStation(), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Ticket Class", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(ticket.getTicketClass().name(), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Coach & Seat Number", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(ticket.getSeats(), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Fare", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(String.valueOf(ticket.getFare()), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Service Charge", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(String.valueOf(ticket.getServiceCharge()), font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase("Total Fare", font)));
            journeyInfoTable.addCell(new PdfPCell(new Phrase(String.valueOf(ticket.getFare() + ticket.getServiceCharge()), font)));

            PdfPTable passengerInfoTable = new PdfPTable(2);
            passengerInfoTable.setWidthPercentage(90);
            passengerInfoTable.setSpacingAfter(20);
            passengerInfoTable.setWidths(new int[]{1, 1});

            PdfPCell passengerInfo = new PdfPCell(new Phrase("Passenger Information", headFont));
            passengerInfo.setBackgroundColor(headerColor);
            passengerInfo.setColspan(2);
            passengerInfoTable.addCell(passengerInfo);
            passengerInfoTable.addCell(new PdfPCell(new Phrase("Passenger Name", font)));
            passengerInfoTable.addCell(new PdfPCell(new Phrase(ticket.getPassengerName(), font)));
            passengerInfoTable.addCell(new PdfPCell(new Phrase("Mobile Number", font)));
            passengerInfoTable.addCell(new PdfPCell(new Phrase(ticket.getPassengerMobileNumber(), font)));
            passengerInfoTable.addCell(new PdfPCell(new Phrase("NID", font)));
            passengerInfoTable.addCell(new PdfPCell(new Phrase(ticket.getPassengerNid(), font)));

            document.add(journeyInfoTable);
            document.add(passengerInfoTable);

            document.close();

        } catch (DocumentException | IOException e) {
            log.error("Error occurred: {}", e.getMessage());
        }

        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }
}
