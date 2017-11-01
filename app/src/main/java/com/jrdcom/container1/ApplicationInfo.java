package com.jrdcom.container1;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by user on 10/31/17.
 */

public class ApplicationInfo {

    void setmStrLabel(String mStrLabel) {
        this.mStrLabel = mStrLabel;
    }

    void setmBitMapIcon(Bitmap mBitMapIcon) {
        this.mBitMapIcon = mBitMapIcon;
    }

    void setmIntent(Intent mIntent) {
        this.mIntent = mIntent;
    }

    void setmType(int mType) {
        this.mType = mType;
    }

    public String getmStrLabel() {
        return mStrLabel;
    }

    public Bitmap getmBitMapIcon() {
        return mBitMapIcon;
    }

    public Intent getmIntent() {
        return mIntent;
    }

    public int getmType() {
        return mType;
    }

    private String mStrLabel;
    private Bitmap mBitMapIcon;
    private Intent mIntent;
    private int mType;// 0: url, 1:app shortcuts

    public static int TYPE_URL = 0x00;
    public static int TYPE_APP_SHORTCUTS = 0x01;

}
