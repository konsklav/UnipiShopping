package com.example.unipishopping.ui.list;

import android.content.Context;
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.view.ViewManager;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Style and 💫 beautify the RecyclerView!
 */
public class ProductRecyclerViewHelper {
    public static void applyStyling(RecyclerView recyclerView, Context context) {
        recyclerView.addItemDecoration(new ProductItemDecoration(10));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Configuration config = context.getResources().getConfiguration();

        boolean isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE;

        recyclerView.setLayoutManager(
                new LinearLayoutManager(context, isLandscape
                        ? LinearLayoutManager.HORIZONTAL
                        : LinearLayoutManager.VERTICAL, false));


        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        recyclerView.setLayoutParams(params);
    }
}
