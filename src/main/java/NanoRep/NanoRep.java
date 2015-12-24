package NanoRep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import NanoRep.Interfaces.NRSpeechRecognizerCompletion;
import NanoRep.Interfaces.NRSuccessCompletion;
import NanoRep.RequestParams.NRFAQLikeParams;
import NanoRep.RequestParams.NRFAQParams;
import NanoRep.RequestParams.NRSearchLikeParams;
import NanoRep.ResponseParams.NRFAQCnf;
import NanoRep.ResponseParams.NRSearchResponse;
import NanoRep.ResponseParams.NRSuggestions;

/**
 * Created by nissopa on 9/12/15.
 */
public class NanoRep {
    private String mAccountName;
    private HashMap<String, String> mNanoContext;
    private String mSessionId;
    private float mDelay;
    private ArrayList<Object[]> mWaitingAPICalls;
    private HashMap<String, NRSearchResponse> mCachedSearches;
    private HashMap<String, NRSuggestions> mCachedSuggestions;
    private String mReferer;
    private NanoRepFAQ mFAQ;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private Context mContext;
    private String mDomain;
    private String mKnowledgeBase;

    private static Context sContext;
    private static String sAccountName;
    private static String sDomain;


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

    /**
     *
     * This method will initiate the nanorep session
     *
     * @param context App context that you can get from the Application class
     * @param domain User domain, where the nanorep data is stored
     */
    public static  void initializeNanorep(Context context, String account, String domain) {
        sContext = context;
        sAccountName = account;
        sDomain = domain;
        NanoRep defaultCNF = new NanoRep(null, null);
        defaultCNF.fetchDefaultFAQWithCompletion(new NRDefaultFAQCompletion() {
            @Override
            public void fetchDefaultFAQ(NRFAQCnf cnf, NRError error) {

            }
        });
    }

