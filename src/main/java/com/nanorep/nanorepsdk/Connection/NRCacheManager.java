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
    private static final String DATABASE_NAME = "CacheNano.db";
    private static final String TABLE_ANSWERS = "ANSWERS";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUE = "value";

    private static NRCacheManager mCacheManager;
    private static Context mContext;


    public NRCacheManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "Create table IF NOT EXISTS " +
                TABLE_ANSWERS + "("
                + COLUMN_ID + " TEXT," + COLUMN_VALUE
                + " BLOB)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        onCreate(db);
    }

    private static synchronized NRCacheManager getCacheManager() {
        if (mCacheManager == null) {
            mCacheManager = new NRCacheManager(mContext);
        }
        return mCacheManager;
    }

    public static HashMap<String, Object> getAnswerById(Context context, String answerId) {
        mContext = context;
        String query = "Select * FROM " + TABLE_ANSWERS + " WHERE " + COLUMN_ID + " =  \"" + answerId + "\"";
        SQLiteDatabase db = getCacheManager().getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        byte[] storedData = null;
        if (cursor.moveToFirst()) {
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

    public static void storeAnswerById(Context context, String answerId, HashMap<String, Object> answerParams) {
        // Convert Map to byte array
        mContext = context;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(answerParams);
            byte[] data = byteOut.toByteArray();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, answerId.trim());
            values.put(COLUMN_VALUE, data);
            SQLiteDatabase db = getCacheManager().getWritableDatabase();
            db.insertWithOnConflict(TABLE_ANSWERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            out.close();
            byteOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
