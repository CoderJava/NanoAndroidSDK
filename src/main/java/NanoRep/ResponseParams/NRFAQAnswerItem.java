package NanoRep.ResponseParams;

import java.util.ArrayList;
import java.util.HashMap;

import NanoRep.Chnneling.NRChanneling;
import NanoRep.Interfaces.NRQueryResult;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQAnswerItem implements NRQueryResult {
    private HashMap<String, Object> mParams;
    private String mBody;
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
        return mParams.get("likes").toString();
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

    @Override
    public String getId() {
        return getObjectId();
    }

    @Override
    public String getTitle() {
        return getLabel();
    }

    @Override
    public void setBody(String body) {
        mBody = body;
    }

    @Override
    public String getBody() {
        return mBody;
    }

    @Override
    public boolean isCNF() {
        return true;
    }

    @Override
    public ArrayList<NRChanneling> getChanneling() {
        return null;
    }
}
