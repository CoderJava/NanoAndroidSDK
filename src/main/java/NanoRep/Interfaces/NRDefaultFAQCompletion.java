package NanoRep.Interfaces;

import com.nanorep.nanorepsdk.Connection.NRError;

import NanoRep.ResponseParams.NRFAQCnf;

/**
 * Created by nissopa on 9/14/15.
 */
public interface NRDefaultFAQCompletion {
    void fetchDefaultFAQ(NRFAQCnf cnf, NRError error);
}
