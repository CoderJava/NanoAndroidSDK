package com.nanorep.nanorepsdk.Connection;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;

import com.nanorep.nanorepsdk.BuildConfig;
import com.nanorep.nanorepsdk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    public static Map<String, Object> jsonStringToMap(String jsonString){
        HashMap map = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            map = (HashMap) mapFromJson(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }
    public static Map<String, Object> mapFromJson(JSONObject jsonObject){
        HashMap<String, Object> map = new HashMap<String, Object>();
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                map.put(key, fromJson(jsonObject.get(key)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    public static String buildReferer(String referer) {
        String ref = "app/" + BuildConfig.APPLICATION_ID + "/Android/" + Integer.toString(BuildConfig.VERSION_CODE) + "/" + referer;
        return ref;
    }

    public static URL getRequest(String link, String referer) {
        URL url = null;
        try {
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            if (referer != null) {
                connection.setRequestProperty("Referer", buildReferer(referer));
            }
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL getFAQRequest(HashMap<String, String> params) {
        String api = params.get("api");
        String link = "https://office.nanorep.com/~" + params.get("account");
        URL url = null;
        if (api != null) {
            link += (api.equals("cnf") ? "/widget/scripts/" + api + ".json" : "/api/faq/v1/" + api + ".js") + "?";
            params.remove("api");
            for (String key: params.keySet()) {
                link += key + "=" + params.get(key) + "&";
            }
            link = link.substring(0, link.length() - 1);
        }
        try {
//            link = URLEncoder.encode(link, "utf-8");
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Referer", buildReferer(params.get("referer")));
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static final String md5(HashMap<String, String> params) {
        final String MD5 = "MD5";
        String api = params.get("api");
        String link = "https://office.nanorep.com/~" + params.get("account");
        if (api != null) {
            link += (api.equals("cnf") ? "/widget/scripts/" + api + ".json" : "/api/faq/v1/" + api + ".js") + "?";
            params.remove("api");
            for (String key: params.keySet()) {
                link += key + "=" + params.get(key) + "&";
            }
            link = link.substring(0, link.length() - 1);
        }
        try {
            // Create MD5 Hash
            java.security.MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(link.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
