package com.ticket.viewers.dto;

import java.time.LocalDate;

public class RequestViewer {

    private Long viewerid;


    private Integer ticketid;


    private String name;


    private LocalDate lastlogin;


    private Boolean status;


    public Long getViewerid() {
        return viewerid;
    }

    public void setViewerid(Long viewerid) {
        this.viewerid = viewerid;
    }

    public Integer getTicketid() {
        return ticketid;
    }

    public void setTicketid(Integer ticketid) {
        this.ticketid = ticketid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(LocalDate lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
