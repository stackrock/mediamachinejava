package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Encoder
 */
public enum Encoder {
    /**
     * H264
     */
    @SerializedName("h264")
    H264,
    /**
     * H265
     */
    @SerializedName("h265")
    H265,
    /**
     * VP8
     */
    @SerializedName("vp8")
    VP8
}
