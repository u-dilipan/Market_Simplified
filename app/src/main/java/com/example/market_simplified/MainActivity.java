package com.example.market_simplified;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.market_simplified.fragments.FirstFragment;
import com.example.market_simplified.fragments.SecondFragment;
import com.example.market_simplified.fragments.ThirdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final Fragment fragment1 = new FirstFragment();
    final Fragment fragment2 = new SecondFragment();
    final Fragment fragment3 = new ThirdFragment();
    public static Fragment detailFragment;
    final FragmentManager fm = getSupportFragmentManager();

    public Fragment active = fragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Market Simplified");

        fm.beginTransaction().add(R.id.main_container, fragment3).hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2).hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment current;
            switch (item.getItemId()) {
                case R.id.ffirst:
                    current = detailFragment != null? detailFragment: fragment1;
                    fm.beginTransaction().hide(active).show(current).commit();
                    active = fragment1;
                    return true;

                case R.id.fsecond:
                    current = detailFragment != null && active == fragment1? detailFragment: active;
                    fm.beginTransaction().hide(current).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.fthird:
                    current = detailFragment != null && active == fragment1? detailFragment: active;
                    fm.beginTransaction().hide(current).show(fragment3).commit();
                    active = fragment3;
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if(active == fragment1 && detailFragment != null) {
            fm.beginTransaction().hide(detailFragment).show(fragment1).commit();
        }
        else {
            super.onBackPressed();
        }
        detailFragment = null;
    }
}