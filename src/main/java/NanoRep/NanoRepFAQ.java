package NanoRep;

import android.content.Context;

import com.nanorep.nanorepsdk.Connection.NRCacheManager;
import com.nanorep.nanorepsdk.Connection.NRConnection;
import com.nanorep.nanorepsdk.Connection.NRError;
import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
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
    private HashMap<String, String> mDefaultParams;

    public NanoRepFAQ(Context context, String accountName) {
        mContext = context;
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

    public void answerWithId(final String answerId, final NRFAQAnswerCompletion completion) {
        HashMap<String, String> params = getDefaultParams();
        params.put("id", answerId);
        params.put("i", Integer.toString(0));
        params.put("api", "answer");
        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.NRConnectionListener() {
            @Override
            public void response(HashMap responseParam, NRError error) {
                if (error != null) {
                    HashMap<String, Object> storedResponse = NRCacheManager.getAnswerById(mContext, answerId);
                    if (storedResponse != null) {
                        completion.fetchAnswer(new NRFAQAnswer(storedResponse), null);
                    } else {
                        completion.fetchAnswer(null, error);
                    }
                } else if (responseParam != null){
                    completion.fetchAnswer(new NRFAQAnswer(responseParam), null);
                    NRCacheManager.storeAnswerById(mContext, answerId, responseParam);
                }
            }
        });
    }

    public void faqLike(final NRFAQLikeParams faqLikeParams, final NRLikeCompletion completion) {
        if (faqLikeParams != null) {
            String likeUrl = "http://office.nanorep.com/~" + mAccountName + "/widget/faqAction.gif?";
            for (String key : faqLikeParams.getParams().keySet()) {
                likeUrl += key + "=" + faqLikeParams.getParams().get(key) + "&";
            }
            likeUrl = likeUrl.substring(0, likeUrl.length() - 1);
            URL url = null;
            try {
//            link = URLEncoder.encode(link, "utf-8");
                url = new URL(likeUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("Referer", getReferer());
                connection.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (url != null) {
                NRConnection.connectionWithRequest(url, new NRConnection.NRConnectionListener() {
                    @Override
                    public void response(HashMap responseParam, NRError error) {
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

    public void fetchDefaultFAQWithCompletion(final NRDefaultFAQCompletion completion) {
        final HashMap<String, String> params = getDefaultParams();
        params.put("api", "cnf");
//        final String id = NRUtilities.md5(NRUtilities.getFAQRequest(params));
        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.NRConnectionListener() {
            @Override
            public void response(HashMap responseParam, NRError error) {
                if (error != null) {
                    HashMap<String, Object> cachedResponse = NRCacheManager.getAnswerById(mContext, NRUtilities.md5(params));
                    if (cachedResponse != null) {
                        completion.fetchDefaultFAQ(new NRFAQCnf(cachedResponse), null);
                    } else {
                        completion.fetchDefaultFAQ(null, error);
                    }
                } else {
                    completion.fetchDefaultFAQ(new NRFAQCnf(responseParam), null);
                    NRCacheManager.storeAnswerById(mContext, NRUtilities.md5(params), responseParam);
                }
                mDefaultParams = null;
            }
        });
    }
}
