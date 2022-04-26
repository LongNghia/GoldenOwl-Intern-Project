package com.example.GSneaker.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.AsyncTask;
import android.util.Log;
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

import java.io.InputStream;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ProductViewHolder> {
    String TAG = "ProductAdapter";
    private List<Product> mListProduct;
    private ShopInterface shopInterface;

    public interface ShopInterface {
        void addToCart(TextView textView, Product product);
    }

    public ShopAdapter(ShopInterface shopInterface) {
        this.shopInterface = shopInterface;
    }

    public void setData(List<Product> list) {
        this.mListProduct = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if (product == null) return;

//        new DownloadImageTask(holder.itemImage)
//                .execute(product.getImage());
        Picasso.get()
                .load(product.getImage())
                .rotate(-24)
                .into(holder.itemImage);

        // https://stackoverflow.com/questions/17823451/set-android-shape-color-programmatically
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

        holder.itemName.setText(product.getName());
        holder.itemDescription.setText(product.getDescription());
        holder.itemPrice.setText("$" + product.getPrice());

        if (product.getQuantity() > 0) {
            holder.itemButton.setBackgroundResource(R.drawable.round_corner_gray);
            holder.itemButton.setText("Added");
        } else {
            holder.itemButton.setText("ADD TO CARD");

            holder.itemButton.setBackgroundResource(R.drawable.round_corner);
        }
        holder.itemButton.setOnClickListener(view -> {
            if (product.getQuantity() == 0)
                shopInterface.addToCart(holder.itemButton, product);
        });
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView itemName;
        private TextView itemDescription;
        private TextView itemPrice;
        private TextView itemButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.shop_item_image);
            itemName = itemView.findViewById(R.id.shop_item_name);
            itemDescription = itemView.findViewById(R.id.shop_item_description);
            itemPrice = itemView.findViewById(R.id.shop_item_price);
            itemButton = itemView.findViewById(R.id.shop_item_button);
        }
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }

    public List<Product> getListProduct() {
        return mListProduct;
    }
}
