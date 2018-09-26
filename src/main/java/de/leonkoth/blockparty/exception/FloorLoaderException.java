package de.leonkoth.blockparty.exception;

import lombok.Getter;

public class FloorLoaderException extends Exception {

    @Getter
    private Error error;

    public FloorLoaderException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public enum Error {

        WRONG_HEIGHT("Floor must be 1 block high"), NO_SIZE("Floor has to have a size");

        @Getter
        private String message;

        Error(String message) {
            this.message = message;
        }

    }

}