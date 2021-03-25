package io.mediamachine.models;

/**
 * Status is the enum representing the differents statuses of a job in mediamachine.io.
 */
public enum Status {
    /**
     * The process is waiting to be queued on our service.
     */
    WAITING,
    /**
     * The process is being processed.
     */
    IN_PROGRESS,
    /**
     * The process is done.
     */
    DONE,
    /**
     * The process failed because of an unknown error.
     */
    UNKNOWN //Used for exceptions
}
