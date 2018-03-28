package com.dedalexey.mathbattle.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.dedalexey.mathbattle.model.SessionInfo;



public class GameFragment extends Fragment {

    protected SessionInfo mSessionInfo;

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
}
