package com.example.GSneaker.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.GSneaker.fragments.CartFragment;
import com.example.GSneaker.fragments.ShopFragment;

public class ViewPagerAdapter  extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ShopFragment();
            case 1:
                return new CartFragment();
            default:
                return new ShopFragment();

        }
    }

    @Override
    public int getCount() {
        return 2 ;
    }
}
