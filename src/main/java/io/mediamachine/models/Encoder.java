package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Encoder is en enum used to configure the encoder used to transcode a video.
 *
 * @see io.mediamachine.TranscodeJobBuilder#encoder(Encoder)
 */
public enum Encoder {
    /**
     * H264 sets h264 as encoder.
     *
     * It can only be used by {@link io.mediamachine.models.Container#MP4} containers.
     */
    @SerializedName("h264")
    H264,
    /**
     * H265 sets h265 as encoder.
     *
     * It can only be used by {@link io.mediamachine.models.Container#MP4} containers.
     */
    @SerializedName("h265")
    H265,
    /**
     * VP8 sets vp8 as encoder.
     *
     * It can be used both by {@link io.mediamachine.models.Container#MP4} and {@link io.mediamachine.models.Container#WEBM} containers.
     */
    @SerializedName("vp8")
    VP8,
    /**
     * VP9 sets vp9 as encoder.
     *
     * It can be used both by {@link io.mediamachine.models.Container#MP4} and {@link io.mediamachine.models.Container#WEBM} containers.
     */
    @SerializedName("vp9")
    VP9,
}
