package com.example.yunus.gallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunus.gallery.R;

import java.io.File;

/**
 * Created by yunus on 4/19/18.
 */

public class GalleryActivity extends AppCompatActivity {

    private ImageView galleryImg;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.gallery_preview);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        galleryImg = findViewById(R.id.previewImg);
        Glide.with(this)
                .load(new File(path))
                .into(galleryImg);
    }

}
