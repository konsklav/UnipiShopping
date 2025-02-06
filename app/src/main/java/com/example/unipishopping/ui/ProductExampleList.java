package com.example.unipishopping.ui;

import com.example.unipishopping.R;
import com.example.unipishopping.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductExampleList {
    public static List<Product> getExampleProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1, R.string.bag1, "Purple Bag-Pack with small pocket at the front.", System.currentTimeMillis(), 19.99, 37.960289766212625, 23.753818262136246, R.drawable.bag1_icon));
        products.add(new Product(2, R.string.bag2, "Blue Bag-Pack with abstract flower print at the front.", System.currentTimeMillis(), 15.99, 37.423, -122.08405, R.drawable.bag2_icon));
        products.add(new Product(3, R.string.boots, "Under the knee, black Boots with red laces.", System.currentTimeMillis(), 29.99, 37.960289766212625, 23.753818262136246, R.drawable.boots_icon));
        products.add(new Product(4, R.string.sneakers, "Pink Sneakers with white laces.", System.currentTimeMillis(), 49.99, 37.423, -122.08405, R.drawable.shoes_icon));
        products.add(new Product(5, R.string.glasses, "Pink Sunglasses with orange lances.", System.currentTimeMillis(), 7.99, 37.960289766212625, 23.753818262136246, R.drawable.glasses_icon));
        products.add(new Product(6, R.string.socks, "Lilac and dark purple Socks with smiley faces.", System.currentTimeMillis(), 4.99, 37.423, -122.08405, R.drawable.socks_icon));
        products.add(new Product(7, R.string.shirt, "Double print, red floral and orange stripes, Shirt with short sleeves.", System.currentTimeMillis(), 15.99, 37.960289766212625, 23.753818262136246, R.drawable.shirt_icon));
        products.add(new Product(8, R.string.sweater, "Red and white, horizontal striped Sweater.", System.currentTimeMillis(), 17.99, 37.423, -122.08405, R.drawable.sweater_icon));
        products.add(new Product(9, R.string.vinyl_disk, "Vintage classic rock Vinyl Disk.", System.currentTimeMillis(), 12.99, 37.960289766212625, 23.753818262136246, R.drawable.vinyl_icon));
        products.add(new Product(10, R.string.snow_globe, "Snow-Globe with penguin, with red binnie and scarf", System.currentTimeMillis(), 6.99, 37.423, -122.08405, R.drawable.snow_ball_icon));

        return products;
    }
}
