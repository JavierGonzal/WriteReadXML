package com.thedeveloperworldisyous.writereadxml.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thedeveloperworldisyous.writereadxml.models.Film;

import java.util.List;

/**
 * Created by javiergonzalezcabezas on 11/10/15.
 */
public class ListAdapter extends BaseAdapter {
    List <Film> mListFilm;
    Context mContext;

    public ListAdapter(List<Film> mListFilm, Context mContext) {
        this.mListFilm = mListFilm;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
