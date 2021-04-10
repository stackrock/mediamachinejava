package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.models.*;
import io.mediamachine.utils.MockAPI;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TranscodeJobBuilderTest {
    private static final String FAKE_API_KEY = "test-123";
    private static URL FAKE_SUCCESS_URL;
    private static URL FAKE_FAILURE_URL;
    private static URL FAKE_WATERMARK_IMAGE_URL;
    private static final String FAKE_INPUT_URL = "http://mediamachine.io/path/to/video.mp4";
    private static final String FAKE_OUTPUT_URL = "http://mediamachine.io/path/to/output";
    private static final String FAKE_GCP_JSON = "{\"test\": \"123\"}";
    private static final String FAKE_AWS_REGION = "us-east-1";
    private static final String FAKE_AWS_ACCESS_KEY_ID = "abc123";
    private static final String FAKE_AWS_SECRET_ACCESS_KEY = "abc123";
    private static final String FAKE_AZURE_ACCOUNT_NAME = "mediamachine";
    private static final String FAKE_AZURE_ACCOUNT_KEY = "123";

    private static Webhooks FAKE_WEBHOOKS;

    static {
        try {
            FAKE_SUCCESS_URL = new URL("http://mediamachine.io/success");
            FAKE_FAILURE_URL = new URL("http://mediamachine.io/failure");
            FAKE_WATERMARK_IMAGE_URL = new URL("http://path.to/your/watermark/image");
            FAKE_WEBHOOKS = Webhooks.withDefaults().failureURL(FAKE_FAILURE_URL).successURL(FAKE_SUCCESS_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    void throwExceptionIfNoInput() {
        MediaMachine client = new MediaMachine(FAKE_API_KEY);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder tjb = client.transcodeJob().webhooks(FAKE_WEBHOOKS).to(FAKE_OUTPUT_URL);
            tjb.execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing from"));
    }

    @Test
    void throwExceptionIfNoOutput() {
        MediaMachine client = new MediaMachine(FAKE_API_KEY);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            TranscodeJobBuilder tjb = client.transcodeJob().webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL);
            tjb.execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing to"));
    }

    @Test
    void throwsExceptionIfNoEncoder() {
        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).encoder(null);
            tjb.execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing encoder"));
    }


    @Test
    void throwsExceptionIfNoBitrate() {
        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).bitrate(null);
            tjb.execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing bitrate"));
    }

    @Test
    void throwsExceptionIfNoContainer() {
        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).container(null);
            tjb.execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Missing container"));
    }

    @Test
    void throwsExceptionIfContainerIsWebmAndEncoderIsH264() {
        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).container(Container.WEBM).encoder(Encoder.H264);
            tjb.execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Wrong container/encoder combination"));
    }

    @Test
    void throwsExceptionIfContainerIsWebmAndEncoderIsH265() {
        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).container(Container.WEBM).encoder(Encoder.H265);
            tjb.execute();
        }, "Expected Transcode to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Wrong container/encoder combination"));
    }

    @Test
    void successIfValidArgumentsWithInputUrlAndOutputUrlWithNoWatermarkAndDefaultWidth() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "http://mediamachine.io/path/to/video.mp4",
            "outputUrl": "http://mediamachine.io/path/to/output",
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
            
        }
        """;
        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).width(null);
        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");
        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }


    @Test
    void successIfValidArgumentsWithInputUrlAndOutputUrlWithNoWatermarkAndProvidedHeight() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "http://mediamachine.io/path/to/video.mp4",
            "outputUrl": "http://mediamachine.io/path/to/output",
            "width": 720,
            "height": 300,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
            
        }
        """;
        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).height(300);
        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");
        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }


    @Test
    void successIfValidArgumentsWithInputUrlAndOutputUrlWithNoWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "http://mediamachine.io/path/to/video.mp4",
            "outputUrl": "http://mediamachine.io/path/to/output",
            "width": 720,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
            
        }
        """;
        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL);
        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");
        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }

    @Test
    void successIfValidArgumentsWithInputS3AndOutputS3WithNoWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "s3://bucket/key",
            "outputUrl": "s3://bucket/outputKey",
            "inputCreds": {"region":"us-east-1", "accessKeyId":"abc123", "secretAccessKey":"abc123"},
            "outputCreds": {"region":"us-east-1", "accessKeyId":"abc123", "secretAccessKey":"abc123"},
            "width": 720,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
        }
                """;

        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client
                .transcodeJob()
                .setApi(fakeApi)
                .webhooks(FAKE_WEBHOOKS)
                .fromS3(FAKE_AWS_REGION, FAKE_AWS_ACCESS_KEY_ID, FAKE_AWS_SECRET_ACCESS_KEY, "bucket", "key")
                .toS3(FAKE_AWS_REGION, FAKE_AWS_ACCESS_KEY_ID, FAKE_AWS_SECRET_ACCESS_KEY, "bucket", "outputKey");

        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");

        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }

    @Test
    void successIfValidArgumentsWithInputGcpAndOutputGcpWithNoWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "gcp://bucket/key",
            "outputUrl": "gcp://bucket/outputKey",
            "inputCreds": {"test":"123"},
            "outputCreds": {"test":"123"},
            "width": 720,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
        }
                """;

        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client
                .transcodeJob()
                .setApi(fakeApi)
                .webhooks(FAKE_WEBHOOKS)
                .fromGcp(FAKE_GCP_JSON, "bucket", "key")
                .toGcp(FAKE_GCP_JSON, "bucket", "outputKey");

        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");

        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }

    @Test
    void successIfValidArgumentsWithInputAzureAndOutputAzureWithNoWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "azure://bucket/key",
            "outputUrl": "azure://bucket/outputKey",
            "inputCreds": {"accountName":"mediamachine", "accountKey": "123"},
            "outputCreds": {"accountName":"mediamachine", "accountKey": "123"},
            "width": 720,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
        }
                """;

        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client
                .transcodeJob()
                .setApi(fakeApi)
                .webhooks(FAKE_WEBHOOKS)
                .fromAzure(FAKE_AZURE_ACCOUNT_NAME, FAKE_AZURE_ACCOUNT_KEY, "bucket", "key")
                .toAzure(FAKE_AZURE_ACCOUNT_NAME, FAKE_AZURE_ACCOUNT_KEY, "bucket", "outputKey");

        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");

        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }


    @Test
    void successIfValidArgumentsWithInputUrlAndOutputUrlWithTextWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "http://mediamachine.io/path/to/video.mp4",
            "outputUrl": "http://mediamachine.io/path/to/output",
            "width": 720,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "watermark": {
                "text": "mediamachine.io",
                "fontSize": 12,
                "fontColor": "white",
                "position": "bottomRight",
                "opacity": 0.9
            },
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
        }
        """;
        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client
                .transcodeJob()
                .setApi(fakeApi)
                .webhooks(FAKE_WEBHOOKS)
                .from(FAKE_INPUT_URL)
                .to(FAKE_OUTPUT_URL)
                .withTextWatermark("mediamachine.io");

        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");
        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }


    @Test
    void successIfValidArgumentsWithInputUrlAndOutputUrlWithImageUrlWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "http://mediamachine.io/path/to/video.mp4",
            "outputUrl": "http://mediamachine.io/path/to/output",
            "width": 720,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "watermark": {
                "imageUrl": "http://path.to/your/watermark/image",
                "height": 100,
                "width": 300,
                "position": "bottomRight",
                "opacity": 0.9
            },
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
        }
        """;
        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client
                .transcodeJob()
                .setApi(fakeApi)
                .webhooks(FAKE_WEBHOOKS)
                .from(FAKE_INPUT_URL)
                .to(FAKE_OUTPUT_URL)
                .withImageWatermark(FAKE_WATERMARK_IMAGE_URL);

        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");
        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }

    @Test
    void successIfValidArgumentsWithInputUrlAndOutputUrlWithNamedImageWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "http://mediamachine.io/path/to/video.mp4",
            "outputUrl": "http://mediamachine.io/path/to/output",
            "width": 720,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "watermark": {
                "imageName": "my-watermark-image",
                "height": 100,
                "width": 300,
                "position": "bottomRight",
                "opacity": 0.9
            },
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
        }
        """;
        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client
                .transcodeJob()
                .setApi(fakeApi)
                .webhooks(FAKE_WEBHOOKS)
                .from(FAKE_INPUT_URL)
                .to(FAKE_OUTPUT_URL)
                .withNamedWatermark("my-watermark-image");

        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");
        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }

    @Test
    void successIfValidArgumentsWithInputUrlAndOutputUrlWithCustomWidthAndNoWatermark() {
        String expectedJsonStr = """
        {
            "apiKey": "test-123",
            "inputUrl": "http://mediamachine.io/path/to/video.mp4",
            "outputUrl": "http://mediamachine.io/path/to/output",
            "width": 1024,
            "successUrl": "http://mediamachine.io/success",
            "failureUrl": "http://mediamachine.io/failure",
            "encoder": "h264",
            "bitrateKbps": "2000",
            "container": "mp4"
        }
        """;
        Gson gson = new Gson();
        Map<String, Object> expectedJsonMap = gson.fromJson(expectedJsonStr, Map.class);

        MediaMachine client = new MediaMachine(FAKE_API_KEY);
        MockAPI fakeApi = new MockAPI();

        TranscodeJobBuilder tjb = client.transcodeJob().setApi(fakeApi).webhooks(FAKE_WEBHOOKS).from(FAKE_INPUT_URL).to(FAKE_OUTPUT_URL).width(1024);
        assertDoesNotThrow(() -> {
            tjb.execute();
        }, "Should not throw an exception");
        Map<String, Object> fakeApiJsonMap = gson.fromJson(fakeApi.getBody(), Map.class);
        assertEquals(expectedJsonMap, fakeApiJsonMap);
        assertEquals(fakeApi.getJobType(), "transcode");
    }
}
