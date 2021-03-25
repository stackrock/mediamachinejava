package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Bitrate is an enum used to configure the bitrate of the transcode output.
 * 
 * @see io.mediamachine.TranscodeJobBuilder#bitrate(BitrateKbps) 
 */
public enum BitrateKbps {
    /**
     * 4000Kbps.
     */
    @SerializedName("4000")
    FOUR_MBPS,
    /**
     *
     */
    @SerializedName("2000")
    TWO_MBPS,
    /**
     * 1000Kbps.
     */
    @SerializedName("1000")
    ONE_MBPS,
}
