package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQCnf {
    private Object mFaqData;
    private HashMap<String, Object> mParams;

    /**
     * Converts the response JSON into NRFAQCnf object
     *
     * @param HashMap contains all of the params from the JSON
     */
    public NRFAQCnf(HashMap<String, Object> params) {
        mParams = params;
    }

    /**
     * Fetches the CNF id
     *
     * @return value of the cnf id
     */
    public String getCnfId() {
        return (String)mParams.get("id");
    }

    /**
     * Fetches the keyboard id
     *
     * @return value of keyboard id
     */
    public String getKbId() {
        return (String)mParams.get("kbId");
    }

    /**
     *
     * @return
     */
    public String getCacheVar() {
        return (String)mParams.get("cacheVar");
    }

    /**
     * Fetches the language code of the response
     *
     * @return value of kbLanguageCode
     */
    public String getKbLanguageCode() {
        return (String)mParams.get("kbLanguageCode");
    }

    /**
     * Generates FAQ items from the FAQ Data
     *
     * @return ArrayList of NRFAQCnfItem
     */
    public Object getFaqData() {
        ArrayList<NRFAQCnfItem> arr = null;
//        ArrayList<HashMap<String, Object>> data = (ArrayList)mParams.get("faqData");
        Object data = mParams.get("faqData");
        if (data == null) {
            return null;
        }
        if (data instanceof ArrayList) {
            arr = new ArrayList<NRFAQCnfItem>();
            for (HashMap<String, Object> map: (ArrayList<HashMap<String, Object>>)data) {
                arr.add(new NRFAQCnfItem(map));
            }
            mFaqData = new ArrayList<NRFAQCnfItem>(arr);
            arr = null;
            return mFaqData;
        }
        return data;
    }
}
