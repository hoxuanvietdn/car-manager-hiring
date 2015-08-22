package com.car.dto;

/**
 * Created by ntminh on 12/06/2015.
 */
public class QuickStats {
    private long session;
    private double sessionTime;
    private double avgSessionTime;

    private int totalUser;
    private int totalNewUser;
    private int totalUserOnline;
    private int totalNewUserOnline;

    public QuickStats() {
        this.session = 0;
        this.sessionTime = 0;
        this.avgSessionTime = 0;
        this.totalUser = 0;
        this.totalNewUser = 0;
        this.totalUserOnline = 0;
        this.totalNewUserOnline = 0;
    }

    public long getSession() {
        return session;
    }

    public void setSession(long session) {
        this.session = session;
    }

    public double getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(double sessionTime) {
        this.sessionTime = sessionTime;
    }

    public double getAvgSessionTime() {
        return avgSessionTime;
    }

    public void setAvgSessionTime(double avgSessionTime) {
        this.avgSessionTime = avgSessionTime;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public int getTotalNewUser() {
        return totalNewUser;
    }

    public void setTotalNewUser(int totalNewUser) {
        this.totalNewUser = totalNewUser;
    }

    public int getTotalUserOnline() {
        return totalUserOnline;
    }

    public void setTotalUserOnline(int totalUserOnline) {
        this.totalUserOnline = totalUserOnline;
    }

    public int getTotalNewUserOnline() {
        return totalNewUserOnline;
    }

    public void setTotalNewUserOnline(int totalNewUserOnline) {
        this.totalNewUserOnline = totalNewUserOnline;
    }
}
