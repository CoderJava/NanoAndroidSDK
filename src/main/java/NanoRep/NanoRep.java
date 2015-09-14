package NanoRep;

import com.nanorep.nanorepsdk.Connection.NRConnection;
import com.nanorep.nanorepsdk.Connection.NRError;
import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.util.HashMap;
import java.util.SimpleTimeZone;

import NanoRep.RequestParams.NRFAQLikeParams;
import NanoRep.RequestParams.NRFAQParams;
import NanoRep.RequestParams.NRSearchLikeParams;
import NanoRep.ResponseParams.NRFAQAnswer;
import NanoRep.ResponseParams.NRFAQAnswerItem;
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

    private void callAPI(String apiName, HashMap<String, String> params, NRConnection.NRConnectionListener completion) {
        if (mSessionId != null) {
            params.put("sid", mSessionId);
            api(apiName, params, completion);
        } else if (false) {
            // TODO: Check for connection
        } else {
            
        }
    }

    private void api(String apiName, HashMap<String, String> params, NRConnection.NRConnectionListener completion) {
        NRConnection.connectionWithRequest(NRUtilities.buildURL(mAccountName, apiName, params), completion);
    }
}
