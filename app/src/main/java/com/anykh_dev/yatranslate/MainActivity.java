package com.anykh_dev.yatranslate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    final String LOG = "myLog";

    private EditText mEditText;
    private Button mBtnClear;
    private TextView tvTranslate;
    ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.ma_et_Text);
        tvTranslate = (TextView) findViewById(R.id.ma_tv_Translate);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mBtnClear = (Button) findViewById(R.id.ma_btn_Clear);
        mBtnClear.setOnClickListener(this);



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;

                case R.id.navigation_dashboard:

                    return true;

                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }

    };

    public void onClikTranslate(View v) {
        String s = mEditText.getText().toString();
        Call<Translation> translationCall = RetroYTClient.getYTAPIservice()
                .getTranslate(RetroYTClient.getAuthKey(), "ru", s);

        translationCall.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {

                Translation trans = response.body();
                String str = trans.getText().get(0);
                tvTranslate.setText(str);
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.ma_btn_Clear):
                mEditText.setText("");
        }
    }
}
