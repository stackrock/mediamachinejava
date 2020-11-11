package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.exceptions.ServiceException;
import io.mediamachine.models.Blob;
import io.mediamachine.models.Job;
import io.mediamachine.models.Watermark;
import io.mediamachine.models.Webhooks;
import io.mediamachine.utils.API;
import io.mediamachine.utils.RealAPI;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Class that build a ThumbnailJob
 */
public class ThumbnailJobBuilder {
    private String apiKey;
    private URL successUrl;
    private URL failureUrl;
    private URL inputUrl;
    private Blob inputBlob;
    private URL outputUrl;
    private Blob outputBlob;
    private Integer width;
    private Watermark watermark;
    private API api = new RealAPI();

    private ThumbnailJobBuilder() {}

    //Package constructor to mock API
    static ThumbnailJobBuilder withDefaults(API api) {
        ThumbnailJobBuilder instance = new ThumbnailJobBuilder();
        instance.api = api;
        return instance;
    }

    /**
     * Method to create a ThumbnailJobBuilder instance with the default settings.
     *
     * @return A ThumbnailJobBuilder instance with defaults settings.
     */
    public static ThumbnailJobBuilder withDefaults() {
        return new ThumbnailJobBuilder();
    }

    /**
     * Set the API Key to use when creating the Job.
     *
     * @param apiKey a String representing the Api Key obtained from stackrock.io .
     * @return A ThumbnailJobBuilder instance configured with the Api Key.
     */
    public ThumbnailJobBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Set the success and failure URL for this job.
     *
     * @param webhooks a Webhooks object with the success and failure URL to use.
     * @return The ThumbnailJobBuilder instance configured with the failure and success URL.
     */
    public ThumbnailJobBuilder webhook(Webhooks webhooks) {
        this.failureUrl = webhooks.getFailureURL();
        this.successUrl = webhooks.getSuccessURL();

        return this;
    }

    /**
     * Set the source based on a Blob.
     *
     * @param input a Blob object representing the source of the video to be processed.
     * @return A ThumbnailJobBuilder instance configured with the source.
     */
    public ThumbnailJobBuilder from(Blob input) {
        this.inputBlob = input;
        return this;
    }

    /**
     * Set the source based on an URL.
     *
     * @param input an URL object representing the source of the video to be processed.
     * @return A ThumbnailJobBuilder instance configured with the source.
     */
    public ThumbnailJobBuilder from(URL input) {
        this.inputUrl = input;
        return this;
    }

    /**
     * Set the destination based on a Blob.
     *
     * @param output a Blob object representing the destination of the image.
     * @return A ThumbnailJobBuilder instance configured with the destination.
     */
    public ThumbnailJobBuilder to(Blob output) {
        this.outputBlob = output;
        return this;
    }

    /**
     * Set the destination based on an URL.
     *
     * @param output an URL object representing the destination of the image.
     * @return A ThumbnailJobBuilder instance configured with the destination.
     */
    public ThumbnailJobBuilder to(URL output) {
        this.outputUrl = output;
        return this;
    }

    /**
     * Set the text to be used as a watermark with the default configuration.
     *
     * @param text A String representing the text to be used as a watermark (will be used with fontSize: 12, fontColor: white and position: Position.BottomRight).
     * @return A ThumbnailJobBuilder instance configured with the watermark.
     */
    public ThumbnailJobBuilder watermarkFromText(String text) {
        this.watermark = Watermark.withDefaults().text(text);
        return this;
    }

    /**
     * Set the watermark to be used.
     *
     * @param watermark A Watermark object representing the watermark to be used with the output.
     * @return A ThumbnailJobBuilder instance configured with the watermark.
     */
    public ThumbnailJobBuilder watermark(Watermark watermark) {
        this.watermark = watermark;
        return this;
    }

    /**
     * Set the width of the output
     *
     * @param width An Integer with the width to be used for the output.
     * @return A ThumbnailJobBuilder instance configured with the width.
     */
    public ThumbnailJobBuilder width(Integer width) {
        this.width = width;
        return this;
    }

    /**
     * Executes the Job.
     *
     * @return A Job object that can be used to query the status of the job.
     * @throws IllegalStateException if there is any missing configuration.
     */
    public Job execute()  {
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

        Gson gson = new Gson();
        String json = gson.toJson(this);
        try {
            Job job = this.api.createJob("thumbnail", json);
            return job;
        } catch (FileNotFoundException e) {
            throw new ServiceException("There was a problem creating a Job with our services:", e);
        }
    }
}
