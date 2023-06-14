package com.test.moviegallery.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.test.moviegallery.ui.LoadingDialog;

public abstract class BaseFragment<ViewModelType extends BaseViewModel> extends Fragment {
    private LoadingDialog loadingDialog;

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract ViewModelType getViewModel();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelType viewModel = getViewModel();

        viewModel.getShowLoading().observe(getViewLifecycleOwner(), __ -> {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(requireContext());
            }

            loadingDialog.show();
        });

        viewModel.getHideLoading().observe(getViewLifecycleOwner(), __ -> {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        });

        viewModel.getShowError().observe(getViewLifecycleOwner(), error -> {
            new AlertDialog.Builder(requireContext())
                    .setMessage(error)
                    .setPositiveButton("Ok", (dialog, __) -> dialog.dismiss())
                    .create()
                    .show();
        });

        viewModel.getShowToast().observe(getViewLifecycleOwner(), this::toast);
    }

    protected void toast(String message) {
        // Implement your toast method here
    }
}