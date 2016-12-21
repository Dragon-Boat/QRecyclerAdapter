package com.example.hzzhoulong.qrecyclerview.qrecycyleradapter;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * 状态接口定义
 * Created by @author hzzhoulong
 * on 2016/12/20.
 */

public interface StateHelpInterface {
    /**
     * 加载中
     *
     * @return 对应的View
     */
    @Nullable
    View getLoadingView();

    /**
     * 空状态
     *
     * @return 对应的View
     */
    @Nullable
    View getEmptyView();

    /**
     * 加载失败
     *
     * @return 对应的View
     */
    @Nullable
    View getLoadErrorView();

    /**
     * 网络错误
     *
     * @return 对应的View
     */
    @Nullable
    View getNetworkErrorView();

    /**
     * 一般错误
     *
     * @return 对应的View
     */
    @Nullable
    View getCommonErrorView();

    /**
     * 特殊需要根据状态码确定的状态
     *
     * @return 对应的View
     */
    @Nullable
    View getExtraView(int stateCode);
}
