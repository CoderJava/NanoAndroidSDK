package com.nanorep.nanorepsdk.Connection;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRDownloader extends  AsyncTask <Uri, Integer, Object> {
    private NRDownloaderListener mListener;
    private int mStatus;

    public interface NRDownloaderListener {
        void downloadCompleted(NRDownloader downloader, Object data, NRError error);
    }


    public NRDownloader(NRDownloaderListener listener) {
        super();
        mListener = listener;
    }

    public int getResponseStatus() {
        return mStatus;
    }

    @Override
    protected Object doInBackground(Uri... uris) {
        Uri uri = uris[0];
        URL url = null;
        byte[] data = null;
        try{
            url = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Referer", uri.getQueryParameter("referer"));
            connection.connect();
            InputStream inputStream = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = inputStream.read();
            while (next > -1){
                bos.write(next);
                next = inputStream.read();
            }

            data = bos.toByteArray();
            bos.flush();
            bos.close();
            mStatus = connection.getResponseCode();
        }catch (Exception e){

            e.printStackTrace();
            return NRError.error("Connection", 1000, e.getMessage());
        }
        return data;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        listener.partialDownload(values[0]);
    }

    @Override
    protected void onPostExecute(Object bytes){
        if (bytes instanceof NRError) {
            mListener.downloadCompleted(this, null, (NRError)bytes);
        } else if (((byte[])bytes).length > 0) {
            mListener.downloadCompleted(this, bytes, null);
        } else {
            mListener.downloadCompleted(this, null, NRError.error("Parsed Response", 1001, "Empty response"));
        }
    }
}
