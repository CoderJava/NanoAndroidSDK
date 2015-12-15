package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQCnf {
    private NRFAQData mFaqData;
    private HashMap<String, Object> mParams;
    private boolean mIsContextDependent = false;

    /**
     * Converts the response JSON into NRFAQCnf object
     *
     * @param HashMap contains all of the params from the JSON
     */
    public NRFAQCnf(HashMap<String, Object> params) {
        mParams = params;
        if (params != null) {
            Object faq = mParams.get("faqData");
            if (faq != null && faq instanceof String) {
                mIsContextDependent = true;
            }
        }
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
//    public Object getFaqData() {
//        ArrayList<NRFAQCnfItem> arr = null;
////        ArrayList<HashMap<String, Object>> data = (ArrayList)mParams.get("faqData");
//        if (mParams == null) {
//            return null;
//        }
//        Object data = mParams.get("faqData");
//        if (data == null) {
//            return null;
//        }
//        if (data instanceof ArrayList) {
//            arr = new ArrayList<NRFAQCnfItem>();
//            for (HashMap<String, Object> map: (ArrayList<HashMap<String, Object>>)data) {
//                arr.add(new NRFAQCnfItem(map));
//            }
//            mFaqData = new ArrayList<NRFAQCnfItem>(arr);
//            arr = null;
//            return mFaqData;
//        }
//        return data;
//    }
    public boolean getIsContextDependent() {
        return mIsContextDependent;
    }

    public void setFaqData(HashMap<String, Object> faqList) {
        mParams.put("faqData", faqList);
        mIsContextDependent = false;
    }

    public NRFAQData getFaqData() {
        if (mIsContextDependent) {
            return null;
        }
        if (mFaqData == null) {
            if (mParams == null) {
                return null;
            }
            ArrayList faq = (ArrayList)mParams.get("faqData");
            if (faq == null || faq.get(0) == null) {
                return null;
            }
            mFaqData = new NRFAQData((HashMap)faq.get(0));
        }
        return mFaqData;
    }
}
