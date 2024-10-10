package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.entity.Ticket;
import org.springframework.core.io.Resource;

public interface TicketPrintService {

    Resource printTicket(Ticket ticket);
}
