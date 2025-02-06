package com.example.unipishopping.ui.list;

import android.content.Context;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductRecyclerViewHelper {
    public static void applyStyling(RecyclerView recyclerView, Context context) {
        recyclerView.addItemDecoration(new ProductItemDecoration(10));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
    }
}
