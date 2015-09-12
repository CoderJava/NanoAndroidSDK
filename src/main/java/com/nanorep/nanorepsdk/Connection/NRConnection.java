package com.nanorep.nanorepsdk.Connection;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRConnection {
    static private ArrayList<NRDownloader> mConnections;
    public interface NRConnectionListener {
        public void response(HashMap responseParam, NRError error);
    }

    public static void connectionWithRequest(String url, final NRConnectionListener listener) {
        NRDownloader downloader = new NRDownloader(new NRDownloader.NRDownloaderListener() {
            @Override
            public void downloadCompleted(NRDownloader downloader, byte[] data, NRError error) {
                if (listener != null) {
                    if (error != null) {
                        listener.response(null, error);
                    } else if (data != null) {
                        String jsonString = new String(data);
                        HashMap<String, Object> retMap = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {}.getType());
                        listener.response(retMap, null);
                    }
                }
            }
        });
        downloader.execute(url);
    }

    public static void cancelAllConnections() {

    }

    static private ArrayList<NRDownloader> getConnections() {
        synchronized (mConnections) {
            if (mConnections == null) {
                mConnections = new ArrayList<NRDownloader>();
            }
            return mConnections;
        }
    }
}
