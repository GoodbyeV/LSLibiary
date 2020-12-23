package com.example.jnrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author  : Liushuai
 * time    : 2020/12/23 20:33
 * desc    : 下拉刷新的overlay视图 可扩展头部
 */
public abstract class JnOverView extends FrameLayout {
    public enum JnRefreshState{
        /**
         * 初始状态
         */
        STATE_INIT,
        /**
         * Header展示状态
         */
        STATE_VISIBLE,

        /**
         * 超出可刷新距离
         */
        STATE_REFRESH,

        /**
         * 超出刷新位置松开手后
         */
        STATE_OVER_RELEASE
    }

    protected JnRefreshState mState=JnRefreshState.STATE_INIT;
    /**
     * 触发下拉刷新需要的最小高度
     */
    public int mPullRefreshHeight;

    /**
     * 最小阻尼 下拉时
     * @param context
     */
    public float minDamp=1.6f;

    /**
     * 最大阻尼
     * @param context
     */
    public float maxDamp=2.2f;


    public JnOverView(@NonNull Context context) {
        super(context);
    }

    public JnOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JnOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public abstract void init();

    protected abstract void onScroll(int scrollY, int mPullRefreshHeight);

    /**
     * 显示Overlay
     */
    protected abstract void onVisible();

    /**
     * 超过Overlay 释放加载
     */
    public abstract void onOver();

    /**
     * 开始刷新
     */
    public abstract void onRefresh();

    /**
     * 刷新完成
     */
    public abstract void onFinish();


    public JnRefreshState getmState() {
        return mState;
    }

    public void setmState(JnRefreshState mState) {
        this.mState = mState;
    }
}
