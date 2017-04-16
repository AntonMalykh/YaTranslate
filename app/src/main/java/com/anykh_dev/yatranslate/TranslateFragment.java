package com.anykh_dev.yatranslate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TranslateFragment extends Fragment implements View.OnClickListener {

    //Элементы ActionBar
    ActionBar actionBar;
    private TextView mTxtFrom, mTxtTo;
    private ImageButton mIBtnSwap;

    //Элементы Фрагмента
    private EditText mEditText;
    private Button mBtnClear, mBtnTranslate;
    private TextView mTvTranslate;
    private ListView mLvVariants;

    ArrayAdapter<String> adapter;

    RetroYTClient retroYTClient;
    RetroYDClient retroYDClient;

    TranslateDB translateDB;

    static Handler h;

    //TODO обдумать, как реализовать выбор языка


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View cont = inflater.inflate(R.layout.fragment_translate, null);
        mEditText = (EditText) cont.findViewById(R.id.ma_et_Text);
        mTvTranslate = (TextView) cont.findViewById(R.id.ma_tv_Translate);

        mBtnClear = (Button) cont.findViewById(R.id.ma_btn_Clear);
        mBtnTranslate = (Button) cont.findViewById(R.id.ma_btn_Translate);
        mBtnClear.setOnClickListener(this);
        mBtnTranslate.setOnClickListener(this);

        mLvVariants = (ListView) cont.findViewById(R.id.ma_lv_Variants);

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
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return cont;
    }

    @Override
    public void onResume() {
        super.onResume();

        mLvVariants.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retroYTClient = new RetroYTClient();
        retroYDClient = new RetroYDClient();

        translateDB = new TranslateDB(getContext());

        h = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                translateDB.addToHistory(mEditText.getText().toString(), (String) msg.obj);
            }
        };

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Очищаем текст по кнопке Clear
            case (R.id.ma_btn_Clear):
                mTvTranslate.setText("");
                mEditText.setText("");
                adapter = null;
                mLvVariants.setAdapter(adapter);

                break;

            //Получаем перевод по кнопке Translate
            case (R.id.ma_btn_Translate):
                String text = mEditText.getText().toString().trim();

                if (!TextUtils.isEmpty(text)) {
                    mTvTranslate.setText("");
                    mLvVariants.setAdapter(null);
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

    void getTranslation(String text) {

        //Если введена фраза
        //TODO поменять условия выбора перевода (по пробелу не подходит, нужно выводить результат, если будут введены любые символы между словами

        if (text.contains(" ")) {
            getYTTranslation(text, null, null);
            adapter = null;
        } else {
            getYTTranslation(text, null, null);
            getYDTranslations(text, null, null);
        }

    }

    //TODO сделать поддержку направления перевода
    private void getYTTranslation(String text, String langFrom, String langTo) {
        retroYTClient.getTranslation(text, "ru", new Callback<Translation>() {

            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {

                if (response.isSuccessful()) {
                    String translation = response.body().getText().get(0);
                    mTvTranslate.setText(translation);

                    Message msg = h.obtainMessage();
                    msg.obj = translation;
                    h.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });

    }

    //TODO сделать поддержку направления перевода
    private void getYDTranslations(String text, String langFrom, String langTo) {
        retroYDClient.getTranslations(text, "en-ru", new Callback<Translations>() {
            @Override
            public void onResponse(Call<Translations> call, Response<Translations> response) {
                if (response.isSuccessful()) {
                    Translations ts = response.body();
                    fillList(ts);
                }
            }

            @Override
            public void onFailure(Call<Translations> call, Throwable t) {

            }
        });
    }

    private void fillList(Translations ts) {

        String[] data = new String[ts.getDef().size()];

        //Наполняем массив преводов для адаптера
        for (int i = 0; i < ts.getDef().size(); i++) {
            StringBuilder sb = new StringBuilder(ts.getDef().get(i).getPos() + ": " + "\n");
            for (int j = 0; j < ts.getDef().get(i).getTr().size(); j++) {
                if (j == ts.getDef().get(i).getTr().size() - 1) {
                    sb.append(ts.getDef().get(i).getTr().get(j).getText());
                    break;
                }
                sb.append(ts.getDef().get(i).getTr().get(j).getText() + ", ");
            }
            data[i] = sb.toString();
        }

        adapter = new ArrayAdapter<>(getContext(),
                R.layout.translation_item, data);

        mLvVariants.setAdapter(adapter);
    }

    /*private class TranslationsListAdapter extends BaseAdapter{
        ArrayList<Translations.Def> defs;
        Context context;
        LayoutInflater li;

        TranslationsListAdapter(Context ctx, Translations ts){
            context = ctx;
            defs = (ArrayList<Translations.Def>) ts.getDef();
            li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return defs.size();
        }

        @Override
        public Object getItem(int position) {
            return defs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null){
                v = li.inflate(R)
            }
            return ;
        }
    }*/
}
