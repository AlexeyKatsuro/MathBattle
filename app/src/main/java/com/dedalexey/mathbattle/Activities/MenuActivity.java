package com.dedalexey.mathbattle.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.dedalexey.mathbattle.Fragments.MenuFragment;
import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.MenuRow;
import com.dedalexey.mathbattle.model.Statistics;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MenuFragment.newInstance(getMenuRowList(),R.layout.menu_list_item);
    }


    public List<MenuRow> getMenuRowList(){
        List<MenuRow> menuRowList = new ArrayList<>();
        MenuRow playMenuRow = new MenuRow(getString(R.string.play), R.drawable.ic_action_play);
        playMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
        MenuRow settingsMenuRow = new MenuRow(getString(R.string.settings), R.drawable.ic_action_settings);
        settingsMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this,R.string.settings,Toast.LENGTH_SHORT).show();
            }
        });

        MenuRow statisticsMenuRow = new MenuRow(getString(R.string.statistics), R.drawable.ic_action_statistics);
        statisticsMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        MenuRow exitMenuRow = new MenuRow(getString(R.string.exit), R.drawable.ic_action_exit);
        exitMenuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.this.finishAffinity();
            }
        });

        menuRowList.add(playMenuRow);
        menuRowList.add(statisticsMenuRow);
        //menuRowList.add(settingsMenuRow);
        menuRowList.add(exitMenuRow);
        return menuRowList;
    }
}
