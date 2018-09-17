package de.leonkoth.blockparty.exception;

/**
 * Created by Leon on 17.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public class IDOverFlowException extends Exception{

    private String message;

    public IDOverFlowException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }


}

