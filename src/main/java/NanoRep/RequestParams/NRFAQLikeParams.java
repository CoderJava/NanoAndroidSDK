package NanoRep.RequestParams;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQLikeParams extends NRRequestParams {
    private int mLikeType;
    private String mKeyboardName;
    private String mAnswerId;

    public void setLikeType(int likeType) {
        mLikeType = likeType;
        setValue(Integer.toString(likeType), "type");
    }

    public void setKeyboardName(String keyboardName) {
        mKeyboardName = keyboardName;
        setValue(keyboardName, "kb");
    }

    public void setAnswerId(String answerId) {
        mAnswerId = answerId;
        setValue(answerId, "id");
    }
}
