package io.mediamachine.models;

/**
 * Transcode options to be used for TranscodeJobs.
 */
public class TranscodeOpts {
    private Encoder encoder;
    private BitrateKbps bitrateKbps;
    private Container container;
    private VideoSize videoSize;

    private TranscodeOpts() {}

    /**
     * Creates a TranscodeOpts instance with the default values:
     * Encoder H264
     * Bitrate 4000kbps
     * Container MP4
     * Video Size 720p
     *
     * @return A TranscodeOpts instance with the default values.
     */
    public static TranscodeOpts withDefaults() {
        TranscodeOpts instance = new TranscodeOpts();
        instance.encoder = Encoder.H264;
        instance.bitrateKbps = BitrateKbps.FOUR_MBPS;
        instance.container = Container.MP4;
        instance.videoSize = VideoSize.VIDEO_SIZE_720;

        return instance;
    }

    /**
     * Set the encoder.
     * @param encoder A Encoder enum value.
     * @return The TranscoderOpts isntance configured with the encoder.
     */
    public TranscodeOpts encoder(Encoder encoder) {
        this.encoder = encoder;
        return this;
    }

    /**
     * Set the Bitrate.
     *
     * @param bitrate A BitrateKbps enum value.
     * @return The TranscoderOpts instance configured with the bitrate.
     */
    public TranscodeOpts bitrate(BitrateKbps bitrate) {
        this.bitrateKbps = bitrate;
        return this;
    }

    /**
     * Set the Container.
     *
     * @param container A Container enum value.
     * @return The TranscoderOpts instance configured with the container.
     */
    public TranscodeOpts container(Container container) {
        this.container = container;
        return this;
    }

    /**
     * Set the video size.
     *
     * @param videoSize A VideoSize enum value.
     * @return The TranscoderOpts instance configured with the container.
     */
    public TranscodeOpts videoSize(VideoSize videoSize) {
        this.videoSize = videoSize;
        return this;
    }
}
