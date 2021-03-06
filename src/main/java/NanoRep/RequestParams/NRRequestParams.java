package NanoRep.RequestParams;

import java.util.HashMap;

import NanoRep.NanoRep;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRRequestParams {
    private HashMap<String, String> mParams;

    public HashMap<String, String> getParams() {
        if (mParams == null) {
            mParams = new  HashMap<String, String>();
        }
        return mParams;
    }

    public void setValue(String value, String forKey) {
        if (value != null && forKey != null) {
            getParams().put(forKey, value);
        }
    }
}
