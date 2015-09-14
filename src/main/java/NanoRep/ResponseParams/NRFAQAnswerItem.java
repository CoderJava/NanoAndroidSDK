package NanoRep.ResponseParams;

import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQAnswerItem {
    private HashMap<String, Object> mParams;

    public NRFAQAnswerItem(HashMap<String, Object> params) {
        mParams = params;
    }

    public int getCount() {
        return Integer.parseInt((String)mParams.get("count"));
    }

    public int getData() {
        return Integer.parseInt((String)mParams.get("data"));
    }

    public String getLabel() {
        return (String)mParams.get("label");
    }

    public int getLikes() {
        return Integer.parseInt((String)mParams.get("likes"));
    }

    public String getObjectId() {
        return (String)mParams.get("objectId");
    }

    public float getPercent() {
        return Float.parseFloat((String)mParams.get("percent"));
    }
}
