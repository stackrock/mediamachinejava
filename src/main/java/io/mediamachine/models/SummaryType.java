package io.mediamachine.models;

/**
 * SummaryType is an enum used to configure {@link io.mediamachine.SummaryJobBuilder} to output a GIF or MOV Summary.
 */
public enum SummaryType {
    /**
     * GIF configures the output of the Summary to be a gif.
     */
    GIF,
    /**
     * MP4 configures the output of the Summary to be a mp4.
     */
    MP4
}
