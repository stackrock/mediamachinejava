package io.mediamachine;

import io.mediamachine.models.Watermark;
import io.mediamachine.models.Webhooks;
import io.mediamachine.utils.MockAPI;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


public class ThumbnailJobBuilderTest {
    private static final String FAKE_API_KEY = "test-123";
    private static URL FAKE_SUCCESS_URL;
    private static URL FAKE_FAILURE_URL;
    private static URL FAKE_INPUT_URL;
    private static URL FAKE_OUTPUT_URL;
    private static Webhooks FAKE_WEBHOOKS;

    static {
        try {
            FAKE_SUCCESS_URL = new URL("http://stackrock.com/success");
            FAKE_FAILURE_URL = new URL("http://stackrock.com/failure");
            FAKE_INPUT_URL = new URL("http://stackrock.com/path/to/video.mp4");
            FAKE_OUTPUT_URL = new URL("http://stackrock.com/path/to/output");
            FAKE_WEBHOOKS = Webhooks.withDefaults().failureURL(FAKE_FAILURE_URL).successURL(FAKE_SUCCESS_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void throwExceptionIfApiKeyIsNull() {
        Watermark watermark = Watermark.withDefaults().text("stackrock.io").fontSize(12).fontColor("white");

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
                    ThumbnailJobBuilder
                            .withDefaults()
                            .webhook(FAKE_WEBHOOKS)
                            .from(FAKE_INPUT_URL)
                            .to(FAKE_OUTPUT_URL)
                            .watermark(watermark)
                            .width(150)
                            .execute();
                }
                , "Expected Thumbnail to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsEmpty() {
        Watermark watermark = Watermark.withDefaults().text("stackrock.io").fontSize(12).fontColor("white");

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            ThumbnailJobBuilder
                    .withDefaults()
                    .apiKey("")
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .watermark(watermark)
                    .width(150)
                    .execute();
        }, "Expected Thumbnail to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsOnlySpaces() {
        Watermark watermark = Watermark.withDefaults().text("stackrock.io").fontSize(12).fontColor("white");

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            ThumbnailJobBuilder
                    .withDefaults()
                    .apiKey("    ")
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .watermark(watermark)
                    .width(150)
                    .execute();
        }, "Expected Thumbnail to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfNoInputUrlAndNoInputBlob() {
        Watermark watermark = Watermark.withDefaults().text("stackrock.io").fontSize(12).fontColor("white");

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            ThumbnailJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .to(FAKE_OUTPUT_URL)
                    .width(150)
                    .watermark(watermark)
                    .execute();
        }, "Expected Thumbnail to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing from"));
    }

    @Test
    void throwExceptionIfNoOutputUrlAndNoOutputlob() {
        Watermark watermark = Watermark.withDefaults().text("stackrock.io").fontSize(12).fontColor("white");

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            ThumbnailJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .width(150)
                    .watermark(watermark)
                    .execute();
        }, "Expected Thumbnail to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing to"));
    }

    @Test
    void successIfValidArguments() {
        Watermark watermark = Watermark.withDefaults().text("stackrock.io").fontSize(12).fontColor("white");

        assertDoesNotThrow(() -> {
            ThumbnailJobBuilder
                    .withDefaults(new MockAPI())
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .width(150)
                    .watermark(watermark)
                    .execute();
        }, "Should not throw an exception");
    }
}
