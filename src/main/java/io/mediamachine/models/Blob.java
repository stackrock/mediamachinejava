package io.mediamachine.models;

/**
 * Blob that represent a file in Amazon/Azure/Google location.
 */
public class Blob {
    private Store store;
    private String bucket;
    private String key;
    private AWSCreds awsCreds;
    private AzureCreds azureCreds;
    private GCPCreds gcpCreds;

    private Blob() {}

    /**
     * Creates a new Blob instance with the default configuration.
     * @return A Blob instance with the default configuration.
     */
    public static Blob withDefaults() {
        Blob instance = new Blob();
        return instance;
    }

    /**
     * Creates a new Blob instance with the default configuration using the credentials provided.
     *
     * @param creds An Amazon/Azure/Google credentials used to configure this blob.
     * @return A Blob instance configure with the provided credentials.
     */
    public static Blob withCredentials(Credentials creds) {
        Blob instance = new Blob();
        switch (creds.storeType()) {
            case S3:
                instance.awsCreds = (AWSCreds) creds;
                instance.store = Store.S3;
                break;
            case AZURE:
                instance.azureCreds = (AzureCreds) creds;
                instance.store = Store.AZURE;
                break;
            case GOOGLE_BUCKET:
                instance.gcpCreds = (GCPCreds) creds;
                instance.store = Store.GOOGLE_BUCKET;
                break;
            default:
                throw new IllegalArgumentException("Invalid credential type");
        }

        return instance;
    }


    /**
     * Set the an Amazon credentials on this Blob.
     *
     * @param awsCreds An AWSCreds instance.
     * @return The Blob instance configured with amazon credentials.
     */
    public Blob credentials(AWSCreds awsCreds) {
        this.awsCreds = awsCreds;
        return this;
    }

    /**
     * Set the Azure credentials on this Blob.
     *
     * @param azureCreds An AzureCreds instance.
     * @return The Blob instance configured with Azure credentials.
     */
    public Blob credentials(AzureCreds azureCreds) {
        this.azureCreds = azureCreds;
        return this;
    }

    /**
     * Set the Google Cloud Platform Credentials on this Blob.
     *
     * @param gcpCreds A GCPCreds instance.
     * @return The Blob instance configured with GCP credentials.
     */
    public Blob credentials(GCPCreds gcpCreds) {
        this.gcpCreds = gcpCreds;
        return this;
    }

    /***
     * Set the Store type on this Blob.
     *
     * @param store a Store enum value representing the store of this Blob.
     * @return The Blob instance configured with the Store.
     */
    public Blob store(Store store) {
        this.store = store;
        return this;
    }

    /**
     * Set the bucket of this Blob.
     *
     * @param bucket A String representing the bucket.
     * @return The Blob instance configured with the bucket.
     */
    public Blob bucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    /**
     * Set the Key of this Blob.
     *
     * @param key A String representing the key.
     * @return The Blob instance configured with the bucket.
     */
    public Blob key(String key) {
        this.key = key;
        return this;
    }
}
