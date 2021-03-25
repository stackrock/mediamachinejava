package io.mediamachine.models;

/**
 * This class represents a {@link io.mediamachine.models.Watermark} text to be used on any Job output.
 */
public class WatermarkText implements Watermark{
    private String text;
    private Integer fontSize;
    private String fontColor;
    private Position position;
    private Float opacity;

    private WatermarkText() {}

    /**
     * Creates a {@link io.mediamachine.models.WatermarkText}k instance with the default values:
     *
     *  - 12px for font size.
     *  - "white" for font color.
     *  - {@link io.mediamachine.models.Position#BOTTOM_RIGHT} position.
     *  - 90% (0.9) opacity.
     *
     * @return The {@link io.mediamachine.models.WatermarkText} instance with the default configuration.
     */
    public static WatermarkText withDefaults() {
        WatermarkText instance = new WatermarkText();
        instance.fontSize = 12;
        instance.fontColor = "white";
        instance.position = Position.BOTTOM_RIGHT;
        instance.opacity = 0.9f;
        return instance;
    }

    /**
     * Set the text to be used as a watermark.
     *
     * @param text a {@link java.lang.String} with the text to be used as a watermark.
     * @return The {@link io.mediamachine.models.WatermarkText} instance with the text to be used as a watermark.
     */
    public WatermarkText text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Set the fontSize to be used in the watermark text.
     *
     * @param fontSize an {@link java.lang.Integer} with the font size to be used.
     * @return The {@link io.mediamachine.models.WatermarkText} instance with the fontSize configured.
     */
    public WatermarkText fontSize(Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    /**
     * Set the fontColor to be used in the watermark text.
     *
     * @param fontColor a {@link java.lang.String} with the color to be used for the text watermark.
     * @return The {@link io.mediamachine.models.WatermarkText} instance with the fontColor configured.
     */
    public WatermarkText fontColor(String fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Set the position of the watermark in the resulting image/video/gif.
     *
     * @param position a {@link io.mediamachine.models.Position} describing where to place the watermark.
     * @return The {@link io.mediamachine.models.WatermarkText} instance with the position configured.
     */
    public WatermarkText position(Position position) {
        this.position = position;
        return this;
    }

    /**
     *  Set the opacity of the watermark in the resulting image/video/gif.
     *
     * @param opacity a Float from 0 to 1 with the opacity.
     * @return The Watermark instance with the position configured.
     */
    public WatermarkText opacity(Float opacity) {
        this.opacity = opacity;
        return this;
    }
}
