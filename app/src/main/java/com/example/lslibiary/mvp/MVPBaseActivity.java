package com.example.lslibiary.mvp;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author  : Liushuai
 * time    : 2021/2/27 21:14
 * desc    :MVPBase
 */
class MVPBaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected  P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取activity的类型
        Type superclass=this.getClass().getSuperclass();
        //具备泛型参数的类型
        if (superclass instanceof ParameterizedType) {
            Type[] arguments=((ParameterizedType)superclass).getActualTypeArguments();
            if (arguments != null && arguments[0] instanceof BasePresenter) {
                try {
                    mPresenter= (P) arguments[0].getClass().newInstance();
                    mPresenter.attach(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean isAlive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !isDestroyed()&&!isFinishing();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
