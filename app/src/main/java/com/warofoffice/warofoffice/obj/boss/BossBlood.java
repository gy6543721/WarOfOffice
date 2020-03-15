package com.warofoffice.warofoffice.obj.boss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;

public class BossBlood extends WorldObj {
    public interface BloodBarListener{
        public float getBlood();
    }
    private BloodBarListener bloodBarListener;

    public static boolean bossDamage;
    public static boolean bossBigDamage;

    private Bitmap bloodBarBitmap;

    private float width;
    private int height;

    float ratio;     //判斷血量與血條之間的比例
    float bossLife;    //血條長度

    public BossBlood(Context context, int x , int y, BloodBarListener bloodBarListener){
        super(context);

        this.x =x;
        this.y =y;
        this.bloodBarListener = bloodBarListener;

        this.bossDamage = false;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*240/1920;
        int bitmapHeight = MainActivity.screenHeight*18/1080;

        bloodBarBitmap = bm.getBitmap(R.drawable.blood,bitmapWidth,bitmapHeight);

        width = bitmapWidth;
        height = bitmapHeight;

        ratio = width / bloodBarListener.getBlood();

    }
    @Override
    public void onPaint(Canvas canvas, Paint paint){
        bossLife = bloodBarListener.getBlood() * ratio;
        //bossLife = BossOne.blood*ratio;
        canvas.drawBitmap(bloodBarBitmap,
                new Rect(0,0,(int)width,height),
                new Rect(x,y,(int)bossLife + x,height + y),
                null);

    }
    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

        return;
    }
}
