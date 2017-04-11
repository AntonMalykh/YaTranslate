package com.anykh_dev.yatranslate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TranslateFragment extends Fragment implements View.OnClickListener{

    //Элементы ActionBar
    ActionBar actionBar;
    private TextView mTxtFrom, mTxtTo;
    private ImageButton mIBtnSwap;

    //Элементы Фрагмента
    private EditText mEditText;
    private Button mBtnClear, mBtnTranslate;
    private TextView tvTranslate;
    //TODO добавить лист и адаптер

    RetroYTClient retroYTClient;
    RetroYDClient retroYDClient;

    //TODO обдумать, как реализовать выбор языка


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View cont = inflater.inflate(R.layout.fragment_translate, null);
        mEditText = (EditText) cont.findViewById(R.id.ma_et_Text);
        tvTranslate = (TextView) cont.findViewById(R.id.ma_tv_Translate);

        //TODO добавить лист
        mBtnClear = (Button) cont.findViewById(R.id.ma_btn_Clear);
        mBtnTranslate = (Button) cont.findViewById(R.id.ma_btn_Translate);
        mBtnClear.setOnClickListener(this);
        mBtnTranslate.setOnClickListener(this);

        View v = getActivity().getLayoutInflater().inflate(R.layout.actionbar_translate, null);
        mTxtFrom = (TextView) v.findViewById(R.id.ab_tr_txtFrom);
        mTxtTo = (TextView) v.findViewById(R.id.ab_tr_txtTo);
        mIBtnSwap = (ImageButton) v.findViewById(R.id.ab_tr_btnSwap);

        mTxtFrom.setOnClickListener(this);
        mTxtTo.setOnClickListener(this);
        mIBtnSwap.setOnClickListener(this);

        //Устанавливаем свой View в ActionBar
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        try {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(v);
        }catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return cont;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroYTClient = new RetroYTClient();
        retroYDClient = new RetroYDClient();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Очищаем текст по кнопке Clear
            case (R.id.ma_btn_Clear):
                if (!TextUtils.isEmpty(mEditText.getText())) {
                    tvTranslate.setText("");
                    mEditText.setText("");

                    //TODO добавить очистку листа
                }
                break;

            //Получаем перевод по кнопке Translate
            case (R.id.ma_btn_Translate):
                String text = mEditText.getText().toString().trim();

                if (!TextUtils.isEmpty(text)) {
                    tvTranslate.setText("");
                    getTranslation(text);
                }

                break;

            //Получаем язык, с которого переводить
            case (R.id.ab_tr_txtFrom):
                //TODO обрабоку выбора языка
                break;

            //Получаем язык, на который перевести
            case (R.id.ab_tr_txtTo):
                //TODO обрабоку выбора языка
                break;

            //Меняем местами выбор языков
            case (R.id.ab_tr_btnSwap):
                //TODO обработку смены языков
                 break;
        }
    }

    void getTranslation(String text){

        //Если введена фраза
        if (text.contains(" ")) {

            //TODO организовать вызов с использованием выбранных языков
            retroYTClient.getTranslation(text, "ru", new Callback<Translation>() {

                @Override
                public void onResponse(Call<Translation> call, Response<Translation> response) {

                    if (response.isSuccessful()) {
                        String translation = response.body().getText().get(0);
                        tvTranslate.setText(translation);
                    }
                }

                @Override
                public void onFailure(Call<Translation> call, Throwable t) {

                }
            });
        }

        //Если введено слово
    }

}
