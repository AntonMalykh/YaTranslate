package com.anykh_dev.yatranslate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft;
    TranslateFragment tf;
    HistoryFragment hf;
    FavoritesFragment ff;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_translate:
                    ft.replace(R.id.ma_fragment, tf);
                    ft.commit();
                    return true;

                case R.id.navigation_history:
                    ft.replace(R.id.ma_fragment, hf);
                    ft.commit();;
                    return true;

                case R.id.navigation_favorites:
                    ft.replace(R.id.ma_fragment, ff);
                    ft.commit();
                    return true;
            }
            return false;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        tf = new TranslateFragment();
        hf = new HistoryFragment();
        ff = new FavoritesFragment();

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ma_fragment, tf);
        ft.commit();
    }

}
