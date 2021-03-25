package io.mediamachine.models;

import io.mediamachine.utils.API;
import io.mediamachine.utils.RealAPI;

/**
 * This class is a representation of a Job in mediamachine.io.
 *
 * @author MediaMachine
 * @version 1.0
 */
public class Job {
    private String reqId;
    private API api = new RealAPI();

    public Job(String reqId) {
        this.reqId = reqId;
    }

    Job(String reqId, API api) {
        this.reqId = reqId;
        this.api = api;
    }

    /**
     * This method is used to get the status of the job in our system.
     *
     * @return a {@link io.mediamachine.models.Status} value representing the status of the job in the system.
     */
    public Status status() {
        String status = this.api.getJobStatus(this.reqId);
        switch(status) {
            case "queued":
                return Status.IN_PROGRESS;
            case "done":
                return Status.DONE;
            default:
                return Status.UNKNOWN;
        }
    }



}
