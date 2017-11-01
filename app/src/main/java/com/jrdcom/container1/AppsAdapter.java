package com.jrdcom.container1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 10/31/17.
 */

class AppsAdapter extends ArrayAdapter<ApplicationInfo> {

    private final LayoutInflater mInflater;

    AppsAdapter(Context context, ArrayList<ApplicationInfo> apps) {
        super(context, 0, apps);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ApplicationInfo info = getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.application_boxed,
                    parent, false);
        }

        final TextView textView = (TextView) convertView;

        Bitmap bitmapIcon = info.getmBitMapIcon();
        if(null != bitmapIcon) {
            info.getmBitMapIcon().setDensity(Bitmap.DENSITY_NONE);
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(null,
                new BitmapDrawable(info.getmBitMapIcon()), null, null);
        textView.setText(info.getmStrLabel());

        return convertView;
    }
}
