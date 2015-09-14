package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

import NanoRep.NanoRep;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRSuggestions {
    private int mResultsCount;
    private String mRequestId;
    private String mLanguageCode;
    private String mQueryAnalysis;
    private ArrayList<String> mAnswers;

    public NRSuggestions(HashMap<String, Object> params) {
        mResultsCount = (int)params.get("c");
        mRequestId = (String)params.get("rid");
        mLanguageCode = (String)params.get("lc");
        mQueryAnalysis = (String)params.get("q");
        mAnswers = (ArrayList<String>)params.get("a");
    }

    public int getResultsCount() {
        return mResultsCount;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public String getLanguageCode() {
        return mLanguageCode;
    }

    public String getQueryAnalysis() {
        return mQueryAnalysis;
    }

    public ArrayList<String> getAnswers() {
        return mAnswers;
    }
}
