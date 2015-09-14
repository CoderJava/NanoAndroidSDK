package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQCnf {
    private ArrayList<NRFAQCnfItem> mFaqData;
    private HashMap<String, Object> mParams;

    public NRFAQCnf(HashMap<String, Object> params) {

    }

    public String getCnfId() {
        return (String)mParams.get("id");
    }

    public String getKbId() {
        return (String)mParams.get("kbId");
    }

    public String getCacheVar() {
        return (String)mParams.get("cacheVar");
    }

    public String getKbLanguageCode() {
        return (String)mParams.get("kbLanguageCode");
    }

    public ArrayList<NRFAQCnfItem> getFaqData() {
        ArrayList<NRFAQCnfItem> arr = null;
        ArrayList<HashMap<String, Object>> data = (ArrayList)mParams.get("faqData");
        if (mFaqData == null && data.size() > 0) {
            arr = new ArrayList<NRFAQCnfItem>();
            for (HashMap<String, Object> map: data) {
                arr.add(new NRFAQCnfItem(map));
            }
            mFaqData = new ArrayList<NRFAQCnfItem>(arr);
            arr = null;
        }
        return mFaqData;
    }
}
