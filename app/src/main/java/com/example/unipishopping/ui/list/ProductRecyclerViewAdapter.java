package com.example.unipishopping.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.os.LocaleListCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipishopping.R;
import com.example.unipishopping.core.settings.SettingsService;
import com.example.unipishopping.databinding.ProductItemBinding;
import com.example.unipishopping.domain.Product;
import com.example.unipishopping.ui.bindings.FontSizeBinder;
import com.example.unipishopping.ui.formats.NumFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductRecyclerViewHolder>{
    private final Locale locale;
    private final List<Product> products;
    private final Consumer<Product> onClick;

    public ProductRecyclerViewAdapter(Context context, Consumer<Product> onClickListener) {
        LocaleListCompat locales = SettingsService.getLocale(context);
        this.products = new ArrayList<>();
        onClick = onClickListener;

        if (locales.isEmpty()) {
            locale = Locale.getDefault();
        } else {
            locale = locales.get(0);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add(List<Product> product) {
        products.addAll(product);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductRecyclerViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewHolder holder, int position) {
        holder.setItem(products.get(position), onClick, locale);
    }

    @Override
    public int getItemCount() { return products.size(); }

    public static class ProductRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView name;
        private final TextView price;

        public ProductRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            ProductItemBinding binding = ProductItemBinding.bind(itemView);
            image = binding.ivProductImg;
            name = binding.tvProductName;
            price = binding.tvProductPrice;

            new FontSizeBinder().bind(itemView);
        }

        public void setItem(Product product, Consumer<Product> onClick, Locale locale) {
            image.setImageResource(product.getImageId());
            name.setText(product.getTitleId());
            price.setText(NumFormatter.formatAsPrice(product.getPrice(), locale));

            itemView.setOnClickListener(v -> onClick.accept(product));
        }
    }
}
