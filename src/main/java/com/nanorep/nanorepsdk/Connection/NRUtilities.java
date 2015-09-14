package com.nanorep.nanorepsdk.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by nissopa on 9/14/15.
 */
public class NRUtilities {

    public static String buildURL(String accountName, String apiName, HashMap<String, String> params) {
        String link = "https://office.nanorep.com/~" + accountName + "/api/widget/v1/" + apiName + ".js?";
        if (params != null) {
            List<String> keys = new ArrayList<String>(params.keySet());
            Collections.sort(keys);
            for (String key : keys) {
                link += key + "=" + params.get(key) + "&";
            }
            link = link.substring(0, link.length() - 1);
        }
        return link;
    }

    public static String wrappedContext(HashMap<String, String > context) {
        String _context = "";
        for (String key: context.keySet()) {
            _context += key + ":" + context.get(key) + ",";
        }
        _context = _context.substring(0, _context.length() - 1);
        return _context;
    }
}
