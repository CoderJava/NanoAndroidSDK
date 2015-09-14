package NanoRep.RequestParams;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQParams extends NRRequestParams {
    private int mDays;
    private int mItems;
    private int mKnowledgeBase;

    public void setDays(int days) {
        mDays = days;
        setValue(Integer.toString(days), "days");
    }

    public void setItems(int items) {
        mItems = items;
        setValue(Integer.toString(items), "items");
    }

    public void setKnowledgeBase(int knowledgeBase) {
        mKnowledgeBase = knowledgeBase;
        setValue(Integer.toString(knowledgeBase), "kb");
    }
}
