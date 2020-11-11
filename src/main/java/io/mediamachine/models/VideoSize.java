package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Video Sizes that can be used for transcoding.
 */
public enum VideoSize {
    /**
     * 1080p
     */
    @SerializedName("1080")
    VIDEO_SIZE_1080,
    /**
     * 720p
     */
    @SerializedName("720")
    VIDEO_SIZE_720,
    /**
     * 480
     */
    @SerializedName("480")
    VIDEO_SIZE_480
}
