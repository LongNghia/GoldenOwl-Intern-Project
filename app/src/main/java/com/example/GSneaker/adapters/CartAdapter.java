package com.example.GSneaker.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.GSneaker.models.Product;
import com.example.GSneaker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> mListCart;
    private List<Product> mListProduct;
    private ShopAdapter mShopAdapter;

    private CartInterface cartInterface;

    public interface CartInterface {
        void deleteItem(Product cartItem);

        void changeQuantity(Product cartItem, int quantity);

        void setPrice(TextView textView);
    }

    public CartAdapter(CartInterface cartInterface){
        this.cartInterface = cartInterface;
    }
    public void setData(List<Product> listCart) {
        this.mListCart = listCart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = mListCart.get(position);
        holder.itemName.setText(product.getName());
        holder.itemPrice.setText("$"+product.getPrice());
        holder.itemCount.setText(String.valueOf(product.getQuantity()));

        Picasso.get()
                .load(product.getImage())
                .rotate(-24)
                .fit()
                .into(holder.itemImage);
        Drawable background = holder.itemImage.getBackground();

        if (background instanceof ShapeDrawable) {
            // cast to 'ShapeDrawable'
            ShapeDrawable shapeDrawable = (ShapeDrawable) background;
            shapeDrawable.getPaint().setColor(Color.parseColor(product.getColor()));
        } else if (background instanceof GradientDrawable) {
            // cast to 'GradientDrawable'
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(Color.parseColor(product.getColor()));
        } else if (background instanceof ColorDrawable) {
            // alpha value may need to be set again after this call
            ColorDrawable colorDrawable = (ColorDrawable) background;
            colorDrawable.setColor(Color.parseColor(product.getColor()));
        }

        holder.itemIncrement.setOnClickListener(view -> {
            cartInterface.changeQuantity(product, product.getQuantity() + 1);
        });

        holder.itemDecrement.setOnClickListener(view -> {
            if (product.getQuantity() > 1)
                cartInterface.changeQuantity(product, product.getQuantity() - 1);
            else
                holder.itemRemove.performClick();
        });

        holder.itemRemove.setOnClickListener(view -> {
            cartInterface.deleteItem(product);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if (mListCart != null)
            return mListCart.size();
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemName;
        private TextView itemPrice;
        private TextView itemDecrement;
        private TextView itemIncrement;
        private TextView itemCount;
        ImageView itemRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.cart_item_image);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemPrice = itemView.findViewById(R.id.cart_item_price);
            itemDecrement = itemView.findViewById(R.id.cart_item_count_button_decrement);
            itemIncrement = itemView.findViewById(R.id.cart_item_count_button_increment);
            itemCount = itemView.findViewById(R.id.cart_item_count_number);
            itemRemove = itemView.findViewById(R.id.cart_item_remove);
        }
    }
}
