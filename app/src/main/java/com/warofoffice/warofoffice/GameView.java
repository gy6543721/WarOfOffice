package com.warofoffice.warofoffice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.warofoffice.warofoffice.controller.GameController;
import com.warofoffice.warofoffice.obj.OnTouchObject;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameView";
    private static final int FRAME_PER_SECOND = 30; // 60幀loading太重
    public static final int SECOND_PER_FRAME = 1000/FRAME_PER_SECOND;
    private static long currentTime;

    Context context;// 當前的畫面

    public static MediaPlayer player; //影片播放器
    SurfaceHolder surfaceHolder;
    Thread timer;

    public boolean flag = false; //story按鈕按下去
    private boolean isVideoCompleted = false;

    public static int screenWidth;
    public static int screenHeight;

    Canvas mCanvas = null;
    Paint paint;

    GameController gameController;

    public GameView(MainActivity context) {
        super(context);
        this.context = context;
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        // 先有paint的畫面，才能創建MediaPlayer，不然會當掉

    }

    int x = 0;
    protected void onPaint(Canvas canvas){
        if(surfaceHolder == null)
            return;
        gameController.onPaint(canvas, paint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if(isVideoCompleted) { // 沒進影片直接回menu
            startTimer();
            return;
        }

        paint = new Paint();
        player = MediaPlayer.create(context, R.raw.opening);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC); //負責處理影片的聲音
        // 設置影片顯示在SurfaceView上

        player.setDisplay(holder);


        try {
//            player.setDataSource("android.resource://"+ context.getPackageName() +"/" + R.raw.opening);
            //player.prepare(); //onResume時要讓影片先prepare
            player.start(); //開始播放
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) //影片播放完成後執行的動作
            {
                player.stop();
                player.release(); //要記得把影片清掉
                isVideoCompleted = true;
                init();

                flag = true;
                startTimer();
            }
        });
    }

    // load Menu & World
    private void startTimer(){
        if (timer == null) {
            timer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (flag) {
                        currentTime = SystemClock.uptimeMillis();

                        synchronized (surfaceHolder) {
                            try {
                                mCanvas = surfaceHolder.lockCanvas();
                                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                                gameController.run();//
                                onPaint(mCanvas);
                            } catch (Exception e) {
//                                Log.d(TAG, "!!" + e.getMessage());
                            } finally {
                                if (mCanvas != null)
                                    surfaceHolder.unlockCanvasAndPost(mCanvas);
                            }
                        }

                        long costTime = SystemClock.uptimeMillis() - currentTime;

                        try {
                            while (SECOND_PER_FRAME < costTime) {
                                costTime -= SECOND_PER_FRAME;
                            }
                            if (SECOND_PER_FRAME - costTime > 0) {
                                Thread.sleep(SECOND_PER_FRAME - costTime);
                            }//讓他幀數跟不上時，下次計算調整成正確的
                        } catch (Exception e) {
//                            Log.d(TAG, e.getMessage());
                        }
                    }
                }
            });
        }
        timer.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.screenHeight = height;
        this.screenWidth = width;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if(player != null){
            player.release();
            player = null;
        }

        if(timer != null){
            timer.interrupt();
            timer = null;
        }
    }

    public void init(){
//        Log.d(TAG, "init");
        if(gameController == null) { //這段不能忘記
            gameController = new GameController(context);
            setOnTouchListener(gameController);
        }
        gameController.updateLimit(0, 0, screenHeight, screenWidth);
    }


    public void setFlag(boolean flag){
        this.flag = flag;
    }

}

