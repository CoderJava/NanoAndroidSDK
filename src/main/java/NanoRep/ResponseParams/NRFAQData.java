package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissimpardo on 14/12/15.
 */
public class NRFAQData {
    private HashMap <String, Object> mParams;
    private ArrayList<NRFAQGroupItem> mGroups;
    private String mTitle;
    private Integer mBehavior;

    public NRFAQData(HashMap params) {
        mParams = params;
    }

    public ArrayList<NRFAQGroupItem> getGroups() {
        if (mGroups == null) {
            ArrayList <HashMap> groups;
            if (mParams == null || (groups = (ArrayList)mParams.get("data")) == null) {
               return null;
            }

            mGroups = new ArrayList<NRFAQGroupItem>();
            for (HashMap<String, Object> group: groups) {
                mGroups.add(new NRFAQGroupItem(group));
            }
        }
        return mGroups;
    }

    public String getTitle() {
        if (mTitle == null) {
            if (mParams == null) {
                return null;
            }
            mTitle = (String)mParams.get("title");
        }
        return mTitle;
    }

    public int getBehavior() {
        if (mBehavior == null) {
            if (mParams == null) {
                return 0;
            }
            mBehavior = (Integer)mParams.get("behavior");
        }
        return mBehavior.intValue();
    }

}
