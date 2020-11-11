package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.exceptions.ServiceException;
import io.mediamachine.models.*;
import io.mediamachine.utils.API;
import io.mediamachine.utils.RealAPI;

import java.io.FileNotFoundException;
import java.net.URL;

public class SummaryJobBuilder {
    private String apiKey;
    private SummaryType summaryType;
    private URL successUrl;
    private URL failureUrl;
    private URL inputUrl;
    private Blob inputBlob;
    private URL outputUrl;
    private Blob outputBlob;
    private Watermark watermark;
    private boolean removeAudio;
    private API api = new RealAPI();

    private SummaryJobBuilder() {
    }

    public static SummaryJobBuilder withDefaults() {
        SummaryJobBuilder instance = new SummaryJobBuilder();
        instance.removeAudio = true;
        return instance;
    }

    // package method to allow test to mock api
    static SummaryJobBuilder withDefaults(API api) {
        SummaryJobBuilder instance = new SummaryJobBuilder();
        instance.api = api;
        return instance;
    }

    public SummaryJobBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public SummaryJobBuilder type(SummaryType type) {
        this.summaryType = type;
        return this;
    }

    /**
     * Set the success and failure URL for this job.
     *
     * @param webhooks a Webhooks object with the success and failure URL to use.
     * @return The ThumbnailJobBuilder instance configured with the failure and success URL.
     */
    public SummaryJobBuilder webhook(Webhooks webhooks) {
        this.failureUrl = webhooks.getFailureURL();
        this.successUrl = webhooks.getSuccessURL();

        return this;
    }

    public SummaryJobBuilder from(URL input) {
        this.inputUrl = input;
        return this;
    }

    public SummaryJobBuilder from(Blob input) {
        this.inputBlob = input;
        return this;
    }

    public SummaryJobBuilder to(URL output) {
        this.outputUrl = output;
        return this;
    }

    public SummaryJobBuilder to(Blob output) {
        this.outputBlob = output;
        return this;
    }

    public SummaryJobBuilder watermarkFromText(String text) {
        this.watermark = Watermark.withDefaults().text(text);
        return this;
    }

    public SummaryJobBuilder watermark(Watermark watermark) {
        this.watermark = watermark;
        return this;
    }

    public SummaryJobBuilder removeAudio(boolean value) {
        this.removeAudio = value;
        return this;
    }

    /**
     * Executes the job.
     *
     * @return a Job instance that represent the work being done on mediamachine services.
     * @throws ServiceException if there is any problem with the underlying service call.
     */
    public Job execute() {
        if (inputBlob == null && inputUrl == null) {
            throw new IllegalStateException("Missing from");
        }

        if (outputBlob == null && outputUrl == null) {
            throw new IllegalStateException("Missing to");
        }

        if (apiKey == null) {
            throw new IllegalStateException("Missing apiKey");
        }

        if (apiKey.trim().equals("")) {
            throw new IllegalStateException("Missing apiKey");
        }

        if (summaryType == null) {
            throw new IllegalStateException("Missing type");
        }

        Gson gson = new Gson();
        String json = gson.toJson(this);
        String jobType = "gif_summary";
        if (summaryType == SummaryType.MP4) {
            jobType = "mp4_summary";
        }

        try {
            Job job = this.api.createJob(jobType, json);
            return job;
        } catch (FileNotFoundException e) {
            throw new ServiceException("There was a problem creating a Job with our services:", e);
        }
    }
}
