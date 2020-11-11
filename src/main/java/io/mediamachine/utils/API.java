package io.mediamachine.utils;

import io.mediamachine.models.Job;

import java.io.FileNotFoundException;

public interface API {
    Job createJob(String jobType, String body) throws FileNotFoundException;
    String getJobStatus(String jobId);
}
