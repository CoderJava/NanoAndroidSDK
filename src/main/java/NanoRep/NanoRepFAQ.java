package nanorep;

import android.content.Context;


import java.util.HashMap;



/**
 * Created by nissopa on 9/12/15.
 */
public class NanoRepFAQ {

    private Context mContext;
    private Nanorep.AccountParams mAccountParams;
    private HashMap<String, String> mDefaultParams;

    public NanoRepFAQ(Context context, Nanorep.AccountParams accountParams) {
        mContext = context;
        mAccountParams = accountParams;
    }




//    public HashMap<String, String> getDefaultParams() {
//        if (mDefaultParams == null) {
//            mDefaultParams = new HashMap();
//            mDefaultParams.put(NRUtilities.DomainKey, mAccountParams.getDomain());
//            mDefaultParams.put(NRUtilities.AccountNameKey, mAccountParams.getAccount());
//            mDefaultParams.put("referer", NRUtilities.buildReferer(mAccountParams.getReferrer()));
//        }
//        return new HashMap(mDefaultParams);
//    }


//    public void answerWithId(final String answerId, final Nanorep.OnFAQAnswerFetchedListener completion) {
//        HashMap<String, String> params = getDefaultParams();
//        params.put("id", answerId);
//        params.put("i", Integer.toString(0));
//        params.put("api", "answer.js");
//        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.Listener() {
//            @Override
//            public void response(Object responseParam, NRError error) {
//                if (error != null) {
//                    HashMap<String, Object> storedResponse = NRCacheManager.getAnswerById(mContext, answerId);
//                    if (storedResponse != null) {
//                        completion.onFAQAnswerFetched(new NRFAQAnswer(storedResponse), null);
//                    } else {
//                        completion.onFAQAnswerFetched(null, error);
//                    }
//                } else if (responseParam != null){
//                    completion.onFAQAnswerFetched(new NRFAQAnswer((HashMap)responseParam), null);
//                    NRCacheManager.storeAnswerById(mContext, answerId, (HashMap)responseParam);
//                }
//            }
//        });
//    }
//
    /**
     * Updates the likes value of an answer
     *
     * @param faqLikeParams FAQ Like params object
     * @param completion Callback contains the like's type and success status
     */
//    public void faqLike(final NRFAQLikeParams faqLikeParams, final NRLikeCompletion completion) {
//        if (faqLikeParams != null) {
//            String likeUrl = "http://" + mAccountParams.getDomain() + "/~" + mAccountParams.getAccount() + "/widget/faqAction.gif?";
//            for (String key : faqLikeParams.getParams().keySet()) {
//                likeUrl += key + "=" + faqLikeParams.getParams().get(key) + "&";
//            }
//            likeUrl = likeUrl.substring(0, likeUrl.length() - 1);
//            URL url = null;
//            try {
////            link = URLEncoder.encode(link, "utf-8");
//                url = new URL(likeUrl);
//                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//                connection.setRequestProperty("Referer", NRUtilities.buildReferer(mAccountParams.getReferrer()));
//                connection.connect();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (url != null) {
//                NRConnection.connectionWithRequest(url, new NRConnection.Listener() {
//                    @Override
//                    public void response(Object responseParam, NRError error) {
//                        if (error != null) {
//                            completion.likeResult(0, false);
//                        } else {
//                            completion.likeResult(Integer.parseInt(faqLikeParams.getParams().get("type")), responseParam == null);
//                        }
//                    }
//                });
//            }
//        }
//    }

//    private void fetchFaqList(NRConnection.Listener completion) {
//        HashMap<String, String> params = getDefaultParams();
//        params.put(NRUtilities.ApiNameKey, "list.json");
//        if (mAccountParams.getNanorepContext() != null && mAccountParams.getNanorepContext().length() > 0) {
//            params.put("context", mAccountParams.getNanorepContext());
//        }
//        final HashMap<String, String> paramsFinal = params;
//        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), completion);
//    }

    /**
     * Fetch the default configuration for the account
     *
     * @param knowledgeBase
     * @param completion Callback with configuration parameters in case of success or NRError in case of error
     */
//    public void fetchDefaultFAQWithCompletion(String knowledgeBase, final Nanorep.OnConfigurationFetchedListener completion) {
//        final HashMap<String, String> params = getDefaultParams();
//        params.put(NRUtilities.ApiNameKey, "cnf.json");
//        if (knowledgeBase != null) {
//            params.put("kb", knowledgeBase);
//        }
//        NRConnection.connectionWithRequest(NRUtilities.getFAQRequest(params), new NRConnection.Listener() {
//            @Override
//            public void response(Object responseParam, NRError error) {
//                if (error != null) {
//                    HashMap<String, Object> cachedResponse = NRCacheManager.getAnswerById(mContext, NRUtilities.md5(params));
//                    if (completion != null) {
//                        if (cachedResponse != null) {
//                            completion.onConfigurationFetched(new NRConfiguration(cachedResponse), null);
//                        } else {
//                            completion.onConfigurationFetched(null, error);
//                        }
//                    }
//                } else {
//                    final NRConfiguration cnf = new NRConfiguration((HashMap) responseParam);
//                    if (cnf.getIsContextDependent()) {
//                        fetchFaqList(new NRConnection.Listener() {
//                            @Override
//                            public void response(Object responseParam, NRError error) {
//                                if (responseParam != null) {
//                                    cnf.setFaqData((ArrayList) responseParam);
//                                }
//                                if (completion != null) {
//                                    if (error != null) {
//                                        completion.onConfigurationFetched(null, error);
//                                    } else if (responseParam != null) {
//                                        completion.onConfigurationFetched(cnf, null);
//                                    } else {
//                                        completion.onConfigurationFetched(null, NRError.error("com.nanorepfaq", 1002, "faqData empty"));
//                                    }
//                                }
//                            }
//                        });
//                    } else {
//                        if (completion != null) {
//                            completion.onConfigurationFetched(new NRConfiguration((HashMap) responseParam), null);
//                        }
//                        NRCacheManager.storeAnswerById(mContext, NRUtilities.md5(params), (HashMap) responseParam);
//                    }
//                }
//                mDefaultParams = null;
//            }
//        });
//    }
}
