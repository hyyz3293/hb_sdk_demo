package com.huabao.huabaosdkdemo;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * author :chenhua
 * create at :2022/7/16 22:09
 * describe:
 */
public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().isEmpty()){
            onTextChanged("0");
        }else {
            onTextChanged(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    abstract void onTextChanged(String data);
}
