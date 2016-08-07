package com.nanorep.nanorepsdk.Connection;

import android.net.Uri;
import android.util.Base64;

import com.nanorep.nanorepsdk.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by nissopa on 9/14/15.
 */
public class NRUtilities {

    public static String AccountNameKey = "account";
    public static String ApiNameKey = "api";
    public static String DomainKey = "DomainKey";

    public static String buildURL(HashMap<String, String> params) {
        String link =  "http://" + params.get(DomainKey) + "/~" + params.get(AccountNameKey) + "/api/widget/v1/" + params.get(ApiNameKey) + ".js?";
        params.remove(DomainKey);
        params.remove(AccountNameKey);
        params.remove(ApiNameKey);
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
        if (context == null) {
            return "";
        }
        for (String key: context.keySet()) {
            _context += key + ":" + context.get(key) + ",";
        }
        _context = _context.substring(0, _context.length() - 1);
        return _context;
    }

    public static String wrappedContextBase64(HashMap<String, String > context) {
        return Base64.encodeToString(wrappedContext(context).getBytes(), Base64.DEFAULT) ;
    }

    public static String autorityForAccount(String account) {
        return account + ".nanorep.com";
    }

    public static Object jsonStringToPropertyList(String jsonString){
        if (jsonString.startsWith("nanoRep.FAQ.faqData = ")) {
            jsonString = jsonString.substring(22);
        }
        Object propertyList = null;
        try {
            Object json = new JSONTokener(jsonString).nextValue();
            if (json instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(jsonString);
                propertyList = toList(jsonArray);
            } else if (json instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject(jsonString);
                propertyList = (HashMap) mapFromJson(jsonObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyList;
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
        String api = params.get(ApiNameKey);
        String link = "https://" + params.get(DomainKey) + "/~" + params.get(AccountNameKey);
        params.remove(DomainKey);
        URL url = null;
        if (api != null) {
            link += (api.equals("cnf.json") ? "/widget/scripts/" + api: "/api/faq/v1/" + api) + "?";
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

    public static URL configurationURL(Uri.Builder uriBuilder) {
        Uri uri = uriBuilder.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Referer", uri.getQueryParameter("referer"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static final String md5(String link) {
        final String MD5 = "MD5";
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
