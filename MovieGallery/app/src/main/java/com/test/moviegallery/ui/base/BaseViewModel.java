package com.test.moviegallery.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kotlin.Unit;

public class BaseViewModel extends ViewModel {
    private MutableLiveData<Unit> _showLoading = new MutableLiveData<>();
    private MutableLiveData<Unit> _hideLoading = new MutableLiveData<>();
    private MutableLiveData<String> _showError = new MutableLiveData<>();
    private MutableLiveData<String> _showToast = new MutableLiveData<>();

    public MutableLiveData<Unit> getShowLoading() {
        return _showLoading;
    }

    public MutableLiveData<Unit> getHideLoading() {
        return _hideLoading;
    }

    public MutableLiveData<String> getShowError() {
        return _showError;
    }

    public MutableLiveData<String> getShowToast() {
        return _showToast;
    }

    public void showLoading() {
        _showLoading.postValue(Unit.INSTANCE);
    }

    public void hideLoading() {
        _hideLoading.postValue(Unit.INSTANCE);
    }

    public void showError(Throwable error) {
        _showError.postValue(error.getLocalizedMessage() != null ? error.getLocalizedMessage() : "Something went wrong.");
    }

    public void showError(String error) {
        _showError.postValue(error);
    }

    public void showToast(String message) {
        _showToast.postValue(message);
    }

    public void showToast(Throwable error) {
        _showToast.postValue(error.getLocalizedMessage() != null ? error.getLocalizedMessage() : "Something went wrong.");
    }
}
