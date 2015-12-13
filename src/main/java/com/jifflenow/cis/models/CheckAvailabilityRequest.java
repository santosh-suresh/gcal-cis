package com.jifflenow.cis.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public class CheckAvailabilityRequest {

    @JsonProperty("users")
    @NotNull
    private List<String> emailAddress;

    @JsonProperty("start_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private ZonedDateTime startDate;

    @JsonProperty("end_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime endDate;

    @JsonProperty("jn_request_id")
    @NotNull
    private String jnRequestId;

    @JsonProperty("include_jf_meeting")
    @NotNull
    private boolean includeJnMeetings;

    public List<String> getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(List<String> emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getJnRequestId() {
        return jnRequestId;
    }

    public void setJnRequestId(String jnRequestId) {
        this.jnRequestId = jnRequestId;
    }

    public boolean isIncludeJnMeetings() {
        return includeJnMeetings;
    }

    public void setIncludeJnMeetings(boolean includeJnMeetings) {
        this.includeJnMeetings = includeJnMeetings;
    }
}
