package com.jifflenow.cis.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.FreeBusyCalendar;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyRequestItem;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.jifflenow.cis.config.AppProperties;
import com.jifflenow.cis.models.CheckAvailabilityRequest;
import com.jifflenow.cis.models.CheckAvailabilityResponse;
import com.jifflenow.cis.models.FreeBusyStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class GoogleCalendarServiceImpl implements GoogleCalendarService {
    private static final Logger logger = LoggerFactory.getLogger(GoogleCalendarServiceImpl.class);
    private static HttpTransport HTTP_TRANSPORT;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    @Autowired
    private AppProperties properties;


    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private GoogleCredential authorize(String impersonatedUser) throws Exception {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(properties.getAccountEmail())
                .setServiceAccountPrivateKeyFromP12File(properties.getKeyFile())
                .setServiceAccountScopes(SCOPES)
                .setServiceAccountUser(impersonatedUser)
                .build();
        return credential;
    }

    private Calendar getCalendarService(String email) throws Exception {
        GoogleCredential credential = authorize(email);
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(properties.getApplicationName())
                .build();
    }

    @Override
    public List<CheckAvailabilityResponse> getAvailability(CheckAvailabilityRequest request) throws Exception {
        Calendar calendarService = getCalendarService(request.getEmailAddress().get(0));
        DateTime minTime = new DateTime(request.getStartDate().toEpochSecond()*1000);
        DateTime maxTime = new DateTime(request.getEndDate().toEpochSecond()*1000);
        List<FreeBusyRequestItem> requestItems = request.getEmailAddress()
                .stream()
                .map(email -> new FreeBusyRequestItem().setId(email))
                .collect(Collectors.toList());
        FreeBusyRequest freeBusyRequest = new FreeBusyRequest();
        freeBusyRequest.setTimeMin(minTime);
        freeBusyRequest.setTimeMax(maxTime);
        freeBusyRequest.setItems(requestItems);
        FreeBusyResponse response = calendarService.freebusy().query(freeBusyRequest).execute();
        Map<String, FreeBusyCalendar> calendars = response.getCalendars();
        logger.info(String.format("%s", calendars));
        return calendars.entrySet()
                .stream()
                .map(GoogleCalendarServiceImpl::getResponse)
                .collect(Collectors.toList());
    }

    private static CheckAvailabilityResponse getResponse(Map.Entry<String,FreeBusyCalendar> entry) {
        FreeBusyCalendar calendar = entry.getValue();
        if(calendar.getErrors() != null && calendar.getErrors().size() > 0) {
            return new CheckAvailabilityResponse(entry.getKey(), FreeBusyStatus.Error);
        }
        FreeBusyStatus status = entry.getValue().getBusy().size() == 0 ? FreeBusyStatus.Available : FreeBusyStatus.Blocked;
        return new CheckAvailabilityResponse(entry.getKey(), status);
    }
}
