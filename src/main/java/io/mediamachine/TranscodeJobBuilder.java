package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.exceptions.ServiceException;
import io.mediamachine.models.*;
import io.mediamachine.utils.API;
import io.mediamachine.utils.RealAPI;

import java.io.FileNotFoundException;
import java.net.URL;

public class TranscodeJobBuilder {
    private String apiKey;
    private URL successUrl;
    private URL failureUrl;
    private URL inputUrl;
    private Blob inputBlob;
    private URL outputUrl;
    private Blob outputBlob;
    private Watermark watermark;
    private TranscodeOpts transcode;
    private API api = new RealAPI();

    private TranscodeJobBuilder() {
    }

    public static TranscodeJobBuilder withDefaults() {
        TranscodeJobBuilder instance = new TranscodeJobBuilder();
        return instance;
    }

    //package method to allow mock api
    static TranscodeJobBuilder withDefaults(API api) {
        TranscodeJobBuilder instance = new TranscodeJobBuilder();
        instance.api = api;
        return instance;
    }

    public TranscodeJobBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Set the success and failure URL for this job.
     *
     * @param webhooks a Webhooks object with the success and failure URL to use.
     * @return The ThumbnailJobBuilder instance configured with the failure and success URL.
     */
    public TranscodeJobBuilder webhook(Webhooks webhooks) {
        this.failureUrl = webhooks.getFailureURL();
        this.successUrl = webhooks.getSuccessURL();

        return this;
    }

    public TranscodeJobBuilder from(URL input) {
        this.inputUrl = input;
        return this;
    }

    public TranscodeJobBuilder from(Blob input) {
        this.inputBlob = input;
        return this;
    }

    public TranscodeJobBuilder to(URL output) {
        this.outputUrl = output;
        return this;
    }

    public TranscodeJobBuilder to(Blob output) {
        this.outputBlob = output;
        return this;
    }

    public TranscodeJobBuilder watermarkFromText(String text) {
        this.watermark = Watermark.withDefaults().text(text);
        return this;
    }

    public TranscodeJobBuilder watermark(Watermark watermark) {
        this.watermark = watermark;
        return this;
    }

    public TranscodeJobBuilder options(TranscodeOpts opts) {
        this.transcode = opts;
        return this;
    }


    public Job execute() {
        if (apiKey == null) {
            throw new IllegalStateException("Missing apiKey");
        }

        if (apiKey.trim().equals("")) {
            throw new IllegalStateException("Missing apiKey");
        }

        if (inputBlob == null && inputUrl == null) {
            throw new IllegalStateException("Missing from");
        }

        if (outputBlob == null && outputUrl == null) {
            throw new IllegalStateException("Missing to");
        }
        if (transcode == null) {
            throw new IllegalStateException("Missing options");
        }

        Gson gson = new Gson();
        String json = gson.toJson(this);
        try {
            Job job = this.api.createJob("transcode", json);
            return job;
        } catch (FileNotFoundException e) {
            throw new ServiceException("There was a problem creating a Job with our services:", e);
        }
    }
}
