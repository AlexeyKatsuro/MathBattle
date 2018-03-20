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
    private List<MenuRow> mMenuRowList = new ArrayList<>();
    private int mMenu_list_item_id;

    public static MenuFragment newInstance(List<MenuRow> menuRowList,int menu_list_item) {

        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setMenuRowList(menuRowList);
        fragment.setMenuListItem(menu_list_item);
        fragment.setArguments(args);
        return fragment;
    }

    public void setMenuRowList(List<MenuRow> menuRowList) {
        mMenuRowList = menuRowList;
    }

    public void setMenuListItem(int meni_list_item){
        mMenu_list_item_id = meni_list_item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new MenuRowAdapter(getActivity(), mMenu_list_item_id,mMenuRowList);
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
