package com.dedalexey.mathbattle.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.dedalexey.mathbattle.Fragments.HomeFragment;
import com.dedalexey.mathbattle.Fragments.MenuFragment;
import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.MenuRow;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return HomeFragment.newInstance();
    }
}
