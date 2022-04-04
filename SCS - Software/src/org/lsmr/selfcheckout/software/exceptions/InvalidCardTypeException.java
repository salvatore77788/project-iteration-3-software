package org.lsmr.selfcheckout.software.exceptions;

/**
 * If card not valid
 */
public class InvalidCardTypeException extends Exception {
    public InvalidCardTypeException() {
        super("InvalidCardTypeException");
    }
}
