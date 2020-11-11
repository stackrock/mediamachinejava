package io.mediamachine.models;

import java.net.URL;

/**
 * Class to hold the Webhooks URL for success and failure.
 */
public class Webhooks {
    private URL successURL;
    private URL failureURL;

    private Webhooks() {}

    /**
     * Creates a new instance of Webhooks with the URLs initialized as empty URL.
     * @return
     */
    public static Webhooks withDefaults() {
        Webhooks instance = new Webhooks();
        return instance;
    }

    /**
     * Set the successURL for this Webhook.
     *
     * @param successURL an URL that represent the URL to call if the job finished successfully.
     * @return The Webhook instance configured with the successURL.
     */
    public Webhooks successURL(URL successURL) {
        this.successURL = successURL;
        return this;
    }

    /**
     * Set the failureURL for this Webhook
     *
     * @param failureURL an URL that represent the URL to call if the job fails.
     * @return The Webhook instance configured with the failureURL.
     */
    public Webhooks failureURL(URL failureURL) {
        this.failureURL = failureURL;
        return this;
    }

    public URL getSuccessURL() {
        return this.successURL;
    }

    public URL getFailureURL() {
        return this.failureURL;
    }
}
