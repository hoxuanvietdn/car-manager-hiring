package com.car.dto;

import java.util.List;

/**
 * Created by hxviet on 6/26/2015.
 */
public class AppVersionWrapper {
    List<SessionAppVersionLevels> sessionAppVersionLevelses;
    List<AppVersion> appVersions;

    public List<SessionAppVersionLevels> getSessionAppVersionLevelses() {
        return sessionAppVersionLevelses;
    }

    public void setSessionAppVersionLevelses(List<SessionAppVersionLevels> sessionAppVersionLevelses) {
        this.sessionAppVersionLevelses = sessionAppVersionLevelses;
    }

    public List<AppVersion> getAppVersions() {
        return appVersions;
    }

    public void setAppVersions(List<AppVersion> appVersions) {
        this.appVersions = appVersions;
    }
}
