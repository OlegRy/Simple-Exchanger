package ru.olegry.simpleexchanger.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onDestroy() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            destroyPresenter();
        }
        super.onDestroy();
    }

    protected void destroyPresenter() {}

    @LayoutRes
    protected abstract int layoutId();
}
