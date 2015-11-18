package com.nanorep.nanorepsdk.Connection;
import android.os.AsyncTask;

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
public class NRDownloader extends  AsyncTask <URL, Integer, Object> {
    private NRDownloaderListener mListener;
    public interface NRDownloaderListener {
        public void downloadCompleted(NRDownloader downloader, Object data, NRError error);
    }


    public NRDownloader(NRDownloaderListener listener) {
        super();
        mListener = listener;
    }

    @Override
    protected Object doInBackground(URL... urls) {
        URL url = urls[0];
        return getFileAtUrl(url);
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

    private Object getFileAtUrl(URL url){
        byte[] data = null;
        try{
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
        }catch (Exception e){

            e.printStackTrace();
            return NRError.error("Connection", 1000, e.getMessage());
        }
        return data;
    }
//    private Object getFileAtUrl(URL url) {
//        String result = "";
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
//            String line = null;
//            while ((line = in.readLine())  != null) {
//                result += line;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
}
