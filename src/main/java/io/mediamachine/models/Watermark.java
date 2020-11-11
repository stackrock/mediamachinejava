package io.mediamachine.models;

/**
 * Represent a Watermark to be used in the Job Builders.
 */
public class Watermark {
    private String text;
    private WatermarkImage image;
    private Integer fontSize;
    private String fontColor;
    private Position position;
    private Float opacity;

    private Watermark() {}

    /**
     * Creates a Watermark instance with the default configuration.
     *
     * @return The Watermark instance with the default configuration (fontSize = 12, fontColor = white, position = Position.BOTTOM_RIGHT, opacity = 0.9f).
     */
    public static Watermark withDefaults() {
        Watermark instance = new Watermark();
        instance.fontSize = 12;
        instance.fontColor = "white";
        instance.position = Position.BOTTOM_RIGHT;
        instance.opacity = 0.9f;
        return instance;
    }

    /**
     * Set the text to be used as a watermark.
     *
     * @param text a String with the text to be used as a watermark.
     * @return The Watermark instance with the text to be used as a watermark.
     */
    public Watermark text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Set the image to be used as a watermark.
     *
     * @param image a WatermarkImage instance that represents the image to be used.
     * @return The Watermark instance with the image to be used as a watermark.
     */
    public Watermark image(WatermarkImage image)  {
        image.build();
        this.image = image;
        return this;
    }

    /**
     * Set the fontSize to be used in the watermark text.
     *
     * @param fontSize an Integer with the font size to be used.
     * @return The Watermark instance with the fontSize configured.
     */
    public Watermark fontSize(Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    /**
     * Set the fontColor to be used in the watermark text.
     *
     * @param fontColor a String with the color to be used for the text watermark.
     * @return The Watermark instance with the fontColor configured.
     */
    public Watermark fontColor(String fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Set the position of the watermark in the resulting image/video/gif.
     *
     * @param position a Position describing where to place the watermark.
     * @return The Watermark instance with the position configured.
     */
    public Watermark position(Position position) {
        this.position = position;
        return this;
    }

    /**
     *  Set the opacity of the watermark in the resulting image/video/gif.
     *
     * @param opacity a Float from 0 to 1 with the opacity.
     * @return The Watermark instance with the position configured.
     */
    public Watermark opacity(Float opacity) {
        this.opacity = opacity;
        return this;
    }

    /**
     * Prepare the instance to be used by other classes.
     * There is no need to call this, the JobBuilders are going to call this for you.
     *
     * @throws IllegalStateException If there is any missing configuration.
     */
    public void build() {
        boolean noText = this.text == null || this.text.trim().equals("");
        boolean noImage = this.image == null;

        if (noText && noImage) {
            throw new IllegalStateException("Watermark: Either text or image must be provided");
        }
    }
}
