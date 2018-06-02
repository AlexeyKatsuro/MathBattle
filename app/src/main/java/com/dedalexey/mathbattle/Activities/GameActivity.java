package com.dedalexey.mathbattle.Activities;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.dedalexey.mathbattle.Fragments.GameManagerFragment;

/**
 * Created by Alexey on 20.01.2018.
 */

public class GameActivity extends SingleFragmentActivity {

    private static final String TAG = GameActivity.class.getSimpleName();
    private OnBackPressedListener mOnBackPressedListener;

    @Override
    public Fragment createFragment() {
        return GameManagerFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"onBackPressed");
        int count = getFragmentManager().getBackStackEntryCount();


        if(mOnBackPressedListener!=null){
            mOnBackPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        mOnBackPressedListener = onBackPressedListener;
    }

    public interface OnBackPressedListener {
        void onBackPressed();
    }
}
