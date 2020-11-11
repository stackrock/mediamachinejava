package io.mediamachine.models;

import java.net.URL;

/**
 * This class represents a Watermark image to be used on any Job output.
 */
public class WatermarkImage {
    private URL path;
    private String imageName;
    private Integer width;
    private Integer height;

    public WatermarkImage() { }

    /**
     * Set the url of the image to be used as a watermark.
     *
     * @param path An URL object representing the location of the image.
     * @return A WatermarkImage configured with the path.
     */
    public WatermarkImage path(URL path) {
        this.path = path;
        return this;
    }

    /**
     * Set the name of the image to be used as a watermark.
     *
     * @param imageName A String representing the image name to be used as a watermark. This image must be previously registered on stackrock.io .
     * @return A WatermarkImage configured with the imageName to be used.
     */
    public WatermarkImage imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    /**
     * Set the width of the watermark image.
     *
     * @param width An Integer with the width.
     * @return A WatermarkImage configured with the width.
     */
    public WatermarkImage width(Integer width) {
        this.width = width;
        return this;
    }

    /**
     * Set the height of the watermark image.
     *
     * @param height An Integar with the height.
     * @return A WatermarkImage configured with the height.
     */
    public WatermarkImage height(Integer height) {
        this.height = height;
        return this;
    }

    /**
     * Builds the WatermarkImage object with the provided settings.
     * There is no need to call this method, the JobBuilders calls them internally.
     *
     * @throws IllegalStateException if there is any missing configuration.
     */
    public void build()  {
        boolean noPath = this.path == null;
        boolean noImageName = this.imageName == null || this.imageName.trim().equals("");

        if (noPath && noImageName) {
            throw new IllegalStateException("WatermarkImage: either path or imageName must be provided");
        }
    }
}
