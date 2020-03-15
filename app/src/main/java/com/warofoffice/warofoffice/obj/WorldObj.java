package com.warofoffice.warofoffice.obj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class WorldObj {
    protected int x;
    protected int y;
    protected Context context;


    public WorldObj(Context context){
        this.context = context;
    }

    public abstract void onPaint(Canvas canvas, Paint paint);

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public abstract void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit);
}