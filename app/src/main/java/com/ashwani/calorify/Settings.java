package com.ashwani.calorify;

import android.content.Intent;
import android.os.SystemClock;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ashwani.calorify.pref.Preference;

public class Settings extends PreferenceActivity {

    private int count = 0;
    private long startMilis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        android.preference.Preference about = getPreferenceManager().findPreference("about");
        if ( about !=null){
            about.setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(android.preference.Preference preference) {
                    //TODO: add about activity here
                    Intent intent = new Intent(Settings.this,AboutUs.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
            });
        }


        android.preference.Preference version = getPreferenceManager().findPreference("version");

        if( version != null){
            version.setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(android.preference.Preference preference) {
                    //Todo : Add easter egg here

                    long time = System.currentTimeMillis();

                    if(startMilis == 0 || (time - startMilis > 3000)){
                        startMilis = time;
                        count = 1;
                    }
                    else {
                        count++;
                    }

                    if(count == 5){
                        Toast.makeText(Settings.this,"Fibonacci series is 0,1,1,2,3,5,8,...",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,ClassifierActivity.class);
        startActivity(intent);
        finish();
    }
}
