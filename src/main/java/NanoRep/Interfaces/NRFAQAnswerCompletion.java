package nanorep.Interfaces;

import com.nanorep.nanorepsdk.Connection.NRError;

import nanorep.ResponseParams.NRFAQAnswer;

/**
 * Created by nissopa on 9/14/15.
 */
public interface NRFAQAnswerCompletion {
    void fetchAnswer(NRFAQAnswer answer, NRError error);
}
