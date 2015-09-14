package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

import NanoRep.NanoRep;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRSearchResponse {
    private String mSearchId;
    private String mLangCode;
    private String mDetectedLanguage;
    private ArrayList<String> mAnswerList;

    public NRSearchResponse(HashMap<String, Object> params) {
        mSearchId = (String)params.get("requestId");
        mLangCode = (String)params.get("kbLanguageCode");
        mDetectedLanguage = (String)params.get("detectedLanguage");
        mAnswerList = (ArrayList<String>)params.get("answers");
    }

    public String getSearchId() {
        return mSearchId;
    }

    public String getLangCode() {
        return mLangCode;
    }

    public String getDetectedLanguage() {
        return mDetectedLanguage;
    }

    public ArrayList<String> getAnswerList() {
        return mAnswerList;
    }
}
