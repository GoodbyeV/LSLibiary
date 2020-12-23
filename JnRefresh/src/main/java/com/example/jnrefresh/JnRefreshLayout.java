package com.example.jnrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author  : Liushuai
 * time    : 2020/12/23 20:45
 * desc    :刷新容器
 */
public class JnRefreshLayout extends FrameLayout implements JnRefresh {
    private JnOverView.JnRefreshState mState;
    //手势监听
    private GestureDetector mGestureDetector;
    private JnRefresh.JnRefreshListener mJnRefreshListener;
    protected JnOverView jnOverView;
    //下拉最后Y轴坐标
    private int mLastY;
    //刷新时是否禁止滚动
    private boolean disableRefreshScroll;
    private AutoScroller autoScroller;

    public JnRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public JnRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JnRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mGestureDetector=new GestureDetector(getContext(),jnGestureDetector);
        autoScroller=new AutoScroller();
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll=disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {

    }

    @Override
    public void setRefreshListener(JnRefreshListener jnRefreshListener) {
            this.mJnRefreshListener=jnRefreshListener;
    }

    @Override
    public void setRefreshOverView(JnOverView jnOverView) {
          this.jnOverView=jnOverView;
    }

    JnGestureDetector jnGestureDetector=new JnGestureDetector(){
        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float disX, float disY) {
            if(Math.abs(disX)> Math.abs(disY)||mJnRefreshListener!=null&&!mJnRefreshListener.enableRefresh()){
                //横向滑动 或者禁止下拉刷新  不处理
                return false;
            }
            if (disableRefreshScroll && mState == JnOverView.JnRefreshState.STATE_REFRESH) {
                //刷新时是否滚动
                return  true;
            }
            View head = getChildAt(0);
            View child = JnScrollUtil.findScrollableChild(JnRefreshLayout.this);
            if (JnScrollUtil.childScrolled(child)) {
                //如果列表发生滚动则不处理
                return false;
            }
            //没有刷新或没到达刷新距离 且头部滑下来了
            if((mState!= JnOverView.JnRefreshState.STATE_REFRESH||head.getBottom()<jnOverView.mPullRefreshHeight)&&(head.getBottom()>0||disY<=0.0f)){
               //还在滑动中
                if (mState != JnOverView.JnRefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    if (child.getTop() < jnOverView.mPullRefreshHeight) {
                        seed = (int) (mLastY / jnOverView.minDamp);
                    }else{
                        seed = (int) (mLastY / jnOverView.maxDamp);
                    }
                }
            }
            return super.onScroll(motionEvent, motionEvent1, disX, disY);
        }
    };


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View head = getChildAt(0);
        if(ev.getAction()==MotionEvent.ACTION_UP||
                ev.getAction()==MotionEvent.ACTION_CANCEL||
                ev.getAction()==MotionEvent.ACTION_POINTER_INDEX_MASK){
             //松开手
            if (head.getBottom() > 0) {//头部被拉下来了
                if (mState != JnOverView.JnRefreshState.STATE_REFRESH) {
                    //不是刷新
                    recover(head.getBottom());
                    return false;
                }
            }
            mLastY=0;
        }
        //consumed true被消费
        boolean consumed=mGestureDetector.onTouchEvent(ev);
        if ((consumed || (mState != JnOverView.JnRefreshState.STATE_INIT && mState != JnOverView.JnRefreshState.STATE_REFRESH))&&head.getBottom()>0) {
            ev.setAction(MotionEvent.ACTION_CANCEL);
            return super.dispatchTouchEvent(ev);
        }

        if (consumed) {
            return true;
        }else{
            return super.dispatchTouchEvent(ev);
        }
    }

    private void recover(int dis) {
        if(mJnRefreshListener!=null&&dis>jnOverView.mPullRefreshHeight){
            //滚动到指定位置  dis-jnOverView.mPullRefreshHeight
            autoScroller.recover(dis-jnOverView.mPullRefreshHeight);
            mState= JnOverView.JnRefreshState.STATE_OVER_RELEASE;
        }else{
            autoScroller.recover(dis);
        }
    }

    //滚动
    private class AutoScroller implements Runnable{
        private Scroller mScroller;
        private int mLastY;
        private boolean isFinished;

        public AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            isFinished=true;
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                //为滚动完成
                mLastY=mScroller.getCurrY();
                post(this);
            }else{
                removeCallbacks(this);
                isFinished=true;
            }
        }

        void recover(int dis) {
            if (dis < 0) {
                return;
            }
            //取消之前的滚动任务
            removeCallbacks(this);
            mLastY=0;
            isFinished=false;
            mScroller.startScroll(0,0,0,dis,300);
            post(this);
        }

        boolean isFinished() {
            return isFinished;
        }
    }
}
