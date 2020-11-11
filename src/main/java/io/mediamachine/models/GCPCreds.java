package io.mediamachine.models;

/**
 * Google Cloud Platform credentials.
 */
public class GCPCreds implements Credentials{
    private String json;

    /**
     * Creates a new Google Cloud Platform credential with the provided json.
     *
     * @param json A String representation of the JSON file needed to access the Google Cloud Platform.
     */
    public GCPCreds(String json) {
        this.json = json;
    }

    public Store storeType() {
        return Store.GOOGLE_BUCKET;
    }

}
