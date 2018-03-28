package com.dedalexey.mathbattle.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dedalexey.mathbattle.Activities.GameActivity;
import com.dedalexey.mathbattle.Activities.HomeActivity;
import com.dedalexey.mathbattle.Activities.StatisticsActivity;
import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.MenuRow;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {

        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    MenuFragment mMenuFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmant_home,container,false);

        FragmentManager fm = getChildFragmentManager();
        mMenuFragment = (MenuFragment) fm.findFragmentById(R.id.menu_container);

        if(mMenuFragment ==null) {
            mMenuFragment = MenuFragment.newInstance(getMenuRowList(),R.layout.layout_row_image_text);
            fm.beginTransaction()
                    .replace(R.id.menu_container, mMenuFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

        return view;
    }

    public List<MenuRow> getMenuRowList(){
        List<MenuRow> menuRowList = new ArrayList<>();
        MenuRow playMenuRow = new MenuRow(getString(R.string.play), R.drawable.ic_action_play);
        playMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GameActivity.class);
                startActivity(intent);
            }
        });

        MenuRow statisticsMenuRow = new MenuRow(getString(R.string.statistics), R.drawable.ic_action_statistics);
        statisticsMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatisticsActivity.class);
                startActivity(intent);
            }
        });

        MenuRow exitMenuRow = new MenuRow(getString(R.string.exit), R.drawable.ic_action_exit);
        exitMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finishAffinity();
            }
        });

//        MenuRow settingsMenuRow = new MenuRow(getString(R.string.settings), R.drawable.ic_action_settings);
//        settingsMenuRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),R.string.settings,Toast.LENGTH_SHORT).show();
//            }
//        });

        menuRowList.add(playMenuRow);
        menuRowList.add(statisticsMenuRow);
        //menuRowList.add(settingsMenuRow);
        menuRowList.add(exitMenuRow);
        return menuRowList;
    }
}
