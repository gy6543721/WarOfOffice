package com.warofoffice.warofoffice.leaderboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class LeaderBoardObj {
    protected int x;
    protected int y;
    protected Context context;

    public LeaderBoardObj(Context context){
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
