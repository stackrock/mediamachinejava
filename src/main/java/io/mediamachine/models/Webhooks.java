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
     * Creates a new instance of {@link io.mediamachine.models.Webhooks} with the URLs initialized as empty URL.
     *
     * @return a {@link io.mediamachine.models.Webhooks} instance.
     */
    public static Webhooks withDefaults() {
        Webhooks instance = new Webhooks();
        return instance;
    }

    /**
     * Set the successURL for this Webhook.
     *
     * @param successURL an {@link java.net.URL} that represent the URL to call if the job finished successfully.
     * @return The {@link io.mediamachine.models.Webhooks} instance configured with the successURL.
     */
    public Webhooks successURL(URL successURL) {
        this.successURL = successURL;
        return this;
    }

    /**
     * Set the failureURL for this Webhook
     *
     * @param failureURL an {@link java.net.URL} that represent the URL to call if the job fails.
     * @return The {@link io.mediamachine.models.Webhooks} instance configured with the failureURL.
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
