package nanorep.RequestParams;

import nanorep.Interfaces.NRQueryResult;

/**
 * Created by nissopa on 9/13/15.
 */
public class NRFAQParams extends NRRequestParams {
    private int mDays;
    private int mItems;
    private int mKnowledgeBase;

    public NRFAQParams(NRQueryResult result) {
        super(result);
    }

    /**
     * How many days worth of statistics to return.
     *
     * @param days
     */
    public void setDays(int days) {
        mDays = days;
        setValue(Integer.toString(days), "days");
    }

    /**
     * How many items for response
     *
     * @param items
     */
    public void setItems(int items) {
        mItems = items;
        setValue(Integer.toString(items), "items");
    }

    /**
     * Set the knowledgeBase for the request
     *
     * @param knowledgeBase
     */
    public void setKnowledgeBase(int knowledgeBase) {
        mKnowledgeBase = knowledgeBase;
        setValue(Integer.toString(knowledgeBase), "kb");
    }
}
