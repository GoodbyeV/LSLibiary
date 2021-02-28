package com.example.lslibiary.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.lslibiary.R;
import com.example.lslibiary.databinding.ActivityMVVMBinding;

public class MVVMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMVVMBinding binding=DataBindingUtil.setContentView(this, R.layout.activity_m_v_v_m);
        MainViewModel mainViewModel = new MainViewModel();
        binding.setViewModel(mainViewModel);
        mainViewModel.getUserInfo();

        binding.etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("---", "onTextChanged"+mainViewModel.userFiled.get().getAdress());
            }
        });
    }
}