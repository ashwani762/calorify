package com.ashwani.calorify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ashwani.calorify.pref.Preference;


public class FirstActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST = 1;

    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(hasPermission()){
            getPref();
        } else {
            requestPermission();
        }
    }

    private boolean hasPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        else{
            return true;
        }
    }

    private void requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(shouldShowRequestPermissionRationale(PERMISSION_CAMERA) || shouldShowRequestPermissionRationale(PERMISSION_STORAGE)){
                Toast.makeText(this, "Camera and storage permission are required for this app", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{PERMISSION_CAMERA, PERMISSION_STORAGE},PERMISSIONS_REQUEST);
        }
    }

    private void startClassifier(){
        startActivity(new Intent(this,ClassifierActivity.class));
        finish();
    }

    private void startOnboarding(){
        startActivity(new Intent(this,OnboardingActivity.class));
        finish();
    }

    private void getPref(){
        if(Preference.getValue(this, "onboarding_complete",false)){
            startClassifier();
        } else {
            startOnboarding();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode, final String[] permissions, final int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getPref();
                } else {
                    requestPermission();
                }
            }
        }
    }
}
