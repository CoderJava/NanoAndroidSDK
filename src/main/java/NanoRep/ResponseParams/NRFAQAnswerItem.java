package NanoRep.ResponseParams;

import java.util.HashMap;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQAnswerItem {
    private HashMap<String, Object> mParams;

    /**
     * Converts JSON string to NRFAQAnswerItem object
     *
     * @param params HashMap generated from json string
     */
    public NRFAQAnswerItem(HashMap<String, Object> params) {
        mParams = params;
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return Integer.parseInt((String)mParams.get("count"));
    }

    /**
     *
     * @return
     */
    public int getData() {
        return Integer.parseInt((String)mParams.get("data"));
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return (String)mParams.get("label");
    }

    /**
     *
     * @return
     */
    public String getLikes() {
        return ((Integer)mParams.get("likes")).toString();
    }

    /**
     *
     * @return
     */
    public String getObjectId() {
        return (String)mParams.get("objectId");
    }

    /**
     *
     * @return
     */
    public float getPercent() {
        return Float.parseFloat((String)mParams.get("percent"));
    }
}
