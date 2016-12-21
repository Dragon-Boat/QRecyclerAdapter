package com.example.hzzhoulong.qrecyclerview.qrecycyleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hzzhoulong.androidlearn.R;

/**
 * {@link StateHelpInterface}接口的一个实现
 * 可以根据业务需要，自由实现状态的展示效果
 * Created by @author hzzhoulong
 * on 2016/12/20.
 */

public class StateHelpDefault implements StateHelpInterface {
    Context mContext;
    View mStateView;

    TextView mStateTint;
    ImageView mStateImg;

    public StateHelpDefault(Context mContext) {
        this.mContext = mContext;
        mStateView = LayoutInflater.from(mContext).inflate(R.layout.state_layout, null, false);
        mStateTint = (TextView) mStateView.findViewById(R.id.state_tint);
        mStateImg = (ImageView) mStateView.findViewById(R.id.state_img);
    }

    @Override
    public View getLoadingView() {
        mStateTint.setText("别看了！加载中");
        return mStateView;
    }

    @Override
    public View getEmptyView() {
        mStateTint.setText("别看了！被掏空了");
        return mStateView;
    }

    @Override
    public View getLoadErrorView() {
        mStateTint.setText("别看了！加载错误");
        return mStateView;
    }

    @Override
    public View getNetworkErrorView() {
        mStateTint.setText("别看了！你网络不好");
        return mStateView;
    }

    @Override
    public View getCommonErrorView() {
        mStateTint.setText("别看了！我也不知道什么错误，反正出错了");
        return mStateView;
    }

    @Override
    public View getExtraView(int stateCode) {
        mStateTint.setText("根据状态码改变");
        return mStateView;
    }
}
