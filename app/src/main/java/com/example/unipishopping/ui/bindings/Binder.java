package com.example.unipishopping.ui.bindings;

import androidx.viewbinding.ViewBinding;

import com.example.unipishopping.ui.AppActivityBase;

public interface Binder {
    <T extends ViewBinding> void bind(AppActivityBase<T> activity);
}
