package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Container is an enum used to configure the container of the transcoded video.
 * 
 * @see io.mediamachine.TranscodeJobBuilder#container(Container) 
 */
public enum Container {
    /**
     * MP4 sets the container to be mp4.
     *
     * This container can be used with {@link io.mediamachine.models.Encoder#H264},
     * {@link io.mediamachine.models.Encoder#H265},
     * {@link io.mediamachine.models.Encoder#VP8} and
     * {@link io.mediamachine.models.Encoder#VP9} encoders.
     */
    @SerializedName("mp4")
    MP4,
    /**
     * WEBM sets the container to be webm.
     *
     * This container can only be used with {@link io.mediamachine.models.Encoder#VP8} and {@link io.mediamachine.models.Encoder#VP9}.
     */
    @SerializedName("webm")
    WEBM
}
