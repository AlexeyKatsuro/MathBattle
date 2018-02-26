package com.dedalexey.mathbattle.Activities;

import android.support.v4.app.Fragment;

import com.dedalexey.mathbattle.Fragments.MenuFragment;

public class MenuActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MenuFragment.newInstance();
    }
}
