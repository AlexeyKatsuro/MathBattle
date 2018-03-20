package com.dedalexey.mathbattle.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dedalexey.mathbattle.Fragments.StatisticsFragment;
import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.Statistics;

public class StatisticsActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return StatisticsFragment.newInstance();
    }
}
