package com.example.abdullah.movieproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.support.v7.widget.AppCompatImageView;


/**
 * Created by abdullah on 18/03/19.
 */

public class CustomImageView extends AppCompatImageView {

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomImageView(Context context) {
        super(context);
    }
    boolean drawGlow = false;
    //this is the pixel coordinates of the screen
    float glowX = 0;
    float glowY = 0;
    //this is the radius of the circle we are drawing
    float radius = 20;
    //this is the paint object which specifies the color and alpha level
    //of the circle we draw
    Paint paint = new Paint();
    {
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setAlpha(50);
    };

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(drawGlow)
            canvas.drawCircle(glowX, glowY, radius, paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            drawGlow = true;
        }else if(event.getAction() == MotionEvent.ACTION_UP)
            drawGlow = false;

        glowX = event.getX();
        glowY = event.getY();
        this.invalidate();
        return true;
    }


}
