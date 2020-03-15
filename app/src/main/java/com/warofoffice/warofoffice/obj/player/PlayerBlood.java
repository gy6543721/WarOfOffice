package com.warofoffice.warofoffice.obj.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;
import com.warofoffice.warofoffice.obj.boss.AttackObject;
import com.warofoffice.warofoffice.obj.boss.Ghost;

public class PlayerBlood extends WorldObj {

    public static boolean playerDamage; //判斷玩家是否受到傷害
    public static float life;      //血量
    public static float initialLife;  //存一開始的血量
    public static float overLife; // 存結束後的血量

    private Bitmap playerBitmap;
    private int width;
    private  int height;

    float ratio;        //判斷血量與血條之間的比例

    public PlayerBlood(Context context, int x, int y, float life){
        super(context);
        this.x = x;
        this.y = y;

        this.initialLife = life;

        this.life = this.overLife;

        this.playerDamage = false;

        BitmapManager bm = new BitmapManager(context);
        int bitmapWidth = MainActivity.screenWidth*320/1920;
        int bitmapHeight = MainActivity.screenHeight*25/1080;

        playerBitmap = bm.getBitmap(R.drawable.player_blood,bitmapWidth,bitmapHeight);

        width = bitmapWidth;
        height = bitmapHeight;

        ratio = width / initialLife;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        if ( playerDamage == true ) {

            this.life -= AttackObject.damage;
            this.life -= Ghost.ghostDamage; //一定要加在這裡，不然就會持續扣血

            canvas.drawBitmap(playerBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x, y, (int)(life*ratio) + x, height + y),
                    null);

            playerDamage = false;

        } else {

            canvas.drawBitmap(playerBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x, y, (int)(life*ratio) + x, height + y),
                    null);

        }


    }
    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

        return;
    }
}
