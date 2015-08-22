package com.car.dto;

/**
 * Created by nvtien2 on 30/06/2015.
 */
public class SessionLocation {
    private String countryCode;
    private long numberOfSessions;

    public SessionLocation() {
    }

    public SessionLocation(String countryCode, long numberOfSessions) {
        this.countryCode = countryCode;
        this.numberOfSessions = numberOfSessions;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public long getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(long numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }
}
