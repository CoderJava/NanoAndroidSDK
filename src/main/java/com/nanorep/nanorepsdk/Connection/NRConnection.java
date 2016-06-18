package com.nanorep.nanorepsdk.Connection;




import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRConnection {
    public static String NRStatusKey = "status";

    static private ArrayList<NRDownloader> mConnections;
    public interface NRConnectionListener {
        void response(Object responseParam, NRError error);
    }

    public static void connectionWithRequest(URL url, final NRConnectionListener listener) {
        Log.d("RequestURL", url.toString());
        NRDownloader downloader = new NRDownloader(new NRDownloader.NRDownloaderListener() {
            @Override
            public void downloadCompleted(NRDownloader downloader, Object data, NRError error) {
                if (listener != null) {
                    if (error != null) {
                        listener.response(null, error);
                    } else if (data != null) {
                        String jsonString = new String((byte[])data);
                        Object retMap = NRUtilities.jsonStringToPropertyList(jsonString);
                        listener.response(retMap, null);
                    }
                }
                if (NRConnection.mConnections != null && NRConnection.mConnections.contains(downloader)) {
                    NRConnection.mConnections.remove(downloader);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        } else {
            downloader.execute(url);
        }

    }

    public static void cancelAllConnections() {
        ArrayList<NRDownloader> downloaders = new ArrayList<NRDownloader>(getConnections());
        for (NRDownloader downloader: downloaders) {
            downloader.cancel(true);
            getConnections().remove(downloader);
            downloader = null;
        }
        mConnections = null;
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
