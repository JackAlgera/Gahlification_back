package com.jackalabrute.homeclusterbackend.config.security.models;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private final String jwtToken;
    private final Long timestamp;

    public JwtResponse(String jwtToken, Long timestamp) {
        this.jwtToken = jwtToken;
        this.timestamp = timestamp;
    }

    public String getToken() {
        return this.jwtToken;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
