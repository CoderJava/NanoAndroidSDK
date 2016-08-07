package nanorep.Chnneling;

import java.util.HashMap;

/**
 * Created by nissimpardo on 29/12/15.
 */
public class NRChannelingChatForm extends NRChanneling {
    private String chatProvider;
    private String initialiseStatus;
    private String agentSkill;
    private String waitTime;
    private String preChat;
    private String postChat;
    private boolean hideSendToEmail;
    private boolean isPopup;
    private String popupSize;
    private String otherChatProviderValues;
    private String accountNum;

    public NRChannelingChatForm(HashMap<String, Object> params) {
        super(params);
        chatProvider = (String)params.get("chatProvider");
        accountNum = (String)params.get("accountNum");
        initialiseStatus = (String)params.get("initialiseStatus");
        agentSkill = (String)params.get("agentSkill");
        waitTime = (String)params.get("waitTime");
        preChat = (String)params.get("preChat");
        postChat = (String)params.get("postChat");
        hideSendToEmail = Boolean.parseBoolean((String)params.get("hideSendToEmail"));
        isPopup = Boolean.parseBoolean((String)params.get("isPopup"));
        popupSize = (String)params.get("popupSize");
        otherChatProviderValues = (String)params.get("otherChatProviderValues");
        accountNum = (String)params.get("accountNum");
        this.type = NRChannelingType.ChatForm;
    }

    public String getChatProvider() {
        return chatProvider;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getInitialiseStatus() {
        return initialiseStatus;
    }

    public String getAgentSkill() {
        return agentSkill;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public String getPreChat() {
        return preChat;
    }

    public String getPostChat() {
        return postChat;
    }

    public boolean getHideSendToEmail() {
        return hideSendToEmail;
    }

    public boolean getIsPopup() {
        return isPopup;
    }

    public String getPopupSize() {
        return popupSize;
    }

    public String getOtherChatProviderValues() {
        return otherChatProviderValues;
    }
}
