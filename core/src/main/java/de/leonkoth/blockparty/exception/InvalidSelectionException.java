package de.leonkoth.blockparty.exception;

import lombok.Getter;

public class InvalidSelectionException extends Exception {

    @Getter
    private Error error;

    public InvalidSelectionException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public enum Error {

        NO_SELECTION("Select 2 points first"), DIFFERENT_WORLDS("The points have to be in the same world");

        @Getter
        private String message;

        Error(String message) {
            this.message = message;
        }

    }

}
