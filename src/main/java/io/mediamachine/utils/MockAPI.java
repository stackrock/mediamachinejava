package io.mediamachine.utils;

import io.mediamachine.models.Job;

public class MockAPI implements API {
    @Override
    public Job createJob(String jobType, String body) {
        return new Job("valid-req-id");
    }

    @Override
    public String getJobStatus(String jobId) {
        return "unknown";
    }
}
