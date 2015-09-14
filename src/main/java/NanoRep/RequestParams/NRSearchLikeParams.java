package NanoRep.RequestParams;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRSearchLikeParams extends NRRequestParams {
    private String mSearchQuery;
    private int mFeedbackType;
    private String mKeywordSetId;
    private String mKBLanguageCode;
    private String mArticleId;

    public void setSearchQuery(String searchQuery) {
        mSearchQuery = searchQuery;
        setValue(searchQuery, "text");
    }

    public void setFeedbackType(int feedbackType) {
        mFeedbackType = feedbackType;
        setValue(Integer.toString(feedbackType), "type");
    }

    public void setKeywordSetId(String keywordSetId) {
        mKeywordSetId = keywordSetId;
        setValue(keywordSetId, "ksId");
    }

    public void setKBLanguageCode(String KBLanguageCode) {
        mKBLanguageCode = KBLanguageCode;
        setValue(KBLanguageCode, "kbLC");
    }

    public void setArticleId(String articleId) {
        mArticleId = articleId;
        setValue(articleId, "articleId");
    }
}
