package com.example.lslibiary.mvvm_jetpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.lslibiary.R;
import com.example.lslibiary.databinding.ActivityMVVM2Binding;
import com.example.lslibiary.livedata.User;

public class MVVM2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMVVM2Binding activityMVVMBinding=DataBindingUtil.setContentView(this,R.layout.activity_m_v_v_m2);

        ViewModelProvider viewModelProvider=new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory());
        MainViewModel viewModel=viewModelProvider.get(MainViewModel.class);
        viewModel.getUserInfo().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userInfo) {
                activityMVVMBinding.setUser(userInfo);
            }
        });
    }
}