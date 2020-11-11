package io.mediamachine.models;

import com.google.gson.annotations.SerializedName;

/**
 * Store to use on the Blob Object.
 */
public enum Store {
    /**
     * Amazon S3
     */
    @SerializedName("s3")
    S3,
    /**
     * Azure Blob
     */
    @SerializedName("azblob")
    AZURE,
    /**
     * Google Bucket.
     */
    @SerializedName("gcp")
    GOOGLE_BUCKET,
}
