package com.dedalexey.mathbattle.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.SessionInfo;

/**
 * Created by Alexey on 24.01.2018.
 */

public class BeforeGameFragment extends GameFragment {

    public static final String TAG = BeforeGameFragment.class.getSimpleName();
    private boolean isReady;

    private CheckBox mReadyCheckBox;
    private CallBacks mCallBacks;

    public interface CallBacks {
        void onReadyClick(boolean isReady);
    }


    public static BeforeGameFragment newInstance(SessionInfo sessionInfo) {
        BeforeGameFragment fragment = new BeforeGameFragment();
        fragment.setSessionInfo(sessionInfo);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        initCallBacksListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_before_game, container, false);
        mReadyCheckBox = view.findViewById(R.id.ready_check_box);
        mReadyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG,"onReadyClick");

                    mCallBacks.onReadyClick(b);
            }
        });
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");

        mCallBacks = null;
    }
    private void initCallBacksListener() {
        Log.d(TAG,"initCallBacksListener");
        Fragment fragment = getParentFragment();
        if ( fragment instanceof CallBacks) {
            mCallBacks = (CallBacks) fragment;
        } else {
            Log.i(TAG,"fragment NOT instanceof CallBacks");
        }
    }

}
