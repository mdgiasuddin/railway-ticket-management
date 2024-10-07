package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.TicketData;
import org.springframework.core.io.Resource;

public interface TicketPrintService {

    Resource printTicket(TicketData ticketData);
}
