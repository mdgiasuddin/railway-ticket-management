package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.entity.Ticket;
import com.example.railwayticket.service.intface.TicketPrintService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.Font.FontFamily.TIMES_ROMAN;
import static com.itextpdf.text.Font.NORMAL;

@Slf4j
@Service
public class TicketPrintServiceImpl implements TicketPrintService {

    @Override
    public Resource printTicket(Ticket ticket) {
        float margin = 25f;

        Document document = new Document(PageSize.A4, margin, margin, margin, margin);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Font font = new Font(TIMES_ROMAN, 11f, NORMAL, BLACK);

        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();
            document.add(new Paragraph("Thank you for travelling with Bangladesh Railway", font));

            document.close();

        } catch (DocumentException e) {
            log.error("Error occurred.");
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(inputStream);
    }
}
