package com.kas.exception;

public class ResourceIsExistingException extends RuntimeException{
    public ResourceIsExistingException(String msg){
        super(msg);
    }

    public ResourceIsExistingException(String msg, Throwable cause){
        super(msg, cause);
    }
}
