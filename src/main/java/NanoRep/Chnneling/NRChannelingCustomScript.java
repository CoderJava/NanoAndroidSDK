package NanoRep.Chnneling;

import java.util.HashMap;

/**
 * Created by nissimpardo on 29/12/15.
 */
public class NRChannelingCustomScript extends NRChanneling {
    private String scriptContent;

    public NRChannelingCustomScript(HashMap<String, Object> params) {
        super(params);
        scriptContent = (String)params.get("scriptContent");
        this.type = NRChannelingType.CustomScript;
    }

    public String getScriptContent() {
        return scriptContent;
    }
}
