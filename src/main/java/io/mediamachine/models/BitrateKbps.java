package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Bitrate.
 */
public enum BitrateKbps {
    /**
     * 8000Kbps.
     */
    @SerializedName("8000")
    EIGHT_MBPS,
    /**
     * 4000Kbps.
     */
    @SerializedName("4000")
    FOUR_MBPS,
    /**
     * 1000Kbps.
     */
    @SerializedName("1000")
    ONE_MBPS,
}
