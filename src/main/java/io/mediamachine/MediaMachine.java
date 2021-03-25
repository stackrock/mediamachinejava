package io.mediamachine;

import io.mediamachine.models.SummaryType;

/**
 * Client class to connect to MediaMachine services.
 *
 * @author MediaMachine Corp.
 */
public class MediaMachine {
    private String apiKey;

    /**
     * Constructor for MediaMachine.
     *
     * This class build the client that is used to access mediamachine services.
     *
     * @param apiKey a String with the apiKey for your organization at https://mediamachine.io
     */
    public MediaMachine(String apiKey) {
        if (apiKey == null || apiKey.trim().length() == 0) {
            throw new IllegalStateException("Missing apiKey");
        }
        this.apiKey = apiKey;
    }


    /**
     * This method returns a new, configured {@link io.mediamachine.TranscodeJobBuilder} that can be used to
     * Transcode a video on our services.
     *
     * @return an instance of {@link io.mediamachine.TranscodeJobBuilder}.
     */
    public TranscodeJobBuilder transcodeJob() { return new TranscodeJobBuilder(apiKey); }

    /**
     * This method returns a new, configured {@link io.mediamachine.ThumbnailJobBuilder} that can be used to generate
     * a Thumbnail on our services.
     *
     * @return an instance of {@link io.mediamachine.ThumbnailJobBuilder}
     */
    public ThumbnailJobBuilder thumbnailJob() {
        return new ThumbnailJobBuilder(apiKey);
    }

    /**
     * This method returns a new, configured {@link io.mediamachine.SummaryJobBuilder} that can be used to generate
     * a GIF Summary on our services.
     *
     * @return an instance of {@link io.mediamachine.SummaryJobBuilder} configured to return a GIF summary.
     */
    public SummaryJobBuilder summaryGifJob() {
        return new SummaryJobBuilder(apiKey).type(SummaryType.GIF);
    }

    /**
     * This method returns a new, configured {@link io.mediamachine.SummaryJobBuilder} that can be used to generate
     * a MP4 Summary on our services.
     *
     * @return an instance of {@link io.mediamachine.SummaryJobBuilder} configured to return a MP4 summary.
     */
    public SummaryJobBuilder summaryMp4Job() {
        return new SummaryJobBuilder(apiKey).type(SummaryType.MP4);
    }
}
