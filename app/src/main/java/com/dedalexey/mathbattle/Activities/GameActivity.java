package com.dedalexey.mathbattle.Activities;

import android.support.v4.app.Fragment;

import com.dedalexey.mathbattle.Fragments.GameManagerFragment;

/**
 * Created by Alexey on 20.01.2018.
 */

public class GameActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return GameManagerFragment.newInstance();
    }
}
