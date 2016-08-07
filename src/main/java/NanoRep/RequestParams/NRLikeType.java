package nanorep.RequestParams;

/**
 * Created by nissimpardo on 18/11/15.
 */
public enum  NRLikeType {
    POSITIVE(1),
    INCORRECT_ANSWER(2),
    MISSING_INFORMATION(4),
    IRRELEVANT(8);

    private int mState;
    NRLikeType(int state) {
        mState = state;
    }

    @Override
    public String toString() {
        return Integer.toString(mState);
    }
}
