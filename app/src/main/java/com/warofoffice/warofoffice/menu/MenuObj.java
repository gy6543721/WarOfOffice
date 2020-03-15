package com.warofoffice.warofoffice.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class MenuObj {
    protected int x;
    protected int y;
    protected Context context;

    public MenuObj(Context context){
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
