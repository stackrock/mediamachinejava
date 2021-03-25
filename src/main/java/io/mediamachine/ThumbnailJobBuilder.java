package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.exceptions.ServiceException;
import io.mediamachine.models.*;
import io.mediamachine.utils.API;
import io.mediamachine.utils.RealAPI;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * ThumbnailJobBuilder is a builder for a Thumbnail Job.
 */
public class ThumbnailJobBuilder extends AbstractJobBuilder<ThumbnailJobBuilder> {
    ThumbnailJobBuilder(String apiKey) {
        // Set the defaults
        this.setApiKey(apiKey);
        this.width(720);
    }

    protected ThumbnailJobBuilder getThis() {
        return this;
    }

    /**
     * Executes the Job.
     *
     * @return A {@link io.mediamachine.models.Job} object that can be used to query the status of the job.
     * @throws {@link java.lang.IllegalStateException} if there is any missing configuration.
     */
    public Job execute()  {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        String jobType = "thumbnail";

        return runExecute(jobType, json);
    }
}
