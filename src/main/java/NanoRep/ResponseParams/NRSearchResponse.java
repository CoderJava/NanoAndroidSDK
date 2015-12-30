package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

import NanoRep.Chnneling.NRChanneling;
import NanoRep.NanoRep;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRSearchResponse {
    private HashMap<String, Object> mParams;
    private String mSearchId;
    private String mLangCode;
    private String mDetectedLanguage;
    private ArrayList<HashMap<String, Object>> mAnswerList;
    private ArrayList<NRChanneling> mChanneling;

    /**
     * Converts JSON string into NRSearchResponse
     *
     * @param params HashMap contains all of the NRSearchResponse params
     */
    public NRSearchResponse(HashMap<String, Object> params) {
        mParams = params;
        mSearchId = (String)params.get("requestId");
        mLangCode = (String)params.get("kbLanguageCode");
        mDetectedLanguage = (String)params.get("detectedLanguage");
        mAnswerList = (ArrayList<HashMap<String, Object>>)params.get("answers");
    }

    /**
     *
     * @return value of search id
     */
    public String getSearchId() {
        return mSearchId;
    }

    /**
     *
     * @return value of langCode
     */
    public String getLangCode() {
        return mLangCode;
    }

    /**
     *
     * @return value of detected language
     */
    public String getDetectedLanguage() {
        return mDetectedLanguage;
    }

    /**
     *
     * @return value of answer list
     */
    public ArrayList<HashMap<String, Object>> getAnswerList() {
        return mAnswerList;
    }

    public ArrayList<NRChanneling> getChanneling() {
        if (mChanneling == null) {
            ArrayList<HashMap<String, Object>> channeling = (ArrayList)mParams.get("rechanneling");
            if (channeling != null && channeling.size() > 0) {
                mChanneling = new ArrayList<>();
            } else {
                return null;
            }
            for (HashMap<String, Object> channel: channeling) {
                mChanneling.add(NRChanneling.channelForParams(channel));
            }
        }
        return mChanneling;
    }
}
