package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Position
 */
public enum Position {
    /**
     * Top right corner.
     */
    @SerializedName("topRight")
    TOP_RIGHT,
    /**
     * Top left corner.
     */
    @SerializedName("topLeft")
    TOP_LEFT,
    /**
     * Bottom right corner.
     */
    @SerializedName("bottomRight")
    BOTTOM_RIGHT,
    /**
     * Bottom left corner.
     */
    @SerializedName("bottomLeft")
    BOTTOM_LEFT
}
