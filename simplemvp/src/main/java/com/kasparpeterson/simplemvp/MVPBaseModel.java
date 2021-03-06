package com.kasparpeterson.simplemvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by kaspar on 01/09/16.
 */
public abstract class MVPBaseModel<P extends MVPBasePresenterModelOperations> {

    private P presenter;

    public MVPBaseModel() {

    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public void onStart() {
        // For overriding
    }

    protected void onResume() {
        // For overriding
    }

    public void onDestroy() {
        // For overriding
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // For overriding
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // For overriding
    }

    @NonNull public P getPresenter() {
        return presenter;
    }
}
