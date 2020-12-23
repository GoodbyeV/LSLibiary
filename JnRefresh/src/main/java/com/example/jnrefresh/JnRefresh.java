package com.example.jnrefresh;

/**
 * author  : Liushuai
 * time    : 2020/12/23 20:29
 * desc    :
 */
public interface JnRefresh {
    /**
     * 刷新时是否禁止滚动
     * @param disableRefreshScroll
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新的监听器
     * @param jnRefreshListener
     */
    void setRefreshListener(JnRefreshListener jnRefreshListener);

    void setRefreshOverView(JnOverView jnOverView);

    interface JnRefreshListener{
        void onRefresh();
        boolean enableRefresh();
    }
}
