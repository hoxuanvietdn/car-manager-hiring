package com.car.mysql.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by nvtien2 on 15/06/2015.
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins implements Serializable {
	private static final long serialVersionUID = -587067425711091104L;

	@Id
    @Column(name = "series", nullable = false)
    private String series;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "token", nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used", nullable = false)
    private Date lastUsed;

    public PersistentLogins() {
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
