package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.exceptions.ServiceException;
import io.mediamachine.models.*;
import io.mediamachine.utils.API;
import io.mediamachine.utils.RealAPI;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;

/**
 * Abstract class that has all the common methods for all the jobs.
 *
 * @param <T> a Class that extends AbstractJobBuilder
 */
public abstract class AbstractJobBuilder<T extends AbstractJobBuilder<T>> {
    private String apiKey;
    private URL failureUrl;
    private URL successUrl;
    private String inputUrl;
    private String outputUrl;
    private Object inputCreds; // this can store a Credentials or a String
    private Object outputCreds; // this can store a Credentials or a String
    private Integer width;
    private Watermark watermark;
    private transient API api = new RealAPI();


    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    protected abstract T getThis();

    T setApi(API api) {
        this.api = api;
        return getThis();
    }

    /**
     * Set the webhooks to call when the job is done.
     *
     * @param webhooks the {@link io.mediamachine.models.Webhooks} to call when the job is done.
     * @return the instance of the JobBuilder configured with the webhooks.
     */
    public T webhooks(Webhooks webhooks) {
        this.failureUrl = webhooks.getFailureURL();
        this.successUrl = webhooks.getSuccessURL();

        return getThis();
    }

    /**
     * Set the width of the output.
     *
     * @param width a {@link java.lang.Integer} for the output.
     * @return the instance of the JobBuilder configured with the width.
     */
    public T width(Integer width) {
        this.width = width;
        return getThis();
    }

    /**
     * Set the url with the location of the input.
     *
     * To know more about the format you can check: https://mediamachine.io/docs/api/thumbnail#url-string.
     *
     * @param input a {@link java.lang.String} representing the location of the input file.
     * @return the instance of the JobBuilder configured with the input location.
     */
    public T from(String input) {
        this.inputUrl = input;
        return getThis();
    }

    /**
     * Set the input as a file located on S3.
     *
     * @param region a {@link java.lang.String} with the AWS Region of the account that has the file.
     * @param accessKeyId a {@link java.lang.String} with the AWS accessKeyId of the account that has the file.
     * @param secretAccessKey a {@link java.lang.String} with the AWS secretAccessKey of the account that has the file.
     * @param bucket a {@link java.lang.String} with the S3 bucket where the file is located.
     * @param key a {@link java.lang.String} with the S3 key where the file is located.
     * @return the instance of the JobBuilder configured with the input location.
     */
    public T fromS3(String region, String accessKeyId, String secretAccessKey, String bucket, String key) {
        AWSCreds creds = new AWSCreds(accessKeyId, secretAccessKey, region);
        this.from(String.format("s3://%s/%s", bucket, key));
        this.inputCreds = creds;
        return getThis();
    }

    /**
     * Set the input as a file located on GCP.
     *
     * @param json a {@link java.lang.String} with the JSON credentials of the GCP account.
     * @param bucket a {@link java.lang.String} with the GCP bucket where the file is located.
     * @param key a {@link java.lang.String} with the GCP key where the file is located.
     * @return the instance of the JobBuilder configured with the input location.
     */
    public T fromGcp(String json, String bucket, String key) {
        this.from(String.format("gcp://%s/%s", bucket, key));
        Gson gson = new Gson();
        Map<String, Object> jsonObj = gson.fromJson(json, Map.class);
        this.inputCreds = jsonObj;
        return getThis();
    }


    /**
     * Set the input as a file located on Azure.
     *
     * @param accountName a {@link java.lang.String} with the Azure accountName of the account that has the file.
     * @param accountKey a {@link java.lang.String} with the Azure accountKey of the account that has the file.
     * @param bucket a {@link java.lang.String} with the Azure bucket where the file is located.
     * @param key a {@link java.lang.String} with the Azure key where the file is located.
     * @return the instance of the JobBuilder configured with the input location.
     */
    public T fromAzure(String accountName, String accountKey, String bucket, String key) {
        AzureCreds creds = new AzureCreds(accountName, accountKey);
        this.from(String.format("azure://%s/%s", bucket, key));
        this.inputCreds = creds;
        return getThis();
    }


    /**
     * Set the output as a file located on S3.
     *
     * @param region a {@link java.lang.String} with the AWS Region of the account where the file should be saved.
     * @param accessKeyId a {@link java.lang.String} with the AWS accessKeyId of the account.
     * @param secretAccessKey a {@link java.lang.String} with the AWS secretAccessKey of the account.
     * @param bucket a {@link java.lang.String} with the S3 bucket where the file should be saved.
     * @param key a {@link java.lang.String} with the S3 key where the file should be saved.
     * @return the instance of the JobBuilder configured with the output location.
     */
    public T toS3(String region, String accessKeyId, String secretAccessKey, String bucket, String key) throws  IllegalStateException {
        AWSCreds creds = new AWSCreds(accessKeyId, secretAccessKey, region);
        this.to(String.format("s3://%s/%s", bucket, key));
        this.outputCreds = creds;
        return getThis();
    }


