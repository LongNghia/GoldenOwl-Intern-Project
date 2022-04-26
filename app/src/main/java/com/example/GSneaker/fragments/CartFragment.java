package com.example.GSneaker.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.GSneaker.adapters.CartAdapter;
import com.example.GSneaker.MainActivity;
import com.example.GSneaker.models.Product;
import com.example.GSneaker.R;
import com.example.GSneaker.viewmodels.ShopViewModel;

import java.util.List;


public class CartFragment extends Fragment {
    private String TAG = "CartFragment";
    private RecyclerView recyclerView;
    private TextView tvCartAmount;
    private TextView tvEmptyCart;
    private CartAdapter cartAdapter;
    private MainActivity mainActivity;
    private SharedPreferences sharedPreferences;
    private ShopViewModel shopViewModel;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.rcv_cart);
        tvCartAmount = view.findViewById(R.id.card_title_amount);
        tvEmptyCart = view.findViewById(R.id.tv_empty_cart);

        mainActivity = (MainActivity) getActivity();

        sharedPreferences = mainActivity.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        // restore previous cart data.
        String cartListString = sharedPreferences.getString("cart_list","");
        if (!cartListString.isEmpty()){
            shopViewModel.setCart(Product.listFromString(cartListString));
            mainActivity.setCountProductInCart(shopViewModel.getCartCount());
        }

        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> cartItems) {
                cartAdapter.setData(cartItems);

                editor.putString("cart_list", cartItems.toString());
                editor.apply();

                if (cartItems.size() == 0) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    tvCartAmount.setVisibility(View.INVISIBLE);
                    tvEmptyCart.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvCartAmount.setVisibility(View.VISIBLE);
                    tvEmptyCart.setVisibility(View.INVISIBLE);
                }
//                cartAdapter.(cartItems);
//                fragmentCartBinding.placeOrderButton.setEnabled(cartItems.size() > 0);
            }
        });

        shopViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                tvCartAmount.setText("$" + String.format(java.util.Locale.US, "%.2f", aDouble));
//                tvCartAmount.setText("$"+aDouble.toString());
            }
        });

        CartAdapter.CartInterface cartInterface = new CartAdapter.CartInterface() {
            @Override
            public void deleteItem(Product cartItem) {
                shopViewModel.removeItemFromCart(cartItem);
                mainActivity.setCountProductInCart(mainActivity.getCountProduct() - 1);
            }

            @Override
            public void changeQuantity(Product cartItem, int quantity) {
                shopViewModel.changeQuantity(cartItem, quantity);
            }

            @Override
            public void setPrice(TextView textView) {
                textView.setText((CharSequence) shopViewModel.getTotalPrice());
            }
        };


        cartAdapter = new CartAdapter(cartInterface);

        cartAdapter.setData(shopViewModel.getCart().getValue());

        recyclerView.setAdapter(cartAdapter);


        return view;

    }

    public void toggleCartEmpty() {
        if (shopViewModel.getCartCount() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            tvCartAmount.setVisibility(View.INVISIBLE);
            tvEmptyCart.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvCartAmount.setVisibility(View.VISIBLE);
            tvEmptyCart.setVisibility(View.INVISIBLE);
        }
    }
}