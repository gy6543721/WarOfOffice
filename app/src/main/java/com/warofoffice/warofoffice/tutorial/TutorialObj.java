package com.warofoffice.warofoffice.tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.warofoffice.warofoffice.obj.boss.AttackObject;

public abstract class TutorialObj {

    protected int x;
    protected int y;
    protected Context context;

    public TutorialObj(Context context){
        this.context =  context;
    }

    public abstract void onPaint(Canvas canvas, Paint paint);

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
