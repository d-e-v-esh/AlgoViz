package com.dev.algoviz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class IntroFragmentAdapter extends FragmentStateAdapter {

    public IntroFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {
            return new VisualizerMenuFragment();
        }
        return new IntroFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
