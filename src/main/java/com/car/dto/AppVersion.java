package com.car.dto;

/**
 * Created by hxviet on 6/25/2015.
 */
public class AppVersion {
    private String appVersion;
    private long count;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "appVersion='" + appVersion + '\'' +
                ", count=" + count +
                '}';
    }
}
