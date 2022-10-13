package com.project.indytskyi.tripsservice.exceptions.enums;

public enum StatusException {
    TRIP_COMPLETED_EXCEPTION("This trip is over, start another one"),
    FORGET_STOP_EXCEPTION("Pause the car to end the trip"),
    UNFINISHED_TRIP_EXCEPTION("The user has not yet completed a trip"),
    STOPPED_CAR_EXCEPTION("The machine has stopped, take the machine off pause");

    private final String exception;

    StatusException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return this.exception;
    }
}
