package com.anykh_dev.yatranslate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listView;
    TranslateDB translateDB;
    SimpleCursorAdapter sca;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, null);
        listView = (ListView) v.findViewById(R.id.hf_List);

        listView.setAdapter(sca);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        translateDB = new TranslateDB(getContext());

        String[] from = {TranslateDB.HIS_TEXT_COLNAME, TranslateDB.HIS_TRANSLATION_COLNAME};
        int[] to = {R.id.textView1, R.id.textView2};

        sca = new SimpleCursorAdapter(getContext(), R.layout.list_item, null, from, to, 0);


        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CLoader(getContext(), translateDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        sca.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        translateDB.dbClose();
    }

    static class CLoader extends CursorLoader{

        TranslateDB tDB;

        CLoader(Context context, TranslateDB db) {
            super(context);
            tDB = db;
        }


        @Override
        public Cursor loadInBackground() {
            tDB.dbOpen();
            return tDB.getHisData();
        }
    }
}