    /**
     * This constructor configures a NanoRep API wrapper.
     *
     * @param knowledgeBase The knowledge base name.
     * @param nanoContext If you want to use a specific context you can set it by key-value.
     */
    public NanoRep(String knowledgeBase, HashMap<String, String> nanoContext) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mContext = sContext;
        mAccountName = sAccountName;
        mNanoContext = nanoContext;
        mDomain = sDomain;
        mKnowledgeBase = knowledgeBase;
    }

    /**
     *
     * @param referer User custom referrer
     */
    public void setReferer(String referer) {
        mReferer = referer;
    }


    /**
     * Fetches search results
     *
     * @param text The text for search query
     * @param completion Callback which contains NRSearchResponse object if the search is successful or NRError object if the search fails.
     */
    public void searchText(final String text, final NRSearchCompletion completion) {
        if (mCachedSearches != null && mCachedSearches.get(text) != null) {
            completion.searchResponse(mCachedSearches.get(text), null);
        } else {
            HashMap<String, String> params = new HashMap<>();
            String encodedText = null;
            try {
                encodedText = URLEncoder.encode(text, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(TextKey, encodedText != null ? encodedText : text);
            params.put(NRUtilities.ApiNameKey, SearchAPI);
            callAPI(params, new NRConnection.NRConnectionListener() {
                @Override
                public void response(Object responseParam, NRError error) {
                    if (error != null) {
                        completion.searchResponse(null, error);
                    } else if (responseParam != null) {
                        NRSearchResponse response = new NRSearchResponse((HashMap)responseParam);
                        NanoRep.this.getCachedSearches().put(text, response);
                        completion.searchResponse(response, null);
                    }
                }
            });
        }
    }

    /**
     * Fetches suggestions for search, helps to speed up the search operation
     *
     * @param text The text for suggestion query
     * @param completion Callback which contains NRSuggestions object if the search is successful or NRError object if it fails.
     */
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
            params.put(NRUtilities.ApiNameKey, SuggestionsAPI);
            callAPI(params, new NRConnection.NRConnectionListener() {
                @Override
                public void response(Object responseParam, NRError error) {
                    if (responseParam != null) {
                        ArrayList<String> answers = (ArrayList) ((HashMap)responseParam).get("a");
                        if (answers != null) {
                            ArrayList<String> arr = new ArrayList<String>();
                            for (String answer : answers) {
                                String[] pipes = answer.split("\\|");
                                String parsedAnswer = "";
                                for (String comma : pipes) {
                                    String[] firstWords = comma.split(",");
                                    parsedAnswer += firstWords[0] + " ";
                                }
                                parsedAnswer = parsedAnswer.substring(0, parsedAnswer.length() - 1);
                                arr.add(parsedAnswer);
                            }
                            ((HashMap)responseParam).put("a", arr);
                            NanoRep.this.getCachedSuggestions().put(text, new NRSuggestions((HashMap)responseParam));
                        }
                        completion.suggustions(NanoRep.this.mCachedSuggestions.get(text), null);
                    }
                }
            });
        }
    }

    /**
     * Updates the likes value of search result.
     *
     * @param likeParams Like params object
     * @param completion Callback contains the like's type and success status
     */
    public void sendLike(NRSearchLikeParams likeParams, final NRLikeCompletion completion) {
        HashMap<String, String> params = likeParams.getParams();
        params.put(NRUtilities.ApiNameKey, LikeAPI);
        callAPI(params, new NRConnection.NRConnectionListener() {
            @Override
            public void response(Object responseParam, NRError error) {
                if (error != null) {
                    completion.likeResult(0, false);
                } else {
                    completion.likeResult(((Integer) ((HashMap)responseParam).get("type")).intValue(), ((HashMap)responseParam).get("result").equals("True"));
                }
            }
        });
    }

    /**
     * Changes the context of NanoRep API wrapper
     *
     * @param context Your widget context
     * @param completion Callback which contains boolean for success/fail
     */
    public void changeContext(HashMap<String, String> context, NRSuccessCompletion completion) {

    }

    /**
     * Fetches list of FAQ according to the params values
     *
     * @param params FAQ params object.
     * @param completion Callback which contains NRFAQDataObject in case of successful response or NRError in case of unsuccessful response.
     */
    public void faqWithParams(NRFAQParams params, NRFAQCompletion completion) {
        getFAQ().faqWithParams(params, completion);
    }

    /**
     * Fetches specific answer by the Id value (which you get when fetching FAQ)
     *
     * @param answerId Id of the answer
     * @param completion Callback which contains the answer object in case of successful response and NSError in case of unsuccessful response.
     */
    public void answerWithId(String answerId, NRFAQAnswerCompletion completion) {
        getFAQ().answerWithId(answerId, completion);
    }

    /**
     * Updates the likes value of an answer
     *
     * @param faqLikeParams FAQ Like params object
     * @param completion Callback contains the like's type and success status
     */
    public void faqLike(NRFAQLikeParams faqLikeParams, NRLikeCompletion completion) {
        getFAQ().faqLike(faqLikeParams, completion);
    }

    /**
     * Fetches the default FAQ of an account (according to the customValue)
     *
     * @param completion Callback which contains the NRFAQCnf object in case of successful response and NRError in case of unsuccessful
     */
    public void fetchDefaultFAQWithCompletion(NRDefaultFAQCompletion completion) {
        getFAQ().fetchDefaultFAQWithCompletion(mKnowledgeBase, completion);
    }


    /**
     * Starts voice recognition and fetches the text by the completion callback
     *
     * @param completion Callback that fetches the recorded text in String.
     */
    public void startVoiceRecognition(final NRSpeechRecognizerCompletion completion) {
        if (mSpeechRecognizer == null) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(sContext);
        }
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, sContext.getPackageName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000000);

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("startVoiceRecognition", "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("startVoiceRecognition", "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                Log.d("startVoiceRecognition", "onRmsChanged");
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                Log.d("startVoiceRecognition", "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.d("startVoiceRecognition", "onEndOfSpeech");
            }

            @Override
            public void onError(int error) {
                Log.d("startVoiceRecognition", "onError");
                completion.speechReconitionResults("");
            }

            @Override
            public void onResults(Bundle results) {
                Log.d("startVoiceRecognition", results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).toString());
                completion.speechReconitionResults(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.d("startVoiceRecognition", "onPartialResults");
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                Log.d("startVoiceRecognition", "onEvent");
            }
        });
        Handler mainHandler = new Handler();

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            } // This is your code
        };
        mainHandler.post(myRunnable);


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

    public NanoRepFAQ getFAQ() {
        if (mFAQ == null) {
            mFAQ = new NanoRepFAQ(mContext, mDomain, mAccountName, NRUtilities.wrappedContextBase64(mNanoContext));
            mFAQ.setReferer(mReferer);
        }
        return mFAQ;
    }

    private void startKeepAlive() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NRUtilities.ApiNameKey, KeepAliveAPI);
        callAPI(params, new NRConnection.NRConnectionListener() {
            @Override
            public void response(Object responseParam, NRError error) {
                if (error != null) {
                    Log.d("Keep Alive Error", error.getDescription());
                } else if (responseParam != null) {
                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {
                        public void run() {
                            NanoRep.this.startKeepAlive();
                        }
                    }, (long) NanoRep.this.mDelay);
                }
            }
        });
    }

    private void callAPI(HashMap<String, String> params, final NRConnection.NRConnectionListener completion) {
        if (mSessionId != null) {
            params.put(SidKey, mSessionId);
            api(params, completion);
        } else if (false) {
            // TODO: Check for connection
        } else {
            if (completion != null) {
                Object[] arr = {params, completion};
                getWaitingAPICalls().add(arr);
            }
            HashMap<String, String> map = new HashMap<>();
            if (mKnowledgeBase != null) {
                map.put("kb", mKnowledgeBase);
            }
            map.put("nostats", "false");
            map.put("url", "Mobile");
            map.put(NRUtilities.ApiNameKey, FetchSessionIdAPI);
            if (mContext != null) {
                map.put("ctx", NRUtilities.wrappedContext(mNanoContext));
            }
            api(map, new NRConnection.NRConnectionListener() {
                @Override
                public void response(Object responseParam, NRError error) {
                    if (error != null) {
                        completion.response(null, error);
                    } else if (responseParam != null && ((HashMap)responseParam).get(SessionIdKey) != null) {
                        NanoRep.this.mSessionId = (String)((HashMap)responseParam).get(SessionIdKey);
                        NanoRep.this.mDelay = (((Integer)((HashMap)responseParam).get(TimeoutKey)).floatValue() / 2) * 1000;
                        Handler timer = new Handler();
                        timer.postDelayed(new Runnable() {
                            public void run() {
                                NanoRep.this.startKeepAlive();
                            }
                        }, (long) NanoRep.this.mDelay);
                        ArrayList<Object[]> temp = new ArrayList<Object[]>(NanoRep.this.getWaitingAPICalls());
                        for (Object[] arr: temp) {
                            NanoRep.this.callAPI((HashMap)arr[0], (NRConnection.NRConnectionListener)arr[1]);
                            NanoRep.this.getWaitingAPICalls().remove(arr);
                        }
                        temp = null;
                        NanoRep.this.mWaitingAPICalls = null;
                    }
                }
            });
        }
    }

    private void api(HashMap<String, String> params, NRConnection.NRConnectionListener completion) {
        params.putAll(getFAQ().getDefaultParams());
        String link = NRUtilities.buildURL(params);
        NRConnection.connectionWithRequest(NRUtilities.getRequest(link, mReferer), completion);
    }
}
