package com.jifflenow.cis.controllers;

import com.jifflenow.cis.models.CheckAvailabilityRequest;
import com.jifflenow.cis.models.CheckAvailabilityResponse;
import com.jifflenow.cis.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@RestController
public class CisController {

    @Autowired
    private GoogleCalendarService calendarService;


    @RequestMapping(value = "/users/check_availability", method = RequestMethod.POST)
    public List<CheckAvailabilityResponse> getAvailability(@Valid @RequestBody CheckAvailabilityRequest request) throws Exception {
        List<CheckAvailabilityResponse> responses = calendarService.getAvailability(request);
        return responses;
    }
}
