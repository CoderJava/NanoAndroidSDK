package NanoRep;

import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.util.HashMap;
import java.util.Stack;

import NanoRep.Interfaces.NRDefaultFAQCompletion;
import NanoRep.Interfaces.NRFAQAnswerCompletion;
import NanoRep.Interfaces.NRFAQCompletion;
import NanoRep.Interfaces.NRLikeCompletion;
import NanoRep.RequestParams.NRFAQLikeParams;
import NanoRep.RequestParams.NRFAQParams;

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

    public void answerWithId(String answerId, NRFAQAnswerCompletion completion) {

    }

    public void faqLike(NRFAQLikeParams faqLikeParams, NRLikeCompletion completion) {

    }

    public void fetchDefaultFAQWithCompletion(NRDefaultFAQCompletion completion) {

    }
}
