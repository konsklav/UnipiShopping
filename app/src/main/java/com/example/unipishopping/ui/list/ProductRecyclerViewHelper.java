package com.example.unipishopping.ui.list;

import android.content.Context;
import android.content.res.Configuration;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductRecyclerViewHelper {
    public static void applyStyling(RecyclerView recyclerView, Context context) {
        recyclerView.addItemDecoration(new ProductItemDecoration(10));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Configuration config = context.getResources().getConfiguration();

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        context, config.orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? LinearLayoutManager.HORIZONTAL
                        : LinearLayoutManager.VERTICAL, false));

    }
}
