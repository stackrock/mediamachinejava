package io.mediamachine;

import io.mediamachine.models.*;
import io.mediamachine.utils.MockAPI;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class TranscodeJobBuilderTest {
    private static final String FAKE_API_KEY="test-123";
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


    private static TranscodeOpts FAKE_TRANSCODE_OPTS = TranscodeOpts.withDefaults();


    @Test
    void throwExceptionIfApiKeyIsNull() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder
                    .withDefaults()
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .options(FAKE_TRANSCODE_OPTS)
                    .execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsEmpty() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder
                    .withDefaults()
                    .apiKey("")
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .options(FAKE_TRANSCODE_OPTS)
                    .execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsOnlySpaces() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder
                    .withDefaults()
                    .apiKey("     ")
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .options(FAKE_TRANSCODE_OPTS)
                    .execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfNoInputUrlAndNoInputBlob() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .to(FAKE_OUTPUT_URL)
                    .options(FAKE_TRANSCODE_OPTS)
                    .execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing from"));
    }

    @Test
    void throwExceptionIfNoOutputUrlAndNoOutputlob() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .options(FAKE_TRANSCODE_OPTS)
                    .execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing to"));
    }

    @Test
    void throwExceptionIfNoTranscodeOpts() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing options"));
    }

    @Test
    void successIfValidArguments() {
        assertDoesNotThrow(() -> {
            TranscodeJobBuilder
                    .withDefaults(new MockAPI())
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .options(FAKE_TRANSCODE_OPTS)
                    .execute();
        }, "Should not throw an exception");
    }
}
