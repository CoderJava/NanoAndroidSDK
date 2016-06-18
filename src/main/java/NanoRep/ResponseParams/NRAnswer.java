package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SimpleTimeZone;

import NanoRep.Chnneling.NRChanneling;
import NanoRep.Interfaces.NRQueryResult;
import NanoRep.NanoRep;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRAnswer implements NRQueryResult{
    private HashMap<String, ?> mParams;
    private String mArticleId;
    private String mKeywordsetId;
    private int mLikes;
    private String mTitle;
    private String mSummary;
    private ArrayList<String> mAttachments;
    private ArrayList<NRChanneling> mChanneling;

    /**
     * Converts JSON string to NRAnswer object
     *
     * @param params HashMap generated from json string
     */
    public NRAnswer(HashMap<String, Object> params) {
        mParams = params;
        mArticleId = (String)params.get("id");
        mKeywordsetId = (String)params.get("keywordsetId");
        mLikes = (int)params.get("likes");
        mTitle = (String)params.get("title");
        mSummary = (String)params.get("summary");
        mAttachments = (ArrayList)params.get("attachments");
    }

    /**
     * Fetches article Id
     *
     * @return Value of article id
     */
    public String getArticleId() {
        return mArticleId;
    }

    /**
     * Fetches Keyword Set Id
     *
     * @return Value of keyword set id
     */
    public String getKeywordsetId() {
        return mKeywordsetId;
    }

    /**
     * Fetches amount of likes
     *
     * @return Value of like param
     */
    public String getLikes() {
        return Integer.toString(mLikes);
    }

    @Override
    public String getId() {
        return getArticleId();
    }

    /**
     * Fetches Title of answer
     *
     * @return Value of answer's title
     */
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void setBody(String body) {

    }

    @Override
    public String getBody() {
        return getSummary();
    }

    /**
     * Fetches short description of the answer
     *
     * @return Value of summary
     */
    public String getSummary() {
        return mSummary;
    }

    /**
     *
     * @return Value of attachments
     */
    public ArrayList<String> getAttachments() {
        return mAttachments;
    }

    public ArrayList<NRChanneling> getChanneling() {
        if (mChanneling == null) {
            ArrayList<HashMap<String, ?>> channels = (ArrayList)mParams.get("rechanneling");
            if (channels != null && channels.size() > 0) {
                mChanneling = new ArrayList<>();
                for (HashMap<String, ?> channel : channels) {
                    mChanneling.add(new NRChanneling(channel));
                }
            } else {
                return null;
            }
        }
        return mChanneling;
    }

}
