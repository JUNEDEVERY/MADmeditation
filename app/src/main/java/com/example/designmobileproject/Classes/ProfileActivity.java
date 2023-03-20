package com.example.designmobileproject.Classes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designmobileproject.Masks.MaskProfile;
import com.example.designmobileproject.R;
import com.example.designmobileproject.adapters.adapterProfile;
import com.example.designmobileproject.adapters.adapterQuote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ProfileActivity extends AppCompatActivity {

    TextView Name;
    ImageView Avatar;
    private com.example.designmobileproject.adapters.adapterProfile adapterProfile;
    public static MaskProfile maskProfile;
    private List<MaskProfile> listviewMaskProfile = new ArrayList<>();
    SharedPreferences sharedPreferences; // для сохранения настроек пользователя
    public static final String APP_PREFERENCES = "mySettings"; // сохранение параметров
    OutputStream outputStream; // поток байтов для запоминания

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Name = findViewById(R.id.tvNameProfile);
        Name.setText(OnboardingActivity.nickName);
        Avatar = findViewById(R.id.avatar);
        new adapterQuote.DownloadImageTask((ImageView) Avatar).execute(OnboardingActivity.avatar);
        GridView listView = findViewById(R.id.llPhoto);
        adapterProfile = new adapterProfile(ProfileActivity.this, listviewMaskProfile);
        listView.setAdapter(adapterProfile);
        showProfileImg();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MaskProfile mask = listviewMaskProfile.get(position);
                if(mask.getImageProfile() == null)
                {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    someActivityResultLauncher.launch(photoPickerIntent);
                }
                else
                {
                    maskProfile = listviewMaskProfile.get(position);
                    startActivity(new Intent(ProfileActivity.this, PhotoActivity.class));
                }
            }
        });


    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap bitmap = null;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try
                        {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        File dir = new File(getApplicationInfo().dataDir + "/MyFiles/");
                        dir.mkdirs();
                        File file = new File(dir, System.currentTimeMillis() + ".jpg");
                        try {
                            outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Toast.makeText(ProfileActivity.this, "Изображение доавлено", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(ProfileActivity.this, "В момент сохранения произошла непредвиденная ошибка", Toast.LENGTH_LONG).show();
                        }
                        showProfileImg();
                    }
                }
            });

    public  void goMenu(View view)
    {
        startActivity(new Intent(this, MenuActivity.class));
    }
    public  void goMain(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
    }
    public  void goListen(View view)
    {
        startActivity(new Intent(this, ListenActivity.class));
    }

    public void goLogin(View view)
    {
        sharedPreferences =  getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("Avatar", "").apply();
        sharedPreferences.edit().putString("NickName", "").apply();
        startActivity(new Intent(this, LoginActivity.class));
    }
    private void showProfileImg() 
    {
        File dir = new File(getApplicationInfo().dataDir + "/MyFiles/");
        dir.mkdirs();
        listviewMaskProfile.clear();
        adapterProfile.notifyDataSetInvalidated();
        String path = getApplicationInfo().dataDir + "/MyFiles";
        File directory = new File(path);
        File[] files = directory.listFiles();
        int j = 0;
        for (int i = 0; i < files.length; i++)
        {
            Long lastPhoto = files[i].lastModified();
            MaskProfile profile = new MaskProfile(j,files[i].getAbsolutePath(),files[i],convertToTime(lastPhoto));
            listviewMaskProfile.add(profile);
            adapterProfile.notifyDataSetInvalidated();
        }
        MaskProfile tempProduct = new MaskProfile(j,  null,null,"null");
        listviewMaskProfile.add(tempProduct);
        adapterProfile.notifyDataSetInvalidated();
    }
    public static String convertToTime(long timeInMillis) // Преобразование из милисикунд в формат часы:минуты
    {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        c.setTimeZone(TimeZone.getDefault());
        return format.format(c.getTime());
    }
}