package com.example.homerental.landlords.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull HomeLandlordFragment fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new YourPropertyFragment();
            case 1:
                return new AddPropertyFragment();
            default:
                return new YourPropertyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
