package io.mediamachine.models;

/**
 * Represent a Watermark to be used in the Job Builders.
 *
 * This is only the interface to mark a class can be used as a Watermark. There are currently 3 implementations:<br/><br/><br/>
 *
 * - {@link io.mediamachine.models.WatermarkText} that represents a text used as watermark.<br/>
 * - {@link io.mediamachine.models.WatermarkImage} that represents a image provided through an url to be used as a watermark.<br/>
 * - {@link io.mediamachine.models.WatermarkNamedImage} that represents an image stored on https://mediamachine.io that can be referenced with the name provided on our service.<br/>
 */
public interface Watermark {
}
