package com.example.designmobileproject.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.designmobileproject.R;

public class PhotoActivity extends AppCompatActivity {



    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        SubsamplingScaleImageView subsamplingScaleImageView = findViewById(R.id.img);
        if (ProfileActivity.maskProfile.getImageProfile().exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(ProfileActivity.maskProfile.getImageProfile().getAbsolutePath());
            subsamplingScaleImageView.setImage(ImageSource.bitmap(myBitmap));
        }
        subsamplingScaleImageView.setOnTouchListener(new SwipeActivity(PhotoActivity.this) { // Добавляем обработку смахивания
            public void onSwipeRight() {
                ProfileActivity.maskProfile = null;
                startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
            }
            public void onSwipeLeft() {
                try {
                    ProfileActivity.maskProfile.imageProfile.delete();
                    ProfileActivity.maskProfile = null;
                    Toast.makeText(PhotoActivity.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(PhotoActivity.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view = findViewById(R.id.clLayou);
        view.setOnTouchListener(new SwipeActivity(PhotoActivity.this){

            public void onSwipeRight() {
                ProfileActivity.maskProfile = null;
                startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
            }
            public void onSwipeLeft() {
                try {
                    ProfileActivity.maskProfile.imageProfile.delete();
                    ProfileActivity.maskProfile = null;
                    Toast.makeText(PhotoActivity.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(PhotoActivity.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void Close(View view)
    {
        ProfileActivity.maskProfile = null;
        startActivity(new Intent(this, ProfileActivity.class));
    }
    public void Delete(View view)
    {
        try {
            ProfileActivity.maskProfile.imageProfile.delete();
            ProfileActivity.maskProfile = null;
            Toast.makeText(PhotoActivity.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
        }
        catch (Exception e)
        {
            Toast.makeText(PhotoActivity.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
        }
    }
}