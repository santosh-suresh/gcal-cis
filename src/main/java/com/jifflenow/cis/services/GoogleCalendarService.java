package com.jifflenow.cis.services;

import com.jifflenow.cis.models.CheckAvailabilityRequest;
import com.jifflenow.cis.models.CheckAvailabilityResponse;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Future;

public interface GoogleCalendarService {

    List<CheckAvailabilityResponse> getAvailability(CheckAvailabilityRequest request) throws Exception;
}
