package NanoRep;

import com.nanorep.nanorepsdk.Connection.NRConnection;
import com.nanorep.nanorepsdk.Connection.NRError;
import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.util.HashMap;
import java.util.Stack;

import NanoRep.Interfaces.NRDefaultFAQCompletion;
import NanoRep.Interfaces.NRFAQAnswerCompletion;
import NanoRep.Interfaces.NRFAQCompletion;
import NanoRep.Interfaces.NRLikeCompletion;
import NanoRep.RequestParams.NRFAQLikeParams;
import NanoRep.RequestParams.NRFAQParams;
import NanoRep.ResponseParams.NRFAQAnswer;
import NanoRep.ResponseParams.NRFAQCnf;

/**
 * Created by nissopa on 9/12/15.
 */
public class NanoRepFAQ {
    private String mAccountName;
    private String mReferer;
    private HashMap<String, String> mDefaultParams;

    public NanoRepFAQ(String accountName) {
        mAccountName = accountName;
    }

    private String getReferer() {
        if (mReferer == null) {
            mReferer = NRUtilities.buildReferer("mobile");
        } else {
            mReferer = NRUtilities.buildReferer(mReferer);
        }
        return mReferer;
    }

    private HashMap<String, String> getDefaultParams() {
        if (mDefaultParams == null) {
            mDefaultParams = new HashMap<String, String>();
            mDefaultParams.put("account", mAccountName);
            mDefaultParams.put("referer", getReferer());
        }
        return mDefaultParams;
    }

    public void setReferer(String referer) {
        mReferer = referer;
    }

    public void faqWithParams(NRFAQParams params, NRFAQCompletion completion) {

    }

    public void answerWithId(String answerId, final NRFAQAnswerCompletion completion) {
        HashMap<String, String> params = getDefaultParams();
        params.put("id", answerId);
        params.put("i", Integer.toString(0));
        params.put("api", "answer");
        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.NRConnectionListener() {
            @Override
            public void response(HashMap responseParam, NRError error) {
                if (error != null) {
                    completion.fetchAnswer(null, error);
                } else if (responseParam != null){
                    completion.fetchAnswer(new NRFAQAnswer(responseParam), null);
                }
            }
        });
    }

    public void faqLike(NRFAQLikeParams faqLikeParams, NRLikeCompletion completion) {

    }

    public void fetchDefaultFAQWithCompletion(final NRDefaultFAQCompletion completion) {
        HashMap<String, String> params = getDefaultParams();
        params.put("api", "cnf");
        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.NRConnectionListener() {
            @Override
            public void response(HashMap responseParam, NRError error) {
                if (error != null) {
                    // TODO: fetch from cache
                    completion.fetchDefaultFAQ(null, error);
                } else {
                    // TODO: cache the response
                    completion.fetchDefaultFAQ(new NRFAQCnf(responseParam), null);
                }
                mDefaultParams = null;
            }
        });
    }
}
