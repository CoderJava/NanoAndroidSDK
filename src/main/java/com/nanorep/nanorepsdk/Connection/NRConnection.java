package com.nanorep.nanorepsdk.Connection;




import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRConnection {
    public static String NRStatusKey = "status";

    static private ArrayList<NRDownloader> mConnections;

    public interface Listener {
        void response(Object responseParam, int status , NRError error);
    }

    public static void connectionWithRequest(Uri uri, final Listener listener) {
        NRDownloader downloader = new NRDownloader(new NRDownloader.NRDownloaderListener() {
            @Override
            public void downloadCompleted(NRDownloader downloader, Object data, NRError error) {
                if (listener != null) {
                    if (error != null) {
                        listener.response(null, -1, error);
                    } else if (data != null) {
                        String jsonString = new String((byte[])data);
                        Object retMap = NRUtilities.jsonStringToPropertyList(jsonString);
                        listener.response(retMap, downloader.getResponseStatus(), null);
                    }
                }
                if (NRConnection.mConnections != null && NRConnection.mConnections.contains(downloader)) {
                    NRConnection.mConnections.remove(downloader);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
        } else {
            downloader.execute(uri);
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
