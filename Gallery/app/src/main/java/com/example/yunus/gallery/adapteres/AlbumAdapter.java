package com.example.yunus.gallery.adapteres;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunus.gallery.Function;
import com.example.yunus.gallery.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yunus on 4/28/18.
 */

public class AlbumAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public AlbumAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
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
        AlbumViewHolder holder = null;
        if (view == null) {
            holder = new AlbumViewHolder();
            view = LayoutInflater.from(activity).inflate(
                    R.layout.album_row, viewGroup, false);
            holder.galleryImage = view.findViewById(R.id.gImg);
            holder.gallery_count = view.findViewById(R.id.tvCount);
            holder.gallery_title = view.findViewById(R.id.tvName);

            view.setTag(holder);
        } else {
            holder = (AlbumViewHolder) view.getTag();
        }
        holder.galleryImage.setId(pos);
        holder.gallery_count.setId(pos);
        holder.gallery_title.setId(pos);

        HashMap<String, String> song;
        song = data.get(pos);
        try {
            holder.gallery_title.setText(song.get(Function.KEY_ALBUM));
            holder.gallery_count.setText(song.get(Function.KEY_COUNT));

            Glide.with(activity)
                    .load(new File(song.get(Function.KEY_PATH))) // Uri of the picture
                    .into(holder.galleryImage);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    class AlbumViewHolder {
        ImageView galleryImage;
        TextView gallery_count;
        TextView gallery_title;
    }

}

