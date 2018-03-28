package com.dedalexey.mathbattle.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.SessionInfo;


public class GameManagerFragment extends Fragment
        implements BeforeGameFragment.CallBacks, DuringGameFragment.CallBacks,  AfterGameFragment.CallBacks{

    private static final String TAG = GameManagerFragment.class.getSimpleName();

    private boolean isReady;
//    private BeforeGameFragment mBeforeGameFragment;
//    private DuringGameFragment mDuringGameFragment;
//    private AfterGameFragment mAfterGameFragment;
    private GameFragment mCurrentGameFragment;
    private SessionInfo mSessionInfo;

    public static GameManagerFragment newInstance() {

        Bundle args = new Bundle();
        GameManagerFragment fragment = new GameManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_game,container,false);

        if(mCurrentGameFragment==null) {
            Log.d(TAG,"onCreateView/ Create new Current Game Fragment");
            mSessionInfo = new SessionInfo();
            mCurrentGameFragment = BeforeGameFragment.newInstance(mSessionInfo);
        }

        replaceFragment(mCurrentGameFragment);

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_play_container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onReadyClick(boolean isReady) {
       this.isReady = isReady;
        if(this.isReady){
            mCurrentGameFragment = DuringGameFragment.newInstance(mSessionInfo);
            replaceFragment(mCurrentGameFragment);
        }
//        Log.d(TAG,"Send to DuringGF: "+mSessionInfo!=null ? mSessionInfo.toString(): "null");
    }

    @Override
    public void onEndGame() {
        isReady= false;
        mCurrentGameFragment = AfterGameFragment.newInstance(mSessionInfo);

        replaceFragment(mCurrentGameFragment);
    }

    @Override
    public void onRepeatClick() {
        mSessionInfo = new SessionInfo();
        mCurrentGameFragment = BeforeGameFragment.newInstance(mSessionInfo);
        replaceFragment(mCurrentGameFragment);
    }

}
