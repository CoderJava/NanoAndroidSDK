package nanorep.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import nanorep.Interfaces.NRQueryResult;
import nanorep.ResponseParams.NRAnswer;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRSearchLikeParams extends NRRequestParams {
    private String mSearchQuery;
    private NRLikeType mFeedbackType;
    private String mKeywordSetId;
    private String mKBLanguageCode;
    private String mArticleId;

    public NRSearchLikeParams(NRQueryResult result) {
        super(result);
        setArticleId(result.getId());
        setKeywordSetId(((NRAnswer)result).getKeywordsetId());
        setSearchQuery(result.getTitle());
    }

    /**
     * Send like for search result
     *
     * @param searchQuery The searched text
     */
    public void setSearchQuery(String searchQuery) {
        String encodedText = null;
        try {
            encodedText = URLEncoder.encode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mSearchQuery = encodedText == null ? "" : encodedText;
        setValue(mSearchQuery, "text");
    }

    /**
     * Set the like type
     *
     * @param feedbackType can be like or unlike and more, check out our documentation
     */
    public void setFeedbackType(NRLikeType feedbackType) {
        mFeedbackType = feedbackType;
        setValue(feedbackType.toString(), "type");
    }

    /**
     *
     * @param keywordSetId
     */
    public void setKeywordSetId(String keywordSetId) {
        mKeywordSetId = keywordSetId;
        setValue(keywordSetId, "ksId");
    }

    /**
     * Set the language by lang code
     *
     * @param KBLanguageCode language code
     */
    public void setKBLanguageCode(String KBLanguageCode) {
        mKBLanguageCode = KBLanguageCode;
        setValue(KBLanguageCode, "kbLC");
    }

    /**
     * Set the liked article id
     *
     * @param articleId The id which you got when you fetched the search result
     */
    public void setArticleId(String articleId) {
        mArticleId = articleId;
        setValue(articleId, "articleId");
    }
}
