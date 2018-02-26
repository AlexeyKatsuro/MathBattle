package com.dedalexey.mathbattle.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dedalexey.mathbattle.MenuRowAdapter;
import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.MenuRow;

import java.util.ArrayList;
import java.util.List;


public class AfterGameFragment extends Fragment {

    public static final String TAG = AfterGameFragment.class.getSimpleName();

    RecyclerView mRecyclerView;

    private List<MenuRow> mMenuRowList;
    private MenuRowAdapter mAdapter;
    private CallBacks mCallBacks;

    public interface CallBacks {
        void onRepeatClick();
    }


    public static AfterGameFragment newInstance() {

        Bundle args = new Bundle();

        AfterGameFragment fragment = new AfterGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initCallBacksListener();

        mMenuRowList = new ArrayList<>();
        MenuRow HomeMenuRow = new MenuRow(getString(R.string.home), R.drawable.ic_action_home);
        HomeMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        MenuRow repeatMenuRow = new MenuRow(getString(R.string.repeat), R.drawable.ic_action_repeat);
        repeatMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallBacks!=null) {
                    mCallBacks.onRepeatClick();
                }
            }
        });

        MenuRow statisticsMenuRow = new MenuRow(getString(R.string.statistics), R.drawable.ic_action_statistics);
        statisticsMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),R.string.statistics,Toast.LENGTH_SHORT).show();
            }
        });

        MenuRow exitMenuRow = new MenuRow(getString(R.string.exit), R.drawable.ic_action_exit);
        exitMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finishAffinity();
            }
        });

        mMenuRowList.add(HomeMenuRow);
        mMenuRowList.add(repeatMenuRow);
        //mMenuRowList.add(statisticsMenuRow);
        mMenuRowList.add(exitMenuRow);
        mAdapter = new MenuRowAdapter(getActivity(),R.layout.layout_row_image_text,mMenuRowList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_after_game, container, false);
        mRecyclerView = view.findViewById(R.id.fragment_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void initCallBacksListener() {
        Fragment fragment = getParentFragment();
        if ( fragment instanceof CallBacks) {
            mCallBacks = (CallBacks) fragment;
        } else {
            Log.i(TAG,"fragment NOT instanceof CallBacks");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallBacks = null;
    }
}
