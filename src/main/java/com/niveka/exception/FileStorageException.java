package com.niveka.exception;

/**
 * Created by Nivek@lara on 13/02/2019.
 */
public class FileStorageException extends RuntimeException{
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
