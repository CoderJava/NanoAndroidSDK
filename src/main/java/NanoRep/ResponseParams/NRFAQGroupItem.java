package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;

import NanoRep.Interfaces.NRQueryResult;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQGroupItem {
    private ArrayList<NRQueryResult> mData;
    private HashMap<String, Object> mParams;

    /**
     * Converts JSON string to NRFAQGroupItem
     *
     * @param params HashMap with NRFAQGroupItem params
     */
    public NRFAQGroupItem(HashMap<String, Object> params) {
        mParams = params;
    }

    private HashMap getFirstGroup() {
        if (mParams.get("data") != null && ((ArrayList)mParams.get("data")).get(0) != null) {
            return (HashMap)((ArrayList)mParams.get("data")).get(0);
        }
        return null;
    }

    /**
     *
     * @return value of id
     */
    public Integer getGroupId() {
        if (getFirstGroup() != null) {
            return (Integer)getFirstGroup().get("id");
        }
        return null;
    }

    public String getTitle() {
        return (String)mParams.get("title");
    }

    public Integer getBehavior() {
        return (Integer)mParams.get("behavior");
    }

    /**
     * Generates ArrayList of NRFAQAnswerItem
     *
     * @return ArrayList of NRFAQAnswerItem
     */
    public ArrayList<NRQueryResult> getAnswers() {
        if (getFirstGroup() == null) {
            return null;
        }
        ArrayList<NRFAQAnswerItem> arr = null;
        ArrayList<HashMap<String, Object>> data = (ArrayList)getFirstGroup().get("data");
        if (mData == null && data.size() > 0) {
            arr = new ArrayList<NRFAQAnswerItem>();
            for (HashMap<String, Object> map: data) {
                arr.add(new NRFAQAnswerItem(map));
            }
            mData = new ArrayList<NRQueryResult>(arr);
        }
        return mData;
    }
}
