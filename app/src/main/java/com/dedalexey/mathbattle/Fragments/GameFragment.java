package com.dedalexey.mathbattle.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dedalexey.mathbattle.Activities.GameActivity;
import com.dedalexey.mathbattle.model.SessionInfo;



public class GameFragment extends Fragment implements GameActivity.OnBackPressedListener {


    private static final String TAG = GameFragment.class.getSimpleName();
    protected SessionInfo mSessionInfo;
    private GameActivity mActivity;

    public static GameFragment newInstance(SessionInfo sessionInfo) {

        GameFragment fragment = new GameFragment();
        fragment.setSessionInfo(sessionInfo);
        return fragment;
    }

    public SessionInfo getSessionInfo() {
        return mSessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        mSessionInfo = sessionInfo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"onBackPressed");
        mActivity.setOnBackPressedListener(null);
        mActivity.onBackPressed();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof GameActivity){
            mActivity = (GameActivity)activity;
        }
    }
}
