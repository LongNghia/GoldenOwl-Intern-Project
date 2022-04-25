package com.example.GSneaker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.GSneaker.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainAcitvity";
    AHBottomNavigationViewPager ahBottomNavigationViewPager;
    AHBottomNavigation ahBottomNavigation;
    ViewPagerAdapter viewPagerAdapter;

    private  int mCountProduct;

    public class TestClass{
        private  int num;

        public TestClass(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "TestClass{" +
                    "num=" + num +
                    '}';
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        List<TestClass> list = new ArrayList<>();
//        list.add(new TestClass(1));
//        list.add(new TestClass(2));
//        list.add(new TestClass(3));
//        Log.d(TAG, "onCreate: "+list);
//
//        TestClass testClass = new TestClass(2);
//        Log.d(TAG, "onCreate: remove " +  list.remove(list.get(1)));

        /////////

        ahBottomNavigation = findViewById(R.id.AHBottomNavigation);
        ahBottomNavigationViewPager = findViewById(R.id.AHBottomNavigationViewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ahBottomNavigationViewPager.setAdapter(viewPagerAdapter);
        ahBottomNavigationViewPager.setPagingEnabled(true);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_product, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_cart, R.color.color_tab_2);

        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);

        ahBottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            ahBottomNavigationViewPager.setCurrentItem(position);
            return true;
        });

        ahBottomNavigationViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ahBottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setCountProductInCart(int count){
        mCountProduct = count;
        AHNotification ahNotification = new AHNotification.Builder()
                .setText(String.valueOf(count))
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .build();
        ahBottomNavigation.setNotification(ahNotification,1);
    }

    public int getCountProduct() {
        return mCountProduct;
    }
}