package nanorep;

import android.content.Context;
import android.net.Uri;

import com.nanorep.nanorepsdk.Connection.NRCacheManager;
import com.nanorep.nanorepsdk.Connection.NRConnection;
import com.nanorep.nanorepsdk.Connection.NRError;
import com.nanorep.nanorepsdk.Connection.NRUtilities;

import java.util.ArrayList;
import java.util.HashMap;

import nanorep.RequestParams.NRFAQLikeParams;
import nanorep.RequestParams.NRSearchLikeParams;
import nanorep.ResponseParams.NRConfiguration;

/**
 * Created by nissimpardo on 07/08/2016.
 */

public class NRImpl implements Nanorep {
    private NanoRepFAQ mFAQ;
    private AccountParams mAccountParams;
    private Context mContext;
    private Uri.Builder mUri;

    public NRImpl(Context context, AccountParams accountParams) {
        mContext = context;
        mAccountParams = accountParams;
    }

    public NanoRepFAQ getFAQ() {
        if (mFAQ == null) {
            mFAQ = new NanoRepFAQ(mContext, mAccountParams);
        }
        return mFAQ;
    }

    private void startSession() {

    }

    private void fetchFaqList(NRConnection.Listener listener) {
        final Uri.Builder uri = mAccountParams.getUri();
        uri.appendPath("api");
        uri.appendPath("faq");
        uri.appendPath("v1");
        uri.appendPath("list.json");
        uri.appendQueryParameter("account", mAccountParams.getAccount());
        if (mAccountParams.getNanorepContext() != null) {
            uri.appendQueryParameter("context", mAccountParams.getKnowledgeBase());
        }
        NRConnection.connectionWithRequest(uri.build(), listener);
    }


    @Override
    public void searchText(String text, OnSearchResultsFetchedListener onSearchResultsFetchedListener) {

    }

    @Override
    public void suggestionsForText(String text, OnSuggestionsFetchedListener onSuggestionsFetchedListener) {

    }

    @Override
    public void likeForSearchResult(NRSearchLikeParams likeParams, OnLikeSentListener onLikeSentListener) {

    }

    @Override
    public void fetchFAQAnswer(String answerId, OnFAQAnswerFetchedListener onFAQAnswerFetchedListener) {

    }

    @Override
    public void likeForFAQResult(final NRFAQLikeParams likeParams, final OnLikeSentListener onLikeSentListener) {
        Uri.Builder uriBuilder = mAccountParams.getUri();
        uriBuilder.appendPath("widget");
        uriBuilder.appendPath("faqAction.gif");
        for (String key: likeParams.getParams().keySet()) {
            uriBuilder.appendQueryParameter(key, likeParams.getParams().get(key));
        }
        NRConnection.connectionWithRequest(uriBuilder.build(), new NRConnection.Listener() {
            @Override
            public void response(Object responseParam, NRError error) {
                if (error != null) {
                    onLikeSentListener.onLikeSent(0, false);
                } else {
                    onLikeSentListener.onLikeSent(Integer.parseInt(likeParams.getParams().get("type")), responseParam == null);
                }
            }
        });
    }

    @Override
    public void fetchConfiguration(final OnConfigurationFetchedListener onConfigurationFetchedListener) {
        if (mAccountParams != null) {
            final Uri.Builder uri = mAccountParams.getUri();
            uri.appendPath("widget");
            uri.appendPath("scripts");
            uri.appendPath("cnf.json");
            if (mAccountParams.getKnowledgeBase() != null) {
                uri.appendQueryParameter("kb", mAccountParams.getKnowledgeBase());
            }
            NRConnection.connectionWithRequest(uri.build(), new NRConnection.Listener() {
                @Override
                public void response(Object responseParam, NRError error) {
                    if (error != null) {
                        HashMap<String, Object> cachedResponse = NRCacheManager.getAnswerById(mContext, NRUtilities.md5(mAccountParams.getKnowledgeBase() + mAccountParams.getNanorepContext()));
                        if (onConfigurationFetchedListener != null) {
                            if (cachedResponse != null) {
                                onConfigurationFetchedListener.onConfigurationFetched(new NRConfiguration(cachedResponse), null);
                            } else {
                                onConfigurationFetchedListener.onConfigurationFetched(null, error);
                            }
                        }
                    } else {
                        final NRConfiguration cnf = new NRConfiguration((HashMap) responseParam);
                        if (cnf.getIsContextDependent()) {
                            fetchFaqList(new NRConnection.Listener() {
                                @Override
                                public void response(Object responseParam, NRError error) {
                                    if (responseParam != null) {
                                        cnf.setFaqData((ArrayList) responseParam);
                                    }
                                    if (onConfigurationFetchedListener != null) {
                                        if (error != null) {
                                            onConfigurationFetchedListener.onConfigurationFetched(null, error);
                                        } else if (responseParam != null) {
                                            onConfigurationFetchedListener.onConfigurationFetched(cnf, null);
                                        } else {
                                            onConfigurationFetchedListener.onConfigurationFetched(null, NRError.error("com.nanorepfaq", 1002, "faqData empty"));
                                        }
                                    }
                                }
                            });
                        } else {
                            if (onConfigurationFetchedListener != null) {
                                onConfigurationFetchedListener.onConfigurationFetched(new NRConfiguration((HashMap) responseParam), null);
                            }
                            NRCacheManager.storeAnswerById(mContext, NRUtilities.md5(mAccountParams.getKnowledgeBase() + mAccountParams.getNanorepContext()), (HashMap) responseParam);
                        }
                    }
                }
            });
        }
    }
}
