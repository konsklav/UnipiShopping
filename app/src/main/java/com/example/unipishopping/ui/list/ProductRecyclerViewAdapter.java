package com.example.unipishopping.ui.list;

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
import com.example.unipishopping.ui.formats.NumFormatter;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductRecyclerViewHolder>{
    private final Locale locale;
    private final List<Product> products;
    private final Consumer<Product> onClick;

    public ProductRecyclerViewAdapter(
            Context context,
            List<Product> products,
            Consumer<Product> onClickListener) {
        LocaleListCompat locales = SettingsService.getLocale(context);
        this.products = products;
        onClick = onClickListener;

        if (locales.isEmpty()) {
            locale = Locale.getDefault();
        } else {
            locale = locales.get(0);
        }
    }

    public void add(Product product, int position) {
        products.add(position, product);
        notifyItemInserted(position);
    }

    public void remove(Product product) {
        int i;
        for (i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                break;
            }
        }

        if (i < products.size()) {
            products.remove(i);
            notifyItemRemoved(i);
        }
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
        }

        public void setItem(Product product, Consumer<Product> onClick, Locale locale) {
            image.setImageResource(product.getImageId());
            name.setText(product.getTitleId());
            price.setText(NumFormatter.formatAsPrice(product.getPrice(), locale));

            itemView.setOnClickListener(v -> onClick.accept(product));
        }
    }
}
