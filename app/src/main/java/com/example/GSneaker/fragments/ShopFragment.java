package com.example.GSneaker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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


public class ShopFragment extends Fragment {

    private View mView;
    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;
    private MainActivity mainActivity;

    private String TAG = "ProductFragment";

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);


        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> cartItems) {
                Log.d(TAG, "onChanged: cart change"+cartItems);
                shopAdapter.setData(cartItems);
            }
        });


        ShopAdapter.ShopInterface shopInterface = new ShopAdapter.ShopInterface() {
            @Override
            public void addToCart(TextView textView, Product product) {
                product.setQuantity(1);
                product.setAddToCart(true);
                shopViewModel.addItemToCart(product);
                mainActivity.setCountProductInCart(mainActivity.getCountProduct()+1);
                shopAdapter.notifyDataSetChanged();
            }
        };

        shopAdapter = new ShopAdapter(shopInterface);
        shopAdapter.setData(shopViewModel.getProducts().getValue());
        recyclerView.setAdapter(shopAdapter);

        return mView;
    }
}