package com.example.designmobileproject.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.designmobileproject.R;

public class OnboardingActivity extends AppCompatActivity {

    public static String avatar, nickName;

    SharedPreferences sharedPreferences; // для сохранения настроек пользователя
    public static final String APP_PREFERENCES = "mySettings"; // сохранение параметров
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

         sharedPreferences = this.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences != null) // если настройки, а именно емайл не пустой, переходим к паролю{
        {
            if(!sharedPreferences.getString("NickName", "").equals("")) {
                avatar = sharedPreferences.getString("Avatar", "");
                nickName = sharedPreferences.getString("NickName", "");
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }
    public void goRegistration(View v){
        startActivity(new Intent(this, RegistrationActivity.class));
    }
    public void goAuthorization(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }
}