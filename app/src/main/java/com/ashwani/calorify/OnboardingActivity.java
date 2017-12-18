package com.ashwani.calorify;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;

import com.ashwani.calorify.pref.Preference;
import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwani on 19-09-2017.
 */

public class OnboardingActivity extends AhoyOnboarderActivity{

    @Override
    public void onFinishButtonPressed() {
        Preference.setValue(this, "onboarding_complete", true);
        startActivity(new Intent(this, ClassifierActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int x = point.x;
        int y = point.y;
        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Get What food it is","Now easily find what food you are having and get the complete detail about the food like calories and workout accordingly",R.drawable.second);
        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard1.setTitleColor(R.color.white);
        ahoyOnboarderCard1.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard1.setTitleTextSize(dpToPixels(10,this));
        ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(8, this));
        //ahoyOnboarderCard1.setIconLayoutParams(x / 2, x / 2,y / 8,30,30,0);

/*        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Every Details","Oh! It doesn't stop here, get the amount of calories present in the item and workout accordingly",R.drawable.second);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setTitleColor(R.color.white);
        ahoyOnboarderCard2.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard2.setTitleTextSize(dpToPixels(10,this));
        ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(8, this));*/
        //ahoyOnboarderCard2.setIconLayoutParams(x / 2.6, );

        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Works in Real TIme","Just pull out your smartphone, open the app and you are good to go",R.drawable.third);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setTitleColor(R.color.white);
        ahoyOnboarderCard3.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard3.setTitleTextSize(dpToPixels(10,this));
        ahoyOnboarderCard3.setDescriptionTextSize(dpToPixels(8, this));
        //ahoyOnboarderCard3.setIconLayoutParams(x / 2.6, );


        setFont(Typeface.createFromAsset(getAssets(),"Avenir-Book.otf"));
        setFinishButtonTitle("Finish");
        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(ahoyOnboarderCard1);
        /*pages.add(ahoyOnboarderCard2);*/
        pages.add(ahoyOnboarderCard3);

        setOnboardPages(pages);

        setImageBackground(R.drawable.bg);

    }
}
