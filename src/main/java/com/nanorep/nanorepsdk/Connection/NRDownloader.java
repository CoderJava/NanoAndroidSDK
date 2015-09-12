package com.nanorep.nanorepsdk.Connection;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRDownloader extends  AsyncTask <String, Integer, byte[]> {
    private NRDownloaderListener mListener;
    public interface NRDownloaderListener {
        public void downloadCompleted(NRDownloader downloader, byte[] data, NRError error);
    }

    public NRDownloader(NRDownloaderListener listener) {
        super();
        mListener = listener;
    }

    @Override
    protected byte[] doInBackground(String... urls) {
        String linkToFile = urls[0];
        return getFileAtUrl(linkToFile);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        listener.partialDownload(values[0]);
    }

    @Override
    protected void onPostExecute(byte[] bytes){
        mListener.downloadCompleted(this, bytes, null);
    }

    private byte[] getFileAtUrl(String url){
        byte[] data = null;
        try{
            URL link = new URL(url);
            URLConnection connection = link.openConnection();
            connection.connect();
            InputStream inputStream = new BufferedInputStream(link.openStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = inputStream.read();
            while (next > -1){
                bos.write(next);
                next = inputStream.read();
            }
            bos.flush();
            data = bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
