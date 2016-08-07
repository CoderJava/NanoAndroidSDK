package nanorep.ResponseParams;

import java.util.HashMap;

/**
 * Created by nissopa on 9/14/15.
 */
public class NRFAQAnswer {
    HashMap<String, Object> mParams;

    /**
     * Converts JSON string to NRFAQAnswer object
     *
     * @param params HashMap generated from json string
     */
    public NRFAQAnswer(HashMap<String, Object> params) {
        mParams = params;
    }

    /**
     * Fetches Title of answer
     *
     * @return Value of answer's title
     */
    public String getTitle() {
        return (String)mParams.get("title");
    }

    /**
     * Fetches short description of the answer in html string
     *
     * @return Value of body
     */
    public String getBody() {
        return (String)mParams.get("body");
    }

    /**
     *
     * @return Value of attachments
     */
    public String getAttachments() {
        return (String)mParams.get("attachments");
    }

    /**
     * Fetches amount of likes
     *
     * @return Value of like param
     */
    public int getLikes() {
        return Integer.parseInt((String)mParams.get("likesCount"));
    }

}
