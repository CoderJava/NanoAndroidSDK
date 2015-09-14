package NanoRep.ResponseParams;

import java.util.HashMap;

/**
 * Created by nissopa on 9/14/15.
 */
public class NRFAQAnswer {
    HashMap<String, Object> mParams;

    public NRFAQAnswer(HashMap<String, Object> params) {
        mParams = params;
    }

    public String getTitle() {
        return (String)mParams.get("title");
    }

    public String getBody() {
        return (String)mParams.get("body");
    }

    public String getAttachments() {
        return (String)mParams.get("attachments");
    }

    public int getLikes() {
        return Integer.parseInt((String)mParams.get("likesCount"));
    }

}
