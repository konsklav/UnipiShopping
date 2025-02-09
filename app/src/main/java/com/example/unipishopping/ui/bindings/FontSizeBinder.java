package com.example.unipishopping.ui.bindings;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.unipishopping.R;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.ui.AppActivityBase;
import com.example.unipishopping.ui.constants.TextSize;

public class FontSizeBinder implements Binder{
    private AppCompatActivity activity;

    @Override
    public <T extends ViewBinding> void bind(AppActivityBase<T> activity) {
        this.activity = activity;

        TextSize textSize = SettingsService.get(activity).getTextSize();
        int[] idList = new int[] {
                R.id.tvProductName,
                R.id.tvProductPrice,
                R.id.tvBuyProduct1,
                R.id.tvBuyProduct2,
                R.id.tvUsername,
                R.id.tvPassword
        };

        setFontSize(idList, textSize);
    }

    private void setFontSize(int[] viewIds, TextSize textSize) {
        float multiplier = 1;
        switch (textSize) {
            case SMALL:
                multiplier = 0.7f;
                break;
            case BIG:
                multiplier = 1.25f;
                break;
        }

        for (int id : viewIds) {
            TextView textView = activity.findViewById(id);

            if (textView != null) {
                float size = textView.getTextSize();
                textView.setTextSize(size * multiplier);
            }
        }

    }
}
