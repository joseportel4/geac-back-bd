package br.com.geac.backend.Domain.Exceptions;

public class EventNotFinishedException extends BadRequestException {
    public EventNotFinishedException(String message) {
        super(message);
    }
}
