package com.example.yunus.gallery.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.GridView;

import com.example.yunus.gallery.Function;
import com.example.yunus.gallery.MapComparator;
import com.example.yunus.gallery.R;
import com.example.yunus.gallery.adapteres.SingleAlbumAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by yunus on 4/19/18.
 */

public class AlbumActivity extends AppCompatActivity {

    private GridView galleryGV;
    private ArrayList<HashMap<String, String>> imageList = new ArrayList<>();
    private String albumName = "";
    private LoadAlbumFromImages loadImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        albumName = intent.getStringExtra("name");
        setTitle(albumName);

        galleryGV = findViewById(R.id.albumGridView);
        int displayWidth = getResources().getDisplayMetrics().widthPixels;
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = displayWidth / (metrics.densityDpi / 160f);

        if (dp < 360) {
            dp = (dp - 17) / 2;
            float px = Function.convertDpToPixel(dp, getApplicationContext());
            galleryGV.setColumnWidth(Math.round(px));
        }

        loadImg = new LoadAlbumFromImages();
        loadImg.execute();
    }

    class LoadAlbumFromImages extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageList.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            String xml = "";
            String path;
            String album;
            String timestamp;
            Uri uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uriInternal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};

            Cursor cursorExternal = getContentResolver().query(uriExternal, projection, "bucket_display_name = \"" + albumName + "\"", null, null);
            Cursor cursorInternal = getContentResolver().query(uriInternal, projection, "bucket_display_name = \"" + albumName + "\"", null, null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal});
            while (cursor.moveToNext()) {

                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));

                imageList.add(Function.mappingInbox(album, path, timestamp, Function.convertToTime(timestamp), null));
            }
            cursor.close();
            Collections.sort(imageList, new MapComparator(Function.KEY_TIMESTAMP, "dsc"));
            return xml;


        }

        @Override
        protected void onPostExecute(String s) {
            SingleAlbumAdapter adapter = new SingleAlbumAdapter(AlbumActivity.this, imageList);
            galleryGV.setAdapter(adapter);
            galleryGV.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(AlbumActivity.this, GalleryActivity.class);
                intent.putExtra("path", imageList.get(+position).get(Function.KEY_PATH));
                startActivity(intent);
            });
        }
    }
}

