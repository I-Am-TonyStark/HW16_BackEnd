package com.mamalimomen.base.controllers.utilities;

public final class InValidDataException extends Exception {

    public InValidDataException(String message) {
        super(message);
    }

    public InValidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
