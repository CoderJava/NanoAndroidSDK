package com.nanorep.nanorepsdk.Connection;

/**
 * Created by nissopa on 9/12/15.
 */
public class NRError {
    String mDomain;
    int mCode;
    String mDescription;

    public static NRError error(String domain, int code, String description) {
        NRError error = new NRError();
        return error;
    }

    private void setDomain(String domain) {
        mDomain = domain;
    }

    private void setCode(int code) {
        mCode = code;
    }

    private void setmDescription(String description) {
        mDescription = description;
    }

    public String getDomain() {
        return mDomain;
    }

    public int getCode() {
        return mCode;
    }

    public String getDescription() {
        return mDescription;
    }
}
