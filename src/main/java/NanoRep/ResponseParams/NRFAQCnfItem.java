package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQCnfItem {
    private ArrayList<NRFAQGroupItem> mData;
    private HashMap<String, Object> mParams;

    public NRFAQCnfItem(HashMap<String, Object> params) {
        mParams = params;
    }

    public String getTitle() {
        return (String)mParams.get("title");
    }

    public Number getBehavior() {
        return (Number)mParams.get("behavior");
    }

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
}
