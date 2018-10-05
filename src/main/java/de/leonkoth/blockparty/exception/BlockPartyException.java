package de.leonkoth.blockparty.exception;

/**
 * Created by Leon on 17.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public class BlockPartyException extends RuntimeException {

    private String message;

    public BlockPartyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}

