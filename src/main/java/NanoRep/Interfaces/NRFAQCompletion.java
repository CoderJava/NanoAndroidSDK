package nanorep.Interfaces;

import com.nanorep.nanorepsdk.Connection.NRError;

import nanorep.ResponseParams.NRFAQDataObject;

/**
 * Created by nissopa on 9/14/15.
 */
public interface NRFAQCompletion {
    void fetchFAQ(NRFAQDataObject faq, NRError error);
}
