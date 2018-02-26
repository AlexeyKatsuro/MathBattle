package com.dedalexey.mathbattle.model;

import android.view.View;

/**
 * Created by Alexey on 20.01.2018.
 */

public class MenuRow {
    String mTitle;
    int mImageId;
    View.OnClickListener mOnClickListener;

    public MenuRow(String title, int imageId) {
        mTitle = title;
        mImageId = imageId;
    }

    public MenuRow(String title, int imageId, View.OnClickListener onClickListener){
        this(title,imageId);
        mOnClickListener = onClickListener;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
