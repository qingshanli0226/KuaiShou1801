package com.lqs.kuaishou.kuaishou1801.login.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class LoginRegisterAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments = new Fragment[] {new LoginFragment(),new RegisterFragment()};


    public LoginRegisterAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
