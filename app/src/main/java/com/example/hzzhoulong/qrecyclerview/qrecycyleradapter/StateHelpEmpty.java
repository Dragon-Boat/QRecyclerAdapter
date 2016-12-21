package com.example.hzzhoulong.qrecyclerview.qrecycyleradapter;

import android.view.View;

/**
 * Created by @author hzzhoulong
 * on 2016/12/21.
 */

public class StateHelpEmpty implements StateHelpInterface {
    @Override
    public View getLoadingView() {
        return null;
    }

    @Override
    public View getEmptyView() {
        return null;
    }

    @Override
    public View getLoadErrorView() {
        return null;
    }

    @Override
    public View getNetworkErrorView() {
        return null;
    }

    @Override
    public View getCommonErrorView() {
        return null;
    }

    @Override
    public View getExtraView(int stateCode) {
        return null;
    }
}
