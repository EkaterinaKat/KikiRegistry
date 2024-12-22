package com.katyshevtseva.kikinotebook.core.films.web.exception;

public class FailedToLoadFilmsException extends Exception {

    public FailedToLoadFilmsException() {
    }

    public FailedToLoadFilmsException(String message) {
        super(message);
    }
}
