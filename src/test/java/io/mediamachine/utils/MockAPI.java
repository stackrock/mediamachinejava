package io.mediamachine.utils;

import io.mediamachine.models.Job;

public class MockAPI implements API {
    private String jobType;
    private String body;

    @Override
    public Job createJob(String jobType, String body) {
        this.jobType = jobType;
        this.body = body;
        return new Job("valid-req-id");
    }

    @Override
    public String getJobStatus(String jobId) {
        return "unknown";
    }

    public String getJobType() {
        return jobType;
    }

    public String getBody() {
        return body;
    }
}
