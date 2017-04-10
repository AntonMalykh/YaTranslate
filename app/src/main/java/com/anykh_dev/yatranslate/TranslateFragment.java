package com.anykh_dev.yatranslate;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class TranslateFragment extends Fragment implements View.OnClickListener{

    private EditText mEditText;
    private Button mBtnClear, mBtnTranslate;
    private TextView tvTranslate;
    ActionBar actionBar;
    Spinner spFrom, spTo;
    String spItems[] = {"One", "Two", "Three"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_translate, null);
        mEditText = (EditText) v.findViewById(R.id.ma_et_Text);
        tvTranslate = (TextView) v.findViewById(R.id.ma_tv_Translate);

        mBtnClear = (Button) v.findViewById(R.id.ma_btn_Clear);
        mBtnTranslate = (Button) v.findViewById(R.id.ma_btn_Translate);
        mBtnClear.setOnClickListener(this);
        mBtnTranslate.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getActivity().getLayoutInflater().inflate(R.layout.actionbar_translate, null);

        spFrom = (Spinner) v.findViewById(R.id.ab_translate_spinnerFrom);
        spTo = (Spinner) v.findViewById(R.id.ab_translate_spinnerTo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, spItems);
        spFrom.setAdapter(adapter);
        spFrom.setSelection(0);
        spTo.setAdapter(adapter);
        spTo.setSelection(1);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        try {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(v);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.ma_btn_Clear):
                mEditText.setText("");
                break;

            case (R.id.ma_btn_Translate):
                String s = mEditText.getText().toString();
                Call<Translation> translationCall = RetroYTClient.getYTApiService()
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
                break;
        }
    }

    private class LangsSpinnerAdapter implements SpinnerAdapter {
        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
