package com.jifflenow.cis.models;

import com.google.api.services.calendar.model.TimePeriod;

import java.util.List;

public class CheckAvailabilityResponse {

    private String email;

    private FreeBusyStatus status;

    public CheckAvailabilityResponse() {
    }

    public CheckAvailabilityResponse(String email, FreeBusyStatus status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FreeBusyStatus getStatus() {
        return status;
    }

    public void setStatus(FreeBusyStatus status) {
        this.status = status;
    }
}
