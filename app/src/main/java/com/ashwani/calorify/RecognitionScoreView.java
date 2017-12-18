package com.ashwani.calorify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ashwani.calorify.Classifier.Recognition;

import java.util.List;


/**
 * Created by Ashwani on 20-09-2017.
 */

public class RecognitionScoreView extends View implements ResultsView{

    private float textSizePx;
    private final Paint fpaint;
    private Recognition results;
    private int x;
    private int y;
    private Context context;
    private int top,bottom,left,right;
    private int btop,bbottom,bleft,bright;
    private String title,url;

    public RecognitionScoreView(final Context context, final AttributeSet set) {
        super(context, set);
        this.context = context;
        fpaint = new Paint();
        fpaint.setColor(Color.parseColor("#ffffff"));
        Display defaultDisplay = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.x = point.x;
        this.y = point.y;
        Log.d("x",""+this.x);
        Log.d("y",""+this.y);
    }

    @Override
    public void onDraw(final Canvas canvas) {
//        final int x = 10;
//        int y = (int) (fgPaint.getTextSize() * 1.5f);
//
//        canvas.drawPaint(bgPaint);
//
//        if (results != null) {
//            for (final Recognition recog : results) {
//                canvas.drawText(recog.getTitle() + ": " + recog.getConfidence(), x, y, fgPaint);
//                y += fgPaint.getTextSize() * 1.5f;
//            }
//        }

        Paint bpaint = new Paint();
        bpaint.setStyle(Style.FILL);
        bpaint.setColor(Color.WHITE);

        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res,R.drawable.setting);

        btop = 0;
        bbottom = (int)getdensity(70f);
        bleft = (int)(x- getdensity(70f));
        bright = x;

        canvas.drawBitmap(bitmap,x-getdensity(50f),60,bpaint);

        if(results != null){
            String knowmore = "Get Calories";

            String[] split = results.getTitle().split(",");

            //title = results.getTitle();

            float confidence = results.getConfidence() * 100;
            String conf_string = String.format("%.0f",confidence);

            title = split[0];
            url = split[1];

            float a = getdensity(250f);
            Paint paint = new Paint();
            float q = y - getdensity(50f);
            textSizePx = TypedValue.applyDimension(1, 26f, getResources().getDisplayMetrics());
            fpaint.setTextSize(textSizePx);
            fpaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-Light.otf"));
            a = q - a;
            RectF rectF = new RectF(((x - getWidth(title)) / 2 ) /2, ((a - textSizePx) - (getdensity(30f))), x - ((x - getWidth(title)) / 2/2), getdensity(95f)+((a - textSizePx) - (getdensity(30f))));
            paint.setColor(Color.parseColor("#000000"));
            paint.setStyle(Style.FILL);
            paint.setAlpha(100);
            canvas.drawRoundRect(rectF, 30,30,paint);
            canvas.drawText(title.toUpperCase() + " " + conf_string + "%", (x/2 - 25f) - (getWidth(title)/2), a, fpaint);

            top = x/4;
            bottom = x - (x/4);
            left = (int)(q - getdensity(30f));
            right = (int)(getdensity(20f)+q);

            RectF rectFl = new RectF(top,left,bottom,right);
            paint.setColor(Color.parseColor("#3F51B5"));
            paint.setStyle(Style.FILL);
            canvas.drawRoundRect(rectFl,190f,190f,paint);
            textSizePx = TypedValue.applyDimension(1,18f,getResources().getDisplayMetrics());
            fpaint.setTextSize(textSizePx);
            fpaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-Light.otf"));
            canvas.drawText(knowmore,(x/2)-(getWidth(knowmore)/2),q,fpaint);

        }
    }

    @Override
    public void setResults(final Recognition results) {
        this.results = results;
        postInvalidate();
    }

    public float getdensity(float f){
        return context.getResources().getDisplayMetrics().density * f;
    }

    public int getWidth(String str){
        Rect rect = new Rect();
        fpaint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();

    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        Activity activity = (Activity)context;
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();
                if(x > top && x < bottom && y > left && y < right && results!=null){
                    Intent intent = new Intent(this.context, BreedDetailsActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("url",url);
                    context.startActivity(intent);
                    activity.finish();
                    break;
                }


                if(y > btop && y < bbottom && x < bright && x > bleft){
                    //Log.d("Touched","Touched here");
                    Intent intent1 = new Intent(this.context,Settings.class);
                    context.startActivity(intent1);
                    activity.finish();
                    break;
                }

        }
        return true;
    }


}

