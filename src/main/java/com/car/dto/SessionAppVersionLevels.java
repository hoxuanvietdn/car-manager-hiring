package com.car.dto;

/**
 * Created by hxviet on 6/26/2015.
 */
public class SessionAppVersionLevels {
    long timeSeries;
    long totalSessionVersionLevel1;
    long totalSessionVersionLevel2;
    long totalSessionVersionLevel3;
    long totalSessionVersionLevel4;
    long totalSessionVersionLevelOther;
    long totalSessionVersionLevelAll;

    public SessionAppVersionLevels(long timeSeries,
                                   long totalSessionVersionLevel1,
                                   long totalSessionVersionLevel2,
                                   long totalSessionVersionLevel3,
                                   long totalSessionVersionLevel4,
                                   long totalSessionVersionLevelOther, long totalSessionVersionLevelAll) {
        this.timeSeries = timeSeries;
        this.totalSessionVersionLevel1 = totalSessionVersionLevel1;
        this.totalSessionVersionLevel2 = totalSessionVersionLevel2;
        this.totalSessionVersionLevel3 = totalSessionVersionLevel3;
        this.totalSessionVersionLevel4 = totalSessionVersionLevel4;
        this.totalSessionVersionLevelOther = totalSessionVersionLevelOther;
        this.totalSessionVersionLevelAll = totalSessionVersionLevelAll;
    }

    public long getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(long timeSeries) {
        this.timeSeries = timeSeries;
    }

    public long getTotalSessionVersionLevel1() {
        return totalSessionVersionLevel1;
    }

    public void setTotalSessionVersionLevel1(long totalSessionVersionLevel1) {
        this.totalSessionVersionLevel1 = totalSessionVersionLevel1;
    }

    public long getTotalSessionVersionLevel2() {
        return totalSessionVersionLevel2;
    }

    public void setTotalSessionVersionLevel2(long totalSessionVersionLevel2) {
        this.totalSessionVersionLevel2 = totalSessionVersionLevel2;
    }

    public long getTotalSessionVersionLevel3() {
        return totalSessionVersionLevel3;
    }

    public void setTotalSessionVersionLevel3(long totalSessionVersionLevel3) {
        this.totalSessionVersionLevel3 = totalSessionVersionLevel3;
    }

    public long getTotalSessionVersionLevel4() {
        return totalSessionVersionLevel4;
    }

    public void setTotalSessionVersionLevel4(long totalSessionVersionLevel4) {
        this.totalSessionVersionLevel4 = totalSessionVersionLevel4;
    }

    public long getTotalSessionVersionLevelOther() {
        return totalSessionVersionLevelOther;
    }

    public void setTotalSessionVersionLevelOther(long totalSessionVersionLevelOther) {
        this.totalSessionVersionLevelOther = totalSessionVersionLevelOther;
    }

    public long getTotalSessionVersionLevelAll() {
        return totalSessionVersionLevelAll;
    }

    public void setTotalSessionVersionLevelAll(long totalSessionVersionLevelAll) {
        this.totalSessionVersionLevelAll = totalSessionVersionLevelAll;
    }

    @Override
    public String toString() {
        return "SessionAppVersionLevels{" +
                "timeSeries=" + timeSeries +
                ", totalSessionVersionLevel1=" + totalSessionVersionLevel1 +
                ", totalSessionVersionLevel2=" + totalSessionVersionLevel2 +
                ", totalSessionVersionLevel3=" + totalSessionVersionLevel3 +
                ", totalSessionVersionLevel4=" + totalSessionVersionLevel4 +
                ", totalSessionVersionLevelOther=" + totalSessionVersionLevelOther +
                ", totalSessionVersionLevelAll=" + totalSessionVersionLevelAll +
                '}';
    }
}
