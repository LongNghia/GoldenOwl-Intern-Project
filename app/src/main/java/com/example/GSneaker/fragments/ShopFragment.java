package com.example.GSneaker.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.GSneaker.MainActivity;
import com.example.GSneaker.models.Product;
import com.example.GSneaker.adapters.ShopAdapter;
import com.example.GSneaker.R;
import com.example.GSneaker.viewmodels.ShopViewModel;

import java.util.List;
import java.util.Set;


public class ShopFragment extends Fragment {

    private View mView;
    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;
    private MainActivity mainActivity;
    private String TAG = "ProductFragment";
    private SharedPreferences sharedPreferences;
    private ShopViewModel shopViewModel;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = mView.findViewById(R.id.rcv_product);
        mainActivity = (MainActivity) getActivity();

        sharedPreferences = mainActivity.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);


        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        // restore previous shop data.
        String shopListString = sharedPreferences.getString("shop_list", "");
        if (!shopListString.isEmpty()) {
            shopViewModel.setProducts(Product.listFromString(shopListString));
        }

        shopViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> shopItems) {
                shopAdapter.setData(shopItems);

                editor.putString("shop_list", shopItems.toString());
                editor.apply();
            }
        });


        ShopAdapter.ShopInterface shopInterface = new ShopAdapter.ShopInterface() {
            @Override
            public void addToCart(TextView textView, Product product) {
                product.setQuantity(1);
                shopViewModel.addItemToCart(product);
                mainActivity.setCountProductInCart(mainActivity.getCountProduct() + 1);
                shopAdapter.notifyDataSetChanged();
            }
        };

        shopAdapter = new ShopAdapter(shopInterface);

        List<Product> products = shopViewModel.getProducts().getValue();
        shopAdapter.setData(products);

        recyclerView.setAdapter(shopAdapter);
        return mView;
    }

}