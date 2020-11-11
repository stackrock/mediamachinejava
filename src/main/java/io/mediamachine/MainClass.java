package io.mediamachine;

import io.mediamachine.models.*;

import java.net.MalformedURLException;
import java.net.URL;

public class MainClass {
    // Fill in secrets from env
    private static final String ACCESS_KEY_ID = "";
    private static final String SECRET_ACCESS_KEY = "";
    private static final String REGION = "us-east-1";
    private static final String STACKROCK_IO_API_KEY = "";

    private static URL FAILURE_URL;
    private static URL SUCCESS_URL;

    static {
        try {
            FAILURE_URL = new URL("https://yourdomain.com/failure");
            SUCCESS_URL = new URL("https://yourdomain.com/success");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        thumbnailJob();
        summaryGifJob();
        summaryMp4Job();
        transcodeJob();
    }

    private static void thumbnailJob() {
        Webhooks webhooks = Webhooks.withDefaults().failureURL(FAILURE_URL).successURL(SUCCESS_URL);
        AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
        Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.webm");
        Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java.jpg");
        ThumbnailJobBuilder tj = ThumbnailJobBuilder.withDefaults().apiKey(STACKROCK_IO_API_KEY).webhook(webhooks).from(input).to(output).watermarkFromText("stackrock.io");
        Job job = tj.execute();
        Status status = job.status();
        System.out.println("-----> " + status.toString());
    }

    private static void summaryGifJob() {
        Webhooks webhooks = Webhooks.withDefaults().failureURL(FAILURE_URL).successURL(SUCCESS_URL);
        AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
        Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.webm");
        Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java.gif");
        SummaryJobBuilder sjg = SummaryJobBuilder
                .withDefaults()
                .apiKey(STACKROCK_IO_API_KEY)
                .webhook(webhooks)
                .type(SummaryType.GIF)
                .from(input)
                .to(output)
                .watermarkFromText("stackrock.io");
        Job job = sjg.execute();
        Status status = job.status();
        System.out.println("------>  " + status.toString());
    }

    private static void summaryMp4Job() {
        Webhooks webhooks = Webhooks.withDefaults().failureURL(FAILURE_URL).successURL(SUCCESS_URL);
        AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
        Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.webm");
        Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java.mp4");
        SummaryJobBuilder sjm = SummaryJobBuilder
                .withDefaults()
                .apiKey(STACKROCK_IO_API_KEY)
                .webhook(webhooks)
                .type(SummaryType.MP4)
                .from(input)
                .to(output)
                .watermarkFromText("stackrock.io")
                .removeAudio(true);
        Job job = sjm.execute();
        Status status = job.status();
        System.out.println("------> " + status.toString());
    }

    private static void transcodeJob() {
        Webhooks webhooks = Webhooks.withDefaults().failureURL(FAILURE_URL).successURL(SUCCESS_URL);
        AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
        Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.webm");
        Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java-transcoded.mp4");
        TranscodeOpts opts = TranscodeOpts.withDefaults().encoder(Encoder.H265).bitrate(BitrateKbps.FOUR_MBPS).container(Container.MP4).videoSize(VideoSize.VIDEO_SIZE_1080);
        TranscodeJobBuilder tj = TranscodeJobBuilder
                .withDefaults()
                .apiKey(STACKROCK_IO_API_KEY)
                .webhook(webhooks)
                .from(input)
                .to(output)
                .options(opts);
        Job job = tj.execute();
        Status status = job.status();
        System.out.println("-------> " + status.toString());

    }
}
