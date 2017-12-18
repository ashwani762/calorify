package com.ashwani.calorify;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Ashwani on 20-09-2017.
 */

public class BreedDetailsActivity extends Activity{

    TextView title,cal,cal_fat,total,walking,running,cycling;
    ImageView back;
    private String name,ur;
    Food food;
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calories_data);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        food = new Food();

        Bundle extras = getIntent().getExtras();
        name =  extras.getString("title");
        ur = extras.getString("url");

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title = (TextView) findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"Futura-Medium.ttf");
        title.setTypeface(typeface);
        title.setText(name.toUpperCase());

        cal = (TextView) findViewById(R.id.cal);
        cal.setTypeface(typeface);
        cal_fat = (TextView) findViewById(R.id.cal_fat);
        cal_fat.setTypeface(typeface);
        total = (TextView) findViewById(R.id.total);
        total.setTypeface(typeface);

        walking = (TextView) findViewById(R.id.walking);
        walking.setTypeface(typeface);
        running = (TextView) findViewById(R.id.running);
        running.setTypeface(typeface);
        cycling = (TextView) findViewById(R.id.cycling);
        cycling.setTypeface(typeface);


        new AsyncFetch().execute();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ClassifierActivity.class);
        intent.putExtra("flag","back");
        startActivity(intent);
        finish();
    }

    private class AsyncFetch extends AsyncTask<Void, Void, Void>{

        ProgressDialog progressDialog = new ProgressDialog(BreedDetailsActivity.this);
        HttpURLConnection conn;
        URL url = null;
        StringBuilder result = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("\tJust a Second...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            try{
                String server_url = sharedPreferences.getString("server_url","https://calorie.herokuapp.com/");
                url = new URL(server_url+ur);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(50000);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.connect();

                int response_code = conn.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String line = "";

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                conn.disconnect();
            }


            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            try{
                JSONObject json_data = new JSONObject(result.toString());
                food.calories = json_data.getString("calories");
                food.calories_from_fat = json_data.getString("calories_fat");
                food.total_Fat = json_data.getString("total_fat");

                cal.setText(food.calories+" KCal");
                cal_fat.setText(food.calories_from_fat+" KCal");
                total.setText(food.total_Fat+" g");

                int weight = Integer.parseInt(sharedPreferences.getString("weight","50"));

                float walking_burn = (Float.parseFloat(food.calories)/(1.9f*weight));
                float running_burn = (Float.parseFloat(food.calories)/(7.0f*weight));
                float cycling_burn = (Float.parseFloat(food.calories)/(4.0f*weight));

                String burn_walk = String.format("%.0f",(walking_burn*60));
                String burn_run = String.format("%.0f",(running_burn*60));
                String burn_cycle = String.format("%.0f",(cycling_burn*60));

                walking.setText(burn_walk + " mins");
                running.setText(burn_run + " mins");
                cycling.setText(burn_cycle + " mins");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