    /**
     * Set the output as a file located on GCP.
     *
     * @param json a {@link java.lang.String} with the JSON credentials of the GCP account.
     * @param bucket a {@link java.lang.String} with the GCP bucket where the file should be saved.
     * @param key a {@link java.lang.String} with the GCP key where the file should be saved.
     * @return the instance of the JobBuilder configured with the output location.
     */
    public T toGcp(String json, String bucket, String key) {
        this.to(String.format("gcp://%s/%s", bucket, key));
        Gson gson = new Gson();
        Map<String, Object> jsonObj = gson.fromJson(json, Map.class);
        this.outputCreds = jsonObj;
        return getThis();
    }

    /**
     * Set the output as a file located on Azure.
     *
     * @param accountName a {@link java.lang.String} with the Azure accountName of the account where the file should be saved.
     * @param accountKey a {@link java.lang.String} with the Azure accountKey of the account where the file should be saved.
     * @param bucket a {@link java.lang.String} with the Azure bucket where the file should be saved.
     * @param key a {@link java.lang.String} with the Azure key where the file should be saved.
     * @return the instance of the JobBuilder configured with the output location.
     */
    public T toAzure(String accountName, String accountKey, String bucket, String key) {
        AzureCreds creds = new AzureCreds(accountName, accountKey);
        this.to(String.format("azure://%s/%s", bucket, key));
        this.outputCreds = creds;
        return getThis();
    }

    /**
     * Set the url with the location where the output should be saved.
     *
     * To know more about the format you can check: https://mediamachine.io/docs/api/thumbnail#url-string.
     *
     * @param output a {@link java.lang.String} representing the location of the output file.
     * @return the instance of the JobBuilder configured with the output location.
     */
    public T to(String output) {
        this.outputUrl = output;
        return getThis();
    }

    /**
     * Configure the output to have a watermark text.
     *
     * This method use the default watermark configuration for the watermark text:
     *  - font size: 12px
     *  - font color: "white"
     *  - position: {@link io.mediamachine.models.Position#BOTTOM_RIGHT}.
     *  - opacity: 90% (0.9)
     *
     * @param text a {@link java.lang.String} with the text to use as a watermark.
     * @return the instance of the JobBuilder configured with the text watermark.
     */
    public T withTextWatermark(String text) {
        Watermark watermark = WatermarkText.withDefaults().text(text);
        this.watermark = watermark;
        return getThis();
    }

    /**
     * Configure the output to have an image watermark.
     *
     * This method use the default watermark configuration for the watermark image:
     *  - position: {@link io.mediamachine.models.Position#BOTTOM_RIGHT}.
     *  - opacity: 90% (0.9)
     *
     * @param url a {@link java.net.URL} with url of the image to use a watermark.
     * @return the instance of the JobBuilder configured with the image watermark.
     */
    public T withImageWatermark(URL url) {
        Watermark watermark = WatermarkImage.withDefaults().url(url);
        this.watermark = watermark;
        return getThis();
    }

    /**
     * Configure the output to have an named image watermark.
     *
     * This method use the default watermark configuration for the watermark image:
     *  - position: {@link io.mediamachine.models.Position#BOTTOM_RIGHT}.
     *  - opacity: 90% (0.9)
     *
     * @param imageName a {@link java.lang.String} with name of the image uploaded to mediamachine.io to use as watermark.
     * @return the instance of the JobBuilder configured with the named image watermark.
     */
    public T withNamedWatermark(String imageName) {
        Watermark watermark = WatermarkNamedImage.withDefaults().name(imageName);
        this.watermark = watermark;
        return getThis();
    }

    /**
     * Configure the output to use the provided {@link io.mediamachine.models.Watermark}.
     * @param watermark a {@link io.mediamachine.models.Watermark} instance with the configuration of the watermark to use.
     * @return the instance of the JobBuilder configured with the watermark.
     */
    public T watermark(Watermark watermark) {
        this.watermark = watermark;
        return getThis();
    }

    public abstract Job execute();

    public Job runExecute(String jobType, String json) {
        if (inputUrl == null) {
            throw new IllegalStateException("Missing from");
        }

        if (outputUrl == null) {
            throw new IllegalStateException("Missing to");
        }

        try {
            Job job = this.api.createJob(jobType, json);
            return job;
        } catch (FileNotFoundException e) {
            throw new ServiceException("There was a problem creating a Job with our services:", e);
        }
    }
}
