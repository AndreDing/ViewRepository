package com.ialways.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ialways.android.R;
import com.ialways.android.ui.fragment.ProgressTvFragment;

public class MainActivity extends FragmentActivity {

    private ProgressTvFragment mProgreesFragment;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        addProgressFragment();
    }

    private void initFragment() {
        mProgreesFragment = ProgressTvFragment.getInstance(this, bundle);
    }

    private void addProgressFragment() {

        FragmentManager fgMgr = this.getSupportFragmentManager();
        FragmentTransaction fgTran = fgMgr.beginTransaction();
        fgTran.add(R.id.fragment_container, mProgreesFragment);
        fgTran.commit();
    }
}
