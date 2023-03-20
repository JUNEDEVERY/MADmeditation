package com.example.designmobileproject.Classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.designmobileproject.Masks.MaskSend;
import com.example.designmobileproject.Masks.MaskUser;
import com.example.designmobileproject.R;
import com.example.designmobileproject.RetrofitApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    boolean isDog;
    EditText etEmail, etPassword;
    SharedPreferences sharedPreferences; // для сохранения настроек пользователя
    public static final String APP_PREFERENCES = "mySettings"; // сохранение параметров
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        sharedPreferences = this.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences != null) // если настройки, а именно емайл не пустой, переходим к паролю{
        {
            etEmail.setText(sharedPreferences.getString("Email", ""));
            etPassword.requestFocus();
        }
    }
    public void goRegistration(View v){
        startActivity(new Intent(this, RegistrationActivity.class));

    }

    public void checkDogProfile(View v){
        if (etEmail.getText().length() == 0 || etPassword.getText().length() == 0 ) {
            Toast.makeText(this, "Возможно одно или несколько полей были незаполнены", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            // создаем регулярку на проверку собаки в поле емайла
            Pattern pattern = Pattern.compile("@");
            Matcher matcher = pattern.matcher(etEmail.getText().toString());
            isDog = matcher.find();
            if(isDog == true){
                startActivity(new Intent(this, ProfileActivity.class));
            }
            else{
                Toast.makeText(LoginActivity.this, "Не найден символ, отделяющий имя пользователя и домен - @", Toast.LENGTH_LONG).show();

            }
        }
    }
    public void checkDog(View v){
        if (etEmail.getText().length() == 0 || etPassword.getText().length() == 0 ) {
            Toast.makeText(this, "Возможно одно или несколько полей были незаполнены", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            // создаем регулярку на проверку собаки в поле емайла
            Pattern pattern = Pattern.compile("@");
            Matcher matcher = pattern.matcher(etEmail.getText().toString());
            isDog = matcher.find();
            if(isDog == true){
                getLogin();
            }
            else{
                Toast.makeText(LoginActivity.this, "Не найден символ, отделяющий имя пользователя и домен - @", Toast.LENGTH_LONG).show();

            }
        }
    }
    private void getLogin(){
        retrofit2.Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
        MaskSend mSend = new MaskSend(etEmail.getText().toString(), etPassword.getText().toString());
        Call<MaskUser> call = retrofitAPI.User(mSend);

        call.enqueue(new Callback<MaskUser>() {
            @Override
            public void onResponse(Call<MaskUser> call, Response<MaskUser> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Учетная запись не найдена", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body() != null)
                {
                    if(response.body().getToken() != null)
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("Email", "" + etEmail.getText().toString()).apply();
                        sharedPreferences.edit().putString("Avatar", "" + response.body().getAvatar()).apply();
                        sharedPreferences.edit().putString("NickName", "" + response.body().getNickName()).apply();

                        OnboardingActivity.avatar = response.body().getAvatar();
                        OnboardingActivity.nickName = response.body().getNickName();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<MaskUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "При попытке авторизации в системе произошла следующая ошибка " + t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });
    }
}