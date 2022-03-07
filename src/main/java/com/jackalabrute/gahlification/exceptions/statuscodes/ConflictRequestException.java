package com.jackalabrute.gahlification.exceptions.statuscodes;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictRequestException extends ResponseStatusException {

    public ConflictRequestException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
