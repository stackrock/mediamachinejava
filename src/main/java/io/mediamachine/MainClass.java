package io.mediamachine;

import io.mediamachine.models.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainClass {
    // Fill in secrets from env
    private static String ACCESS_KEY_ID = "";
    private static String SECRET_ACCESS_KEY = "";
    private static String REGION = "us-east-1";
    private static String MEDIAMACHINE_API_KEY = "";
    private static String INPUT_BUCKET = "";
    private static String INPUT_KEY = "";
    private static String OUTPUT_BUCKET = "";
    private static String OUTPUT_KEY = "";

    static {
        Map<String, String> env = System.getenv();
        ACCESS_KEY_ID = env.get("AWS_ACCESS_KEY_ID");
        SECRET_ACCESS_KEY = env.get("AWS_SECRET_ACCESS_KEY");
        REGION = env.get("AWS_REGION");
        MEDIAMACHINE_API_KEY = env.get("MEDIAMACHINE_API_KEY");
        INPUT_BUCKET = env.get("AWS_BUCKET");
        INPUT_KEY = env.get("AWS_INPUT_KEY");
        OUTPUT_BUCKET = env.get("AWS_BUCKET");
        OUTPUT_KEY = env.get("AWS_OUTPUT_KEY");
    }


    public static void main(String[] args) throws InterruptedException {
//        thumbnailJob();
//        summaryGifJob();
//        summaryMp4Job();
        transcodeJob();
    }

    private static void thumbnailJob() throws InterruptedException {
        MediaMachine client = new MediaMachine(MEDIAMACHINE_API_KEY);
        ThumbnailJobBuilder tj = client
                .thumbnailJob()
                .fromS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, INPUT_BUCKET, INPUT_KEY)
                .toS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, OUTPUT_BUCKET, OUTPUT_KEY)
                .withTextWatermark("mediamachine.io");

        Job job = tj.execute();
        Status status = job.status();
        while(status == Status.IN_PROGRESS) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("waiting...");
            status = job.status();
        }
        System.out.println("---> done");
    }

    private static void summaryGifJob() throws InterruptedException {
        MediaMachine client = new MediaMachine(MEDIAMACHINE_API_KEY);

        SummaryJobBuilder summaryJobBuilder = client
                .summaryGifJob()
                .fromS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, INPUT_BUCKET, INPUT_KEY)
                .toS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, INPUT_BUCKET, OUTPUT_KEY + ".gif")
                .withTextWatermark("mediamachine.io");

        Job job = summaryJobBuilder.execute();
        Status status = job.status();

        while(status == Status.IN_PROGRESS) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("waiting...");
            status = job.status();
        }
        System.out.println("---> done");
    }


    private static void summaryMp4Job() throws InterruptedException {
        MediaMachine client = new MediaMachine(MEDIAMACHINE_API_KEY);

        SummaryJobBuilder summaryJobBuilder = client
                .summaryMp4Job()
                .fromS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, INPUT_BUCKET, INPUT_KEY)
                .toS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, INPUT_BUCKET, OUTPUT_KEY + ".mov")
                .withTextWatermark("mediamachine.io");

        Job job = summaryJobBuilder.execute();
        Status status = job.status();

        while(status == Status.IN_PROGRESS) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("waiting...");
            status = job.status();
        }
        System.out.println("---> done");
    }

    private static void transcodeJob() throws InterruptedException {
        MediaMachine client = new MediaMachine(MEDIAMACHINE_API_KEY);

        TranscodeJobBuilder transcodeJobBuilder = client
                .transcodeJob()
                .bitrate(BitrateKbps.TWO_MBPS)
                .container(Container.WEBM)
                .encoder(Encoder.VP8)
                .fromS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, INPUT_BUCKET, INPUT_KEY)
                .toS3(REGION, ACCESS_KEY_ID, SECRET_ACCESS_KEY, INPUT_BUCKET, OUTPUT_KEY + ".webm")
                .withTextWatermark("mediamachine.io");

        Job job = transcodeJobBuilder.execute();
        Status status = job.status();

        while(status == Status.IN_PROGRESS) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("waiting...");
            status = job.status();
        }
        System.out.println("---> done");
    }
}
