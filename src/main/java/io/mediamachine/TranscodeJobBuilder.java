package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.models.*;

/**
 * TranscodeJobBuilder is a builder for a Thumbnail Job.
 */
public class TranscodeJobBuilder extends AbstractJobBuilder<TranscodeJobBuilder> {
    private Encoder encoder;
    private BitrateKbps bitrateKbps;
    private Container container;
    private Integer height = null;

    protected TranscodeJobBuilder(String apiKey) {
        // Set the defaults
        this.setApiKey(apiKey);
        this.width(720);
        // set transcode defaults
        this.encoder = Encoder.H264;
        this.container = Container.MP4;
        this.bitrateKbps = BitrateKbps.TWO_MBPS;
        this.width(720);
    }


    public TranscodeJobBuilder getThis() {
        return this;
    }

    /**
     * Set the height of the output video.
     *
     * @param height an int representing the height of the ouput video.
     * @return the {@link io.mediamachine.TranscodeJobBuilder} instance configured with the output height.
     */
    public TranscodeJobBuilder height(int height) {
        this.height = height;
        return this;
    }

    /**
     * Set the encoder to use for the output video.
     * @param encoder a {@link io.mediamachine.models.Encoder} with the encoder type to use.
     * @return the {@link io.mediamachine.TranscodeJobBuilder} instance configured with the encoder to use.
     */
    public TranscodeJobBuilder encoder(Encoder encoder) {
        this.encoder = encoder;
        return this;
    }

    /**
     * Set the bitrate to use for the output video.
     * @param bitrate a {@link io.mediamachine.models.BitrateKbps} with the bitrate to use.
     * @return the {@link io.mediamachine.TranscodeJobBuilder} instance configured with the bitrate to use.
     */
    public TranscodeJobBuilder bitrate(BitrateKbps bitrate) {
        this.bitrateKbps = bitrate;
        return this;
    }

    /**
     * Set the container to use for the output video.
     * @param container a {@link io.mediamachine.models.Container} with the container to use.
     * @return the {@link io.mediamachine.TranscodeJobBuilder} instance configured with the container to use.
     */
    public TranscodeJobBuilder container(Container container) {
        this.container = container;
        return this;
    }

    /**
     * Executes the Job.
     *
     * @return A {@link io.mediamachine.models.Job} object that can be used to query the status of the job.
     * @throws java.lang.IllegalStateException if there is any missing configuration.
     */
    public Job execute() {
        if (encoder == null) {
            throw new IllegalArgumentException("Missing encoder");
        }

        if (bitrateKbps == null) {
            throw new IllegalArgumentException("Missing bitrate");
        }

        if (container == null) {
            throw new IllegalArgumentException("Missing container");
        }

        if (container == Container.WEBM && (encoder == Encoder.H264 || encoder == Encoder.H265)) {
            throw new IllegalArgumentException("Wrong container/encoder combination");
        }

        Gson gson = new Gson();
        String json = gson.toJson(this);
        String jobType = "transcode";
        return runExecute(jobType, json);
    }
}
