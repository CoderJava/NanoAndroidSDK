package NanoRep;

import android.content.Context;

import com.nanorep.nanorepsdk.Connection.NRCacheManager;
import com.nanorep.nanorepsdk.Connection.NRConnection;
import com.nanorep.nanorepsdk.Connection.NRError;
import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
    private Context mContext;
    private String mDomain;
    private HashMap<String, String> mDefaultParams;
    private String mNanoContext;

    private static NRFAQCnf sCNF;

    public static void setCNF(NRFAQCnf cnf) {
        sCNF = cnf;
    }

    public NanoRepFAQ(Context context, String domain,  String accountName, String nanoContext) {
        mContext = context;
        mAccountName = accountName;
        mDomain = domain;
        mNanoContext = nanoContext;
    }

    private String getReferer() {
        if (mReferer == null) {
            mReferer = "mobile";
        }
        return mReferer;
    }

    public HashMap<String, String> getDefaultParams() {
        if (mDefaultParams == null) {
            mDefaultParams = new HashMap<String, String>();
            mDefaultParams.put(NRUtilities.DomainKey, mDomain);
            mDefaultParams.put(NRUtilities.AccountNameKey, mAccountName);
            mDefaultParams.put("referer", NRUtilities.buildReferer(getReferer()));
        }
        return new HashMap<String, String>(mDefaultParams);
    }

    public void setReferer(String referer) {
        mReferer = referer;
    }

    public void faqWithParams(NRFAQParams params, NRFAQCompletion completion) {

    }

    public void answerWithId(final String answerId, final NRFAQAnswerCompletion completion) {
        HashMap<String, String> params = getDefaultParams();
        params.put("id", answerId);
        params.put("i", Integer.toString(0));
        params.put("api", "answer.js");
        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.NRConnectionListener() {
            @Override
            public void response(Object responseParam, NRError error) {
                if (error != null) {
                    HashMap<String, Object> storedResponse = NRCacheManager.getAnswerById(mContext, answerId);
                    if (storedResponse != null) {
                        completion.fetchAnswer(new NRFAQAnswer(storedResponse), null);
                    } else {
                        completion.fetchAnswer(null, error);
                    }
                } else if (responseParam != null){
                    completion.fetchAnswer(new NRFAQAnswer((HashMap)responseParam), null);
                    NRCacheManager.storeAnswerById(mContext, answerId, (HashMap)responseParam);
                }
            }
        });
    }

    /**
     * Updates the likes value of an answer
     *
     * @param faqLikeParams FAQ Like params object
     * @param completion Callback contains the like's type and success status
     */
    public void faqLike(final NRFAQLikeParams faqLikeParams, final NRLikeCompletion completion) {
        if (faqLikeParams != null) {
            String likeUrl = "http://" + mDomain + "/~" + mAccountName + "/widget/faqAction.gif?";
            for (String key : faqLikeParams.getParams().keySet()) {
                likeUrl += key + "=" + faqLikeParams.getParams().get(key) + "&";
            }
            likeUrl = likeUrl.substring(0, likeUrl.length() - 1);
            URL url = null;
            try {
//            link = URLEncoder.encode(link, "utf-8");
                url = new URL(likeUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("Referer", NRUtilities.buildReferer(getReferer()));
                connection.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (url != null) {
                NRConnection.connectionWithRequest(url, new NRConnection.NRConnectionListener() {
                    @Override
                    public void response(Object responseParam, NRError error) {
                        if (error != null) {
                            completion.likeResult(0, false);
                        } else {
                            completion.likeResult(Integer.parseInt(faqLikeParams.getParams().get("type")), responseParam == null);
                        }
                    }
                });
            }
        }
    }

    private void fetchFaqList(NRConnection.NRConnectionListener completion) {
        HashMap<String, String> params = getDefaultParams();
        params.put(NRUtilities.ApiNameKey, "list.json");
        if (mNanoContext != null && mNanoContext.length() > 0) {
            params.put("context", mNanoContext);
        }
        final HashMap<String, String> paramsFinal = params;
        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), completion);
    }

    /**
     * Fetch the default configuration for the account
     *
     * @param knowledgeBase
     * @param completion Callback with configuration parameters in case of success or NRError in case of error
     */
    public void fetchDefaultFAQWithCompletion(String knowledgeBase, final NRDefaultFAQCompletion completion) {
        if (sCNF != null) {
            completion.fetchDefaultFAQ(sCNF, null);
        } else {
            final HashMap<String, String> params = getDefaultParams();
            params.put(NRUtilities.ApiNameKey, "cnf.json");
            if (knowledgeBase != null) {
                params.put("kb", knowledgeBase);
            }
//        final String id = NRUtilities.md5(NRUtilities.getFAQRequest(params));
            NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.NRConnectionListener() {
                @Override
                public void response(Object responseParam, NRError error) {
                    if (error != null) {
                        HashMap<String, Object> cachedResponse = NRCacheManager.getAnswerById(mContext, NRUtilities.md5(params));
                        if (cachedResponse != null) {
                            completion.fetchDefaultFAQ(new NRFAQCnf(cachedResponse), null);
                        } else {
                            completion.fetchDefaultFAQ(null, error);
                        }
                    } else {
                        final NRFAQCnf cnf = new NRFAQCnf((HashMap) responseParam);
                        if (cnf.getIsContextDependent()) {
                            fetchFaqList(new NRConnection.NRConnectionListener() {
                                @Override
                                public void response(Object responseParam, NRError error) {
                                    if (error != null) {
                                        completion.fetchDefaultFAQ(null, error);
                                    } else if (responseParam != null) {
                                        cnf.setFaqData((HashMap) responseParam);
                                        completion.fetchDefaultFAQ(cnf, null);
                                    } else {
                                        completion.fetchDefaultFAQ(null, NRError.error("com.nanorepfaq", 1002, "faqData empty"));
                                    }
                                }
                            });
                        } else {
                            completion.fetchDefaultFAQ(new NRFAQCnf((HashMap) responseParam), null);
                            NRCacheManager.storeAnswerById(mContext, NRUtilities.md5(params), (HashMap) responseParam);
                        }
                    }
                    mDefaultParams = null;
                }
            });
        }
    }
}
