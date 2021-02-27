package com.example.lslibiary.mvp;

/**
 * author  : Liushuai
 * time    : 2021/2/27 21:01
 * desc    :
 */
class BasePresenter<IView extends BaseView> {
    public IView view;
    public void attach(IView view){
        this.view=view;
    }

    public void detach() {
        view=null;
    }

}
