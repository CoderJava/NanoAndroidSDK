package com.nanorep.nanorepsdk.Connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by nissopa on 10/7/15.
 */
public class NRCacheManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FAQCache.db";
    private static final String TABLE_ANSWERS = "answers";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUE = "value";


    public NRCacheManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_ANSWERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_VALUE
                + " BLOB)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        onCreate(db);
    }

    public static HashMap<String, Object> getAnswerById(Context context, String link) {
        String query = "Select * FROM " + TABLE_ANSWERS + " WHERE " + COLUMN_ID + " =  \"" + md5(link) + "\"";
        NRCacheManager manager = new NRCacheManager(context);
        SQLiteDatabase db = manager.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        byte[] storedData = null;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            storedData = cursor.getBlob(1);
            cursor.close();
            if (storedData != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(storedData);
                ObjectInput in = null;
                try {
                    in = new ObjectInputStream(bis);
                    return (HashMap<String, Object>)in.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        db.close();
        return null;
    }

    public static void storeAnswerById(Context context, String link, HashMap<String, Object> answerId) {
        // Convert Map to byte array
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(answerId);
            byte[] data = byteOut.toByteArray();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, md5(link));
            values.put(COLUMN_VALUE, data);
            NRCacheManager manager = new NRCacheManager(context);
            SQLiteDatabase db = manager.getWritableDatabase();
            db.insert(TABLE_ANSWERS, null, values);
            db.close();
            out.close();
            byteOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            java.security.MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
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
