package io.mediamachine;

import com.google.gson.Gson;
import io.mediamachine.models.*;

/**
 * SummaryJobBuilder is a builder for a Summary Job.
 */
public class SummaryJobBuilder  extends AbstractJobBuilder<SummaryJobBuilder> {
    private transient SummaryType summaryType;
    private boolean removeAudio;

    protected SummaryJobBuilder(String apiKey) {
        // Set the defaults
        this.setApiKey(apiKey);
        this.removeAudio(false);
        this.width(720);
        this.type(SummaryType.GIF);
    }

    protected SummaryJobBuilder getThis() {
        return this;
    }


    /**
     * Configure the builder instance with the type of summary to generate.
     *
     * @param type a {@link io.mediamachine.models.SummaryType} describing if the summary should be a GIF or a MP4.
     * @return the {@link io.mediamachine.SummaryJobBuilder} instance configured with the correct type.
     */
    public SummaryJobBuilder type(SummaryType type) {
        this.summaryType = type;
        return this;
    }


    /**
     * Configure the builder instance to remove the audio from the the summary.
     * Keep in mind that you can set this to a GIF Summary, but will be a no-op.
     *
     * @param value a boolean that indicates if the service should remove the audio from the summary or not.
     * @return the {@link io.mediamachine.SummaryJobBuilder} instance configured to remove or not the audio.
     */
    public SummaryJobBuilder removeAudio(boolean value) {
        this.removeAudio = value;
        return this;
    }

    /**
     * Executes the job.
     *
     * @return a {@link io.mediamachine.models.Job} instance that represent the work being done on mediamachine services.
     * @throws {@link io.mediamachine.exceptions.ServiceException} if there is any problem with the underlying service call.
     */
    public Job execute() {
        if (summaryType == null) {
            throw new IllegalStateException("Missing type");
        }
        Gson gson = new Gson();
        String json = gson.toJson(this);
        String jobType = "gif_summary";
        if (summaryType == SummaryType.MP4) {
            jobType = "mp4_summary";
        }

        return runExecute(jobType, json);
    }
}
