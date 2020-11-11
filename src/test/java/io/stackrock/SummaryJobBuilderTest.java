package io.mediamachine;

import io.mediamachine.models.SummaryType;
import io.mediamachine.models.Webhooks;
import io.mediamachine.utils.MockAPI;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryJobBuilderTest {
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
    void throwExceptionIfApiKeyIsNull() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            SummaryJobBuilder
                    .withDefaults()
                    .type(SummaryType.MP4)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .removeAudio(false)
                    .execute();
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsEmpty() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            SummaryJobBuilder
                    .withDefaults()
                    .apiKey("")
                    .type(SummaryType.MP4)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .removeAudio(false)
                    .execute();
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsOnlySpaces() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            SummaryJobBuilder
                    .withDefaults()
                    .apiKey("     ")
                    .type(SummaryType.MP4)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .removeAudio(false)
                    .execute();
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfNoInputUrlAndNoInputBlob() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            SummaryJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .type(SummaryType.MP4)
                    .webhook(FAKE_WEBHOOKS)
                    .to(FAKE_OUTPUT_URL)
                    .removeAudio(false)
                    .execute();
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing from"));
    }

    @Test
    void throwExceptionIfNoOutputUrlAndNoOutputlob() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            SummaryJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .type(SummaryType.MP4)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .removeAudio(false)
                    .execute();
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing to"));
    }

    @Test
    void throwExceptionIfNoSummary() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            SummaryJobBuilder
                    .withDefaults()
                    .apiKey(FAKE_API_KEY)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .removeAudio(false)
                    .execute();
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing type"));
    }

    @Test
    void successIfValidArguments() {
        assertDoesNotThrow(() -> {
            SummaryJobBuilder
                    .withDefaults(new MockAPI())
                    .apiKey(FAKE_API_KEY)
                    .type(SummaryType.MP4)
                    .webhook(FAKE_WEBHOOKS)
                    .from(FAKE_INPUT_URL)
                    .to(FAKE_OUTPUT_URL)
                    .removeAudio(false)
                    .execute();
        }, "Should not throw an exception");
    }
}
