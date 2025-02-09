package com.example.unipishopping.ui.bindings;

import androidx.viewbinding.ViewBinding;

import com.example.unipishopping.ui.AppActivityBase;

public interface ActivityBinder {
    <T extends ViewBinding> void bind(AppActivityBase<T> activity);
}
