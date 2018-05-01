package com.example.yunus.gallery.adapteres;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunus.gallery.Function;
import com.example.yunus.gallery.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yunus on 4/28/18.
 */

public class SingleAlbumAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public SingleAlbumAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        SingleAlbumViewHolder holder;
        if (view == null) {
            holder = new SingleAlbumViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.single_album_rom,
                    viewGroup, false);
            holder.galleryImg = view.findViewById(R.id.galleryImg);
            view.setTag(holder);
        } else {
            holder = (SingleAlbumViewHolder) view.getTag();
        }

        holder.galleryImg.setId(pos);
        HashMap<String, String> song;
        song = data.get(pos);

        try {
            Glide.with(activity)
                    .load(new File(song.get(Function.KEY_PATH)))
                    .into(holder.galleryImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    class SingleAlbumViewHolder {
        ImageView galleryImg;
    }
}

