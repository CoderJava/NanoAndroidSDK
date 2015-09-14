package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQGroupItem {
    private ArrayList<NRFAQAnswerItem> mData;
    private HashMap<String, Object> mParams;

    public NRFAQGroupItem(HashMap<String, Object> params) {
        mParams = params;
    }

    public String getGroupId() {
        return (String)mParams.get("id");
    }

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
