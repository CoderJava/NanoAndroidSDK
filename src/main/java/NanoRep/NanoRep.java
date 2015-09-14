package NanoRep;

import android.os.Handler;

import com.nanorep.nanorepsdk.Connection.NRConnection;
import com.nanorep.nanorepsdk.Connection.NRError;
import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import NanoRep.RequestParams.NRFAQLikeParams;
import NanoRep.RequestParams.NRFAQParams;
import NanoRep.RequestParams.NRSearchLikeParams;
import NanoRep.ResponseParams.NRFAQAnswer;
import NanoRep.ResponseParams.NRFAQCnf;
import NanoRep.ResponseParams.NRFAQDataObject;
import NanoRep.ResponseParams.NRSearchResponse;
import NanoRep.ResponseParams.NRSuggestions;

/**
 * Created by nissopa on 9/12/15.
 */
public class NanoRep {
    private String mAccountName;
    private HashMap<String, String> mContext;
    private String mSessionId;
    private float mDelay;
    private ArrayList<Object[]> mWaitingAPICalls;
    private Handler mHandler;

    // APIs
    private String FetchSessionIdAPI = "hello";
    private String KeepAliveAPI = "keepAlive";
    private String DieAPI = "die";
    private String SearchAPI = "q";
    private String SuggestionsAPI = "suggest";
    private String LikeAPI = "thumb";
    private String ChangeContext = "set";


    // Keys
    private String SessionIdKey = "sessionId";
    private String TimeoutKey = "timeout";
    private String SidKey = "sid";
    private String TextKey = "text";
    private String StemmingKey = "stemming";

    public interface NRSearchCompletion {
        public void response(NRSearchResponse response, NRError error);
    }

    public interface NRSuggestionsCompletion {
        public void suggustions(NRSuggestions suggestions, NRError error);
    }

    public interface NRLikeCompletion {
        public void likeResult(int type, boolean success);
    }

    public interface NRSuccessCompletion {
        public void didRequestSucceeded(boolean success);
    }

    public interface NRFAQCompletion {
        public void fetchFAQ(NRFAQDataObject faq, NRError error);
    }

    public interface NRFAQAnswerCompletion {
        public void fetchAnswer(NRFAQAnswer answer, NRError error);
    }

    public interface NRDefaultFAQCompletion {
        public void fetchDefaultFAQ(NRFAQCnf cnf, NRError error);
    }

    public NanoRep(String accountName, HashMap<String, String> context) {
        mAccountName = accountName;
        mContext = context;
    }

    public void searchText(String text, NRSearchCompletion completion) {

    }

    public void suggestionsForText(String text, NRSuggestionsCompletion completion) {

    }

    public void sendLike(NRSearchLikeParams likeParams, NRLikeCompletion completion) {

    }

    public void changeContext(HashMap<String, String> context, NRSuccessCompletion completion) {

    }

    public void faqWithParams(NRFAQParams params, NRFAQCompletion completion) {

    }

    public void answerWithId(String answerId, NRFAQAnswerCompletion completion) {

    }

    public void faqLike(NRFAQLikeParams faqLikeParams, NRLikeCompletion completion) {

    }

    public void fetchDefaultFAQWithCompletion(NRDefaultFAQCompletion completion) {

    }

    private ArrayList<Object[]> getWaitingAPICalls() {
        if (mWaitingAPICalls == null) {
            mWaitingAPICalls = new ArrayList<>();
        }
        return mWaitingAPICalls;
    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    private void startKeepAlive() {

    }

    private void callAPI(String apiName, HashMap<String, String> params, final NRConnection.NRConnectionListener completion) {
        if (mSessionId != null) {
            params.put("sid", mSessionId);
            api(apiName, params, completion);
        } else if (false) {
            // TODO: Check for connection
        } else {
            if (apiName != null && completion != null) {
                if (params == null) {
                    params = new HashMap<>();
                }
                Object[] arr = {apiName, params, completion};
                getWaitingAPICalls().add(arr);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("kb", "qa");
            map.put("nostats", "false");
            map.put("url", "Mobile");
            if (mContext != null) {
                map.put("ctx", NRUtilities.wrappedContext(mContext));
            }
            api(FetchSessionIdAPI, map, new NRConnection.NRConnectionListener() {
                @Override
                public void response(HashMap responseParam, NRError error) {
                    if (error != null) {
                        completion.response(null, error);
                    } else if (responseParam != null && responseParam.get(SessionIdKey) != null) {
                        NanoRep.this.mSessionId = (String)responseParam.get(SessionIdKey);
                        NanoRep.this.mDelay = Float.parseFloat((String)responseParam.get(TimeoutKey)) / 2;
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                NanoRep.this.startKeepAlive();
                            }
                        }, (int)NanoRep.this.mDelay);
                        ArrayList<Object[]> temp = new ArrayList<Object[]>(NanoRep.this.getWaitingAPICalls());
                        for (Object[] arr: temp) {
                            NanoRep.this.callAPI((String)arr[0], (HashMap)arr[1], (NRConnection.NRConnectionListener)arr[2]);
                            NanoRep.this.getWaitingAPICalls().remove(arr);
                        }
                        temp = null;
                        NanoRep.this.mWaitingAPICalls = null;
                    }
                }
            });
        }
    }

    private void api(String apiName, HashMap<String, String> params, NRConnection.NRConnectionListener completion) {
        NRConnection.connectionWithRequest(NRUtilities.buildURL(mAccountName, apiName, params), completion);
    }
}
