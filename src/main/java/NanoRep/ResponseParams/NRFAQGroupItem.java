package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQGroupItem {
    private ArrayList<NRFAQAnswerItem> mData;
    private HashMap<String, Object> mParams;

    /**
     * Converts JSON string to NRFAQGroupItem
     *
     * @param params HashMap with NRFAQGroupItem params
     */
    public NRFAQGroupItem(HashMap<String, Object> params) {
        mParams = params;
    }

    /**
     *
     * @return value of id
     */
    public String getGroupId() {
        return (String)mParams.get("id");
    }

    /**
     * Generates ArrayList of NRFAQAnswerItem
     *
     * @return ArrayList of NRFAQAnswerItem
     */
    public ArrayList<NRFAQAnswerItem> getData() {
        ArrayList<NRFAQAnswerItem> arr = null;
        ArrayList<HashMap<String, Object>> data = (ArrayList)mParams.get("data");
        if (mData == null && data.size() > 0) {
            arr = new ArrayList<NRFAQAnswerItem>();
            for (HashMap<String, Object> map: data) {
                arr.add(new NRFAQAnswerItem(map));
            }
            mData = new ArrayList<NRFAQAnswerItem>(arr);
            arr = null;
        }
        return mData;
    }
}
