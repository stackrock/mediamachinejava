package io.mediamachine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MediaMachineTest {
    @Test
    void throwExceptionIfApiKeyIsNull() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            new MediaMachine(null);
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsEmpty() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            new MediaMachine("");
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }

    @Test
    void throwExceptionIfApiKeyIsOnlySpaces() {
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            new MediaMachine("   ");
        }, "Expected Summary to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing apiKey"));
    }
}
