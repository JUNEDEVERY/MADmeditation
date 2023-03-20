package com.example.designmobileproject.Classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designmobileproject.Masks.MaskFeeling;
import com.example.designmobileproject.Masks.MaskQuote;
import com.example.designmobileproject.R;
import com.example.designmobileproject.adapters.adapterFeeling;
import com.example.designmobileproject.adapters.adapterQuote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final List<MaskQuote> listQuote = new ArrayList<>();
    private final List<MaskFeeling> listFeeling = new ArrayList<>();
    private com.example.designmobileproject.adapters.adapterQuote adapterQuote;
    private com.example.designmobileproject.adapters.adapterFeeling adapterFeeling;


    public String name = "С " + "возвращением, " + OnboardingActivity.nickName + "!";
    ImageView imgProfile;
    TextView tvGreeting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvQuestion = findViewById(R.id.lvQuestions);
        adapterQuote = new adapterQuote(MainActivity.this, listQuote);
        lvQuestion.setAdapter(adapterQuote);
        new GetQuotes().execute();

        RecyclerView recyclerView = findViewById(R.id.rvCard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        adapterFeeling = new adapterFeeling(listFeeling, MainActivity.this);
        recyclerView.setAdapter(adapterFeeling);
        new GetFeeling().execute();

        imgProfile = findViewById(R.id.imgProfileAvatar);
        new adapterQuote.DownloadImageTask(imgProfile)
                .execute(OnboardingActivity.avatar);

        tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText(tvGreeting.getText().toString() + name);


    }
    private class GetQuotes extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listQuote.clear();
                adapterQuote.notifyDataSetInvalidated();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskQuote tempProduct = new MaskQuote(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getString("description")
                    );
                    listQuote.add(tempProduct);
                    adapterQuote.notifyDataSetInvalidated();
                }

            }
            catch (Exception exception)
            {
                Toast.makeText(MainActivity.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public  void goMenu(View view)
    {
        startActivity(new Intent(this, MenuActivity.class));
    }

    public  void goProfile(View view)
    {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void goListen(View view)
    {
        startActivity(new Intent(this, ListenActivity.class));
    }
    private class GetFeeling extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listFeeling.clear();
                adapterFeeling.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskFeeling tempProduct = new MaskFeeling(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getInt("position")
                    );
                    listFeeling.add(tempProduct);
                    adapterFeeling.notifyDataSetChanged();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listFeeling.sort(Comparator.comparing(MaskFeeling::getPosition));
                }
                adapterFeeling.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(MainActivity.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }
}