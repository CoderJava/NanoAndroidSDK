package nanorep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissimpardo on 14/12/15.
 */
public class NRFAQData {
    private ArrayList <HashMap<String, Object>> mParams;
    private ArrayList<NRFAQGroupItem> mGroups;

    public NRFAQData(ArrayList params) {
        mParams = params;
    }

    public ArrayList<NRFAQGroupItem> getGroups() {
        if (mGroups == null) {
            ArrayList <HashMap> groups;
            if (mParams == null) {
               return null;
            }

            mGroups = new ArrayList<NRFAQGroupItem>();
            for (HashMap<String, Object> group: mParams) {
                mGroups.add(new NRFAQGroupItem(group));
            }
        }
        return mGroups;
    }
}
