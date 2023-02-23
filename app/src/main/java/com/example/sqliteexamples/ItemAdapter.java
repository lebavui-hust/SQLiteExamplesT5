package com.example.sqliteexamples;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemAdapter extends BaseAdapter {
    Cursor cs;

    @Override
    public int getCount() {
        return cs.getCount();
    }

    @Override
    public Object getItem(int i) {
        return cs.moveToPosition(i);
    }

    @Override
    public long getItemId(int i) {
        cs.moveToPosition(i);
        return cs.getInt(0);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        cs.moveToPosition(i);
        int recID = cs.getInt(0);
        String name = cs.getString(1);
        String phone = cs.getString(2);

        return null;
    }
}
