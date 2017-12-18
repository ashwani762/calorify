package com.ashwani.calorify.pref;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by Ashwani on 19-09-2017.
 */

public class Preference {
    public static void setValue(Context context, String str, boolean value){
        Editor edit = context.getSharedPreferences("MyPref",Context.MODE_PRIVATE).edit();
        edit.putBoolean(str,value);
        edit.apply();
    }

    public static boolean getValue(Context context, String str, boolean value){
        return context.getSharedPreferences("MyPref", Context.MODE_PRIVATE).getBoolean(str, value);
    }
}
