package NanoRep.Interfaces;

import com.nanorep.nanorepsdk.Connection.NRError;

import NanoRep.ResponseParams.NRFAQDataObject;

/**
 * Created by nissopa on 9/14/15.
 */
public interface NRFAQCompletion {
    public void fetchFAQ(NRFAQDataObject faq, NRError error);
}
