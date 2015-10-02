package NanoRep;

import android.os.Handler;
import android.util.Log;

import com.nanorep.nanorepsdk.Connection.NRConnection;
import com.nanorep.nanorepsdk.Connection.NRError;
import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import NanoRep.Interfaces.NRDefaultFAQCompletion;
import NanoRep.Interfaces.NRFAQAnswerCompletion;
import NanoRep.Interfaces.NRFAQCompletion;
import NanoRep.Interfaces.NRLikeCompletion;
import NanoRep.Interfaces.NRSuccessCompletion;
import NanoRep.RequestParams.NRFAQLikeParams;
import NanoRep.RequestParams.NRFAQParams;
import NanoRep.RequestParams.NRSearchLikeParams;
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
    private HashMap<String, NRSearchResponse> mCachedSearches;
    private HashMap<String, NRSuggestions> mCachedSuggestions;
    private Handler mHandler;
    private String mReferer;
    private NanoRepFAQ mFAQ;

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
        public void searchResponse(NRSearchResponse response, NRError error);
    }

    public interface NRSuggestionsCompletion {
        public void suggustions(NRSuggestions suggestions, NRError error);
    }

    public NanoRep(String accountName, HashMap<String, String> context) {
        mAccountName = accountName;
        mContext = context;
    }

    public void setReferer(String referer) {
        mReferer = referer;
    }

    public void searchText(final String text, final NRSearchCompletion completion) {
        if (mCachedSearches != null && mCachedSearches.get(text) != null) {
            completion.searchResponse(mCachedSearches.get(text), null);
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put(TextKey, text);
            callAPI(SearchAPI, params, new NRConnection.NRConnectionListener() {
                @Override
                public void response(HashMap responseParam, NRError error) {
                    if (error != null) {
                        completion.searchResponse(null, error);
                    } else if (responseParam != null) {
                        NRSearchResponse response = new NRSearchResponse(responseParam);
                        NanoRep.this.getCachedSearches().put(text, response);
                        completion.searchResponse(response, null);
                    }
                }
            });
        }
    }

    public void suggestionsForText(final String text, final NRSuggestionsCompletion completion) {
        if (mCachedSuggestions != null && mCachedSuggestions.get(text) != null) {
            completion.suggustions(mCachedSuggestions.get(text), null);
        } else {
            final HashMap<String, String> params = new HashMap<>();
            String encodedText = null;
            try {
                encodedText = URLEncoder.encode(text, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(TextKey, encodedText != null ? encodedText : text);
            params.put(StemmingKey, "false");
            callAPI(SuggestionsAPI, params, new NRConnection.NRConnectionListener() {
                @Override
                public void response(HashMap responseParam, NRError error) {
                    if (responseParam != null) {
                        ArrayList<String> answers = (ArrayList)responseParam.get("a");
                        if (answers != null) {
                            ArrayList<String> arr = new ArrayList<String>();
                            for (String answer: answers) {
                                String[] pipes = answer.split("\\|");
                                String parsedAnswer = "";
                                for (String comma: pipes) {
                                    String[] firstWords = comma.split(",");
                                    parsedAnswer += firstWords[0] + " ";
                                }
                                parsedAnswer = parsedAnswer.substring(0, parsedAnswer.length() - 1);
                                arr.add(parsedAnswer);
                            }
                            responseParam.put("a", arr);
                            NanoRep.this.getCachedSuggestions().put(text, new NRSuggestions(responseParam));
                        }
                        completion.suggustions(NanoRep.this.mCachedSuggestions.get(text), null);
                    }
                }
            });
        }
    }

    public void sendLike(NRSearchLikeParams likeParams, NRLikeCompletion completion) {

    }

    public void changeContext(HashMap<String, String> context, NRSuccessCompletion completion) {

    }

    public void faqWithParams(NRFAQParams params, NRFAQCompletion completion) {
        getFAQ().faqWithParams(params, completion);
    }

    public void answerWithId(String answerId, NRFAQAnswerCompletion completion) {
        getFAQ().answerWithId(answerId, completion);
    }

    public void faqLike(NRFAQLikeParams faqLikeParams, NRLikeCompletion completion) {
        getFAQ().faqLike(faqLikeParams, completion);
    }

    public void fetchDefaultFAQWithCompletion(NRDefaultFAQCompletion completion) {
        getFAQ().fetchDefaultFAQWithCompletion(completion);
    }

    private ArrayList<Object[]> getWaitingAPICalls() {
        if (mWaitingAPICalls == null) {
            mWaitingAPICalls = new ArrayList<>();
        }
        return mWaitingAPICalls;
    }

    private HashMap<String, NRSearchResponse> getCachedSearches() {
        if (mCachedSearches == null) {
            mCachedSearches = new HashMap<>();
        }
        return mCachedSearches;
    }

    private HashMap<String, NRSuggestions> getCachedSuggestions() {
        if (mCachedSuggestions == null) {
            mCachedSuggestions = new HashMap<>();
        }
        return mCachedSuggestions;
    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    public NanoRepFAQ getFAQ() {
        if (mFAQ == null) {
            mFAQ = new NanoRepFAQ(mAccountName);
            mFAQ.setReferer(mReferer);
        }
        return mFAQ;
    }

    private void startKeepAlive() {
        callAPI(KeepAliveAPI, new HashMap<String, String>(), new NRConnection.NRConnectionListener() {
            @Override
            public void response(HashMap responseParam, NRError error) {
                if (error != null) {
                    Log.d("Keep Alive Error", error.getDescription());
                } else if (responseParam != null) {
                    getHandler().postDelayed(new Runnable() {
                        public void run() {
                            NanoRep.this.startKeepAlive();
                        }
                    }, (long) NanoRep.this.mDelay);
                }
            }
        });
    }

    private void callAPI(String apiName, HashMap<String, String> params, final NRConnection.NRConnectionListener completion) {
        if (mSessionId != null) {
            params.put(SidKey, mSessionId);
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
                        NanoRep.this.mDelay = (((Integer)responseParam.get(TimeoutKey)).floatValue() / 2) * 1000;
//                        getHandler().postDelayed(new Runnable() {
//                            public void run() {
//                                NanoRep.this.startKeepAlive();
//                            }
//                        }, (long) NanoRep.this.mDelay);
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
        String link = NRUtilities.buildURL(mAccountName, apiName, params);
        NRConnection.connectionWithRequest(NRUtilities.getRequest(link, mReferer), completion);
    }
}
