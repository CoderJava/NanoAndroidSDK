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
    private ArrayList<String> mSuggestions;

    /**
     * Converts JSON string into NRSuggestions
     *
     * @param params HashMap contains the NRSuggestions params
     */
    public NRSuggestions(HashMap<String, Object> params) {
        mResultsCount = (int)params.get("c");
        mRequestId = (String)params.get("rid");
        mLanguageCode = (String)params.get("lc");
        mQueryAnalysis = (String)params.get("q");
        mSuggestions = (ArrayList<String>)params.get("a");
    }

    /**
     *
     * @return value of results count
     */
    public int getResultsCount() {
        return mResultsCount;
    }

    /**
     *
     * @return value of request id
     */
    public String getRequestId() {
        return mRequestId;
    }

    /**
     *
     * @return value of language code
     */
    public String getLanguageCode() {
        return mLanguageCode;
    }

    /**
     *
     * @return value of query analysis
     */
    public String getQueryAnalysis() {
        return mQueryAnalysis;
    }

    /**
     *
     * @return value of answers
     */
    public ArrayList<String> getSuggestions() {
        return mSuggestions;
    }
}
