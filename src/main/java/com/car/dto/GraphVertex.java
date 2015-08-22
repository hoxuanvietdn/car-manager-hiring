package com.car.dto;

/**
 * Created by nvtien2 on 01/06/2015.
 */
public class GraphVertex {
    String timeSeries;
    long numOfSessions;
    double timeSpents;

    public GraphVertex() {
    }

    public GraphVertex(String timeSeries, long numOfSessions) {
        this.timeSeries = timeSeries;
        this.numOfSessions = numOfSessions;
    }

    public GraphVertex(String timeSeries, double timeSpents) {
        this.timeSeries = timeSeries;
        this.timeSpents = timeSpents;
    }

    public String getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(String timeSeries) {
        this.timeSeries = timeSeries;
    }

    public long getNumOfSessions() {
        return numOfSessions;
    }

    public void setNumOfSessions(long numOfSessions) {
        this.numOfSessions = numOfSessions;
    }

    public double getTimeSpents() {
        return timeSpents;
    }

    public void setTimeSpents(double timeSpents) {
        this.timeSpents = timeSpents;
    }
}
