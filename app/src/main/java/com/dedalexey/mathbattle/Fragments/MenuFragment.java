package com.dedalexey.mathbattle.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dedalexey.mathbattle.Activities.GameActivity;
import com.dedalexey.mathbattle.MenuRowAdapter;
import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.MenuRow;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private static final String TAG = MenuFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MenuRowAdapter mAdapter;
    private List<MenuRow> mMenuRowList;

    public static MenuFragment newInstance() {

        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMenuRowList = new ArrayList<>();
        MenuRow playMenuRow = new MenuRow(getString(R.string.play), R.drawable.ic_action_play);
        playMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GameActivity.class);
                startActivity(intent);
            }
        });
        MenuRow settingsMenuRow = new MenuRow(getString(R.string.settings), R.drawable.ic_action_settings);
        settingsMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),R.string.settings,Toast.LENGTH_SHORT).show();
            }
        });
        MenuRow exitMenuRow = new MenuRow(getString(R.string.exit), R.drawable.ic_action_exit);
        exitMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finishAffinity();
            }
        });

        mMenuRowList.add(playMenuRow);
        //mMenuRowList.add(settingsMenuRow);
        mMenuRowList.add(exitMenuRow);

        mAdapter = new MenuRowAdapter(getActivity(),R.layout.menu_list_item,mMenuRowList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        mRecyclerView = view.findViewById(R.id.fragment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


}
