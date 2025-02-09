package com.example.unipishopping.ui.bindings;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.core.util.TypedValueCompat;

import com.example.unipishopping.R;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.ui.constants.TextSize;

public class FontSizeBinder implements ViewBinder {

    @Override
    public void bind(View view) {
        TextSize textSize = SettingsService.get(view.getContext()).getTextSize();
        int[] idList = new int[] {
                R.id.tvProductName,
                R.id.tvProductPrice,
                R.id.tvCode,
                R.id.tvProductInfo,
                R.id.tvBuyProduct1,
                R.id.tvBuyProduct2,
                R.id.tvUsername,
                R.id.tvPassword
        };

        setFontSize(idList, textSize, view);
    }

    private void setFontSize(int[] viewIds, TextSize textSize, View view) {
        float multiplier = 1;
        switch (textSize) {
            case SMALL:
                multiplier = 0.7f;
                break;
            case BIG:
                multiplier = 1.25f;
                break;
        }

        DisplayMetrics metrics = view.getResources().getDisplayMetrics();

        for (int id : viewIds) {
            TextView textView = view.findViewById(id);

            if (textView != null) {
                float size = textView.getTextSize();
                float spSize = TypedValueCompat.pxToSp(size * multiplier, metrics);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
            }
        }
    }
}
