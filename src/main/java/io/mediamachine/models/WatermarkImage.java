package io.mediamachine.models;

import java.net.URL;

/**
 * This class represents a {@link io.mediamachine.models.Watermark} image (provided as an URL) to be used on any Job output.
 */
public class WatermarkImage implements Watermark {
    private URL imageUrl;
    private int width;
    private int height;
    private Position position;
    private float opacity;

    private WatermarkImage() { }

    /**
     * Returns a {@link io.mediamachine.models.WatermarkImage} instances configured with the defaults values:
     *  - 300px width.
     *  - 100px height.
     *  - {@link io.mediamachine.models.Position#BOTTOM_RIGHT} position.
     *  - 90% (0.9) opacity.
     *
     * @return a {@link io.mediamachine.models.WatermarkImage} instance.
     */
    public static WatermarkImage withDefaults() {
        WatermarkImage instance = new WatermarkImage();
        instance.width = 300;
        instance.height = 100;
        instance.position = Position.BOTTOM_RIGHT;
        instance.opacity = 0.9f;
        return instance;
    }
    /**
     * Set the url of the image to be used as a watermark.
     *
     * @param path An {@link java.net.URL} object representing the location of the image.
     * @return A WatermarkImage configured with the path.
     */
    public WatermarkImage url(URL path) {
        this.imageUrl = path;
        return this;
    }


    /**
     * Set the width of the watermark image.
     *
     * @param width An {@link java.lang.Integer} with the width.
     * @return A WatermarkImage configured with the width.
     */
    public WatermarkImage width(Integer width) {
        this.width = width;
        return this;
    }

    /**
     * Set the height of the watermark image.
     *
     * @param height An {@link java.lang.Integer} with the height.
     * @return A WatermarkImage configured with the height.
     */
    public WatermarkImage height(Integer height) {
        this.height = height;
        return this;
    }
}
