package nanorep.Interfaces;

import com.nanorep.nanorepsdk.Connection.NRError;

import nanorep.ResponseParams.NRConfiguration;

/**
 * Created by nissopa on 9/14/15.
 */
public interface NRDefaultFAQCompletion {
    void fetchDefaultFAQ(NRConfiguration cnf, NRError error);
}
