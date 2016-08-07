package nanorep.Chnneling;

import java.util.HashMap;

/**
 * Created by nissimpardo on 29/12/15.
 */
public class NRChannelingOpenCustomURL extends NRChanneling {
    private String linkUrl;
    private String linkTarget;
    private String popupSize;
    public NRChannelingOpenCustomURL(HashMap<String, Object> params) {
        super(params);
        linkUrl = (String)params.get("linkUrl");
        linkTarget = (String)params.get("linkTarget");
        popupSize = (String)params.get("popupSize");
        this.type = NRChannelingType.OpenCustomURL;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getLinkTarget() {
        return linkTarget;
    }

    public String getPopupSize() {
        return popupSize;
    }
}
