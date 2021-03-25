package io.mediamachine.models;

/**
 * This class represents a {@link io.mediamachine.models.Watermark} named image (using an image stored in https://mediamachine.io) to be used on any Job output.
 */
public class WatermarkNamedImage implements Watermark {
    private String imageName;
    private int width;
    private int height;
    private Position position;
    private float opacity;

    private WatermarkNamedImage() { }

    /**
     * Returns a {@link io.mediamachine.models.WatermarkNamedImage} instances configured with the defaults values:
     *  - 300px width.
     *  - 100px height.
     *  - {@link io.mediamachine.models.Position#BOTTOM_RIGHT} position.
     *  - 90% (0.9) opacity.
     *
     * @return a {@link io.mediamachine.models.WatermarkNamedImage} instance.
     */
    public static WatermarkNamedImage withDefaults() {
        WatermarkNamedImage instance = new WatermarkNamedImage();
        instance.width = 300;
        instance.height = 100;
        instance.position = Position.BOTTOM_RIGHT;
        instance.opacity = 0.9f;
        return instance;
    }
    /**
     * Set the name of the image to be used as a watermark.
     *
     * @param name An {@link java.lang.String} object representing the name of the image stored on https://mediamachine.io.
     * @return A {@link io.mediamachine.models.WatermarkNamedImage} configured with the name.
     */
    public WatermarkNamedImage name(String name) {
        this.imageName = name;
        return this;
    }


    /**
     * Set the width of the watermark image.
     *
     * @param width An {@link java.lang.Integer} with the width.
     * @return A {@link io.mediamachine.models.WatermarkNamedImage} configured with the width.
     */
    public WatermarkNamedImage width(Integer width) {
        this.width = width;
        return this;
    }

    /**
     * Set the height of the watermark image.
     *
     * @param height An {@link java.lang.Integer} with the height.
     * @return A {@link io.mediamachine.models.WatermarkNamedImage} configured with the height.
     */
    public WatermarkNamedImage height(Integer height) {
        this.height = height;
        return this;
    }
}
