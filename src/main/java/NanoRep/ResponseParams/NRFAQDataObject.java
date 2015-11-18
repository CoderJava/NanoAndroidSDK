package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQDataObject {
    private ArrayList<NRFAQGroupItem> mData;
    private HashMap<String, Object> mParams;

    /**
     * Converts JSON string into NRFAQDataObject
     *
     * @param params HashMap with the NRFAQDataObject params
     */
    public NRFAQDataObject(HashMap<String, Object> params) {
        mParams = params;
    }

    /**
     *
     * @return value of instanceId
     */
    public String getInstanceId() {
        return (String)mParams.get("instanceId");
    }

    /**
     * Generates ArrayList of NRFAQGroupItem
     *
     * @return ArrayList of NRFAQGroupItem
     */
    public ArrayList<NRFAQGroupItem> getData() {
        ArrayList<NRFAQGroupItem> arr = null;
        ArrayList<HashMap<String, Object>> data = (ArrayList)mParams.get("data");
        if (mData == null && data.size() > 0) {
            arr = new ArrayList<NRFAQGroupItem>();
            for (HashMap<String, Object> map: data) {
                arr.add(new NRFAQGroupItem(map));
            }
            mData = new ArrayList<NRFAQGroupItem>(arr);
            arr = null;
        }
        return mData;
    }

    /**
     *
     * @return value of lang
     */
    public String getLanguage() {
        return (String)mParams.get("lang");
    }

    /**
     *
     * @return value of cacheVar
     */
    public String getCacheVar() {
        return (String)mParams.get("cacheVar");
    }

    /**
     *
     * @return value of integrate_ga
     */
    public boolean isIntegrate() {
        return Boolean.parseBoolean((String)mParams.get("integrate_ga"));
    }
}
