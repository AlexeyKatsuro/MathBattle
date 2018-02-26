package com.dedalexey.mathbattle.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dedalexey.mathbattle.R;


public class GameManagerFragment extends Fragment
        implements BeforeGameFragment.CallBacks, DuringGameFragment.CallBacks,  AfterGameFragment.CallBacks{

    private static final String TAG = GameManagerFragment.class.getSimpleName();

    private boolean isReady;
    private BeforeGameFragment mBeforeGameFragment;
    private DuringGameFragment mDuringGameFragment;
    private AfterGameFragment mAfterGameFragment;

    public static GameManagerFragment newInstance() {

        Bundle args = new Bundle();
        GameManagerFragment fragment = new GameManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game,container,false);
        mBeforeGameFragment = BeforeGameFragment.newInstance();
        replaceFragment(mBeforeGameFragment);

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
            mDuringGameFragment = DuringGameFragment.newInstance();
            replaceFragment(mDuringGameFragment);
        }
    }

    @Override
    public void onEndGame() {
        isReady= false;
        mAfterGameFragment = AfterGameFragment.newInstance();
        replaceFragment(mAfterGameFragment);
    }

    @Override
    public void onRepeatClick() {

        mBeforeGameFragment = BeforeGameFragment.newInstance();
        replaceFragment(mBeforeGameFragment);
    }
}
