package nanorep;

import android.content.Context;
import java.util.HashMap;


/**
 * Created by nissimpardo on 07/08/2016.
 */

public class NanorepBuilder {
    public static void initializeNanorep(Context context, String accountName) {
        Nanorep nanorep = createNanorep(context, accountName, null, null);
        nanorep.fetchConfiguration(null);
    }

    public static Nanorep createNanorep(Context context, String accountName, String knowledgeBase, HashMap<String, String> nanorepContext) {
        Nanorep.AccountParams accountParams = new Nanorep.AccountParams();
        accountParams.setAccount(accountName);
        accountParams.setKnowledgeBase(knowledgeBase);
        accountParams.setContext(nanorepContext);
        return createNanorep(context, accountParams);
    }

    public static Nanorep createNanorep(Context context, Nanorep.AccountParams accountParams) {
        NRImpl nanorep = new NRImpl(context, accountParams);
        return nanorep;
    }

}
