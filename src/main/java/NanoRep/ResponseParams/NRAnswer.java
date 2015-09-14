package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SimpleTimeZone;

import NanoRep.NanoRep;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRAnswer {
    private String mArticleId;
    private String mKeywordsetId;
    private int mLikes;
    private String mTitle;
    private String mSummary;
    private ArrayList<String> mAttachments;

    public NRAnswer(HashMap<String, Object> params) {
        mArticleId = (String)params.get("id");
        mKeywordsetId = (String)params.get("keywordsetId");
        mLikes = (int)params.get("likes");
        mTitle = (String)params.get("title");
        mSummary = (String)params.get("summary");
        mAttachments = (ArrayList<String>)params.get("attachments");
    }

    public String getArticleId() {
        return mArticleId;
    }

    public String getKeywordsetId() {
        return mKeywordsetId;
    }

    public int getLikes() {
        return mLikes;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSummary() {
        return mSummary;
    }

    public ArrayList<String> getAttachments() {
        return mAttachments;
    }
}
