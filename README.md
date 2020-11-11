# Mediamachine Java client

## Maven ?

## Ant ?

## Gradle ?

## Usage

### Thumbnail Job

```java
AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.web");
Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java.jpg");
ThumbnailJobBuilder tj = ThumbnailJobBuilder.withDefaults().apiKey(STACKROCK_IO_API_KEY).from(input).to(output).watermarkFromText("stackrock.io");
tj.execute();
```

### Summary Gif Job

```java
AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.web");
Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java.gif");
SummaryJobBuilder sjg = SummaryJobBuilder.withDefaults().apiKey(STACKROCK_IO_API_KEY).type(SummaryType.GIF).from(input).to(output).watermarkFromText("stackrock.io");
sjg.execute();
```

### Summary Mp4 Job

```java
AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.web");
Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java.mp4");
SummaryJobBuilder sjm = SummaryJobBuilder.withDefaults().apiKey(STACKROCK_IO_API_KEY).type(SummaryType.MP4).from(input).to(output).watermarkFromText("stackrock.io").removeAudio(true);
sjm.execute();
```

### Transcode Job

```java
AWSCreds creds = new AWSCreds(ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION);
Blob input = Blob.withCredentials(creds).bucket("chechito-test").key("test.web");
Blob output = Blob.withCredentials(creds).bucket("chechito-test").key("test-java-transcoded.mp4");
TranscodeOpts opts = TranscodeOpts.withDefaults().encoder(Encoder.H265).bitrate(BitrateKbps.FOUR_MBPS).container(Container.MP4).videoSize(VideoSize.VIDEO_SIZE_1080);
TranscodeJobBuilder tj = TranscodeJobBuilder.withDefaults().apiKey(STACKROCK_IO_API_KEY).from(input).to(output).options(opts);
tj.execute();
```