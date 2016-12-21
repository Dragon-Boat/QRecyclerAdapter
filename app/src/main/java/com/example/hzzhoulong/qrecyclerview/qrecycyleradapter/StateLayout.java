package com.example.hzzhoulong.qrecyclerview.qrecycyleradapter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 状态页，可以嵌入任何位置
 * 状态的呈现根据注入的{@link StateHelpInterface}决定
 * 需要先通过 @see StateLayout#setStateHelper(StateHelpInterface) 方法来注入
 * Created by @author hzzhoulong
 * on 2016/12/20.
 */

public class StateLayout extends FrameLayout {
    private StateHelpInterface mStateHelper;

    public StateLayout(Context context) {
        super(context);
        init();
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public void setStateHelper(StateHelpInterface stateHelper) {
        this.mStateHelper = stateHelper;
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
        setOnClickListener(null);
    }

    private void replaceView(View view) {
        if (view != null) {
            removeAllViews();
            addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public void onLoading() {
        if (mStateHelper != null) {
            replaceView(mStateHelper.getLoadingView());
        }
    }

    public void onNetworkError() {
        if (mStateHelper != null) {
            replaceView(mStateHelper.getNetworkErrorView());
        }
    }

    public void onEmpty() {
        if (mStateHelper != null) {
            replaceView(mStateHelper.getEmptyView());
        }
    }

    public void onLoadError() {
        if (mStateHelper != null) {
            replaceView(mStateHelper.getLoadErrorView());
        }
    }

    public void onCommonError() {
        if (mStateHelper != null) {
            replaceView(mStateHelper.getCommonErrorView());
        }
    }

    public void onExtraState(int stateCode) {
        if (mStateHelper != null) {
            replaceView(mStateHelper.getExtraView(stateCode));
        }
    }
}
