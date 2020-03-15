package com.warofoffice.warofoffice;

import android.app.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import java.text.AttributedCharacterIterator;

public class MainActivity extends AppCompatActivity implements SensorEventListener { //implements SensorEventListener才能啟用g sensor

    private static final String TAG = "MainActivity";

    public static int screenWidth;
    public static int screenHeight; //手機螢幕的比例，直接設成全域變數

    public static Vibrator myVibrator; //震動服務

    public static Handler handler;//計時功能

    public SensorManager sensorManager; // g sensor

    public Sensor mSensor; // g sensor

    public static boolean gBlockOrNot; //判斷有沒有用g sensor擋掉

    public static String name; // scoreboard預設姓名

    public static SoundPool soundPool;

    public static MediaPlayer menuMusic;

    public static MediaPlayer fightMusic;

    public SoundPoolSystem soundPoolSystem;

    //SharePreferences
    public static SharedPreferences sharedPreferences;

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("test","onCreate");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics); //取得手機螢幕長寬
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels;


        //取得震動服務
        myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        //計時服務
        handler = new Handler();

        //g sensor服務
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE); //獲取陀螺儀

        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 獲取三軸加速感應器，單位m/s2
        // 註冊sensorManager
        sensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_GAME);

        gBlockOrNot = false; // 傳給AttackObject

        name = "你"; // scoreboard預設姓名

        //建出這個sharedPreferences的實體
        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

//        Log.d("test", (gameView == null)+"");

        gameView = new GameView(this);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);   //全螢幕設定

        setContentView(gameView);

        //創建音樂寫在setContentView(gameView) 下面

        //創建音樂
        menuMusic = MediaPlayer.create(this, R.raw.menu_bgm);
        menuMusic.setAudioStreamType(AudioManager.STREAM_MUSIC); // 載入音量鍵控制
        menuMusic.setLooping(true);

        fightMusic = MediaPlayer.create(this, R.raw.fight);
        fightMusic.setAudioStreamType(AudioManager.STREAM_MUSIC); // 載入音量鍵控制
        fightMusic.setLooping(true);    //循環播放

        //創建音效
        SoundPool.Builder buider = new SoundPool.Builder();
        buider.setMaxStreams(15);    //音效數量先設定15個

        AudioAttributes.Builder audioAttrBuider = new AudioAttributes.Builder();
        audioAttrBuider.setUsage(AudioAttributes.USAGE_GAME);
        buider.setAudioAttributes(audioAttrBuider.build());
        soundPool = buider.build();

        soundPoolSystem = new SoundPoolSystem(this);

        //先綁定           inputBox管理所有對話框的方法
        TransparentInputBox inputBox = TransparentInputBox.getInstance(this);
        TransparentInputBox.Builder inputName = new TransparentInputBox.Builder();

        //在用畫的地方把這個東西的實體丟進去就可以畫出來    要try catch
        try {
            inputBox.addInputField(inputName
                    .setKey("a")    //是用來記錄是哪一個輸入框的key
                    .setPosition(screenWidth * 150 / 1920, screenHeight * 200 / 1080)
                    .setSize(screenWidth * 300 / 1920, screenHeight * 100 / 1080)
                    .setMaxLength(5)
                    .getParam());   //新增出來

            inputBox.setInputBoxInterface(new TransparentInputBox.InputBoxInterface() {
                @Override
                public void onTextChanged(String key, String text) {
                    name = text;
                }

                @Override
                public void onKeyboardClosed(String key, String text) {

                }
            });
        } catch (TransparentInputBox.InputBoxException e) {

        }
        //可以設定是不是實心的長方形 setFill
        //setMaxLength 設定字數上限
        //setSize框的大小
        //設定顏色可以用色碼去做
        //Position是左上角的位置
        //delaytime用來控制游標的閃爍速度
        //setKey這個去用這個key去取得這個對話框的實體

    }

//    // 音量控制
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//
//            setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//        } else {
//
//            onPause();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onPause(){
        super.onPause();
//        Log.d("test","onPause");
        if(gameView != null) {
            gameView.setFlag(false);

            menuMusic.pause();
            fightMusic.pause();
            soundPool.autoPause();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d("test","onResume");
        if(gameView != null) {
            gameView.setFlag(true);

        }

//        if (GameView.flag = true) {
//
//            menuMusic.seekTo(0);
//            menuMusic.start();
//        }
    }

    // 把返回鍵disable掉
    @Override
    public void onBackPressed() {
//        return;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        Log.d("test","onDestroy");
////        AssetUtils.destroyInstance();
////        BitmapLoader.destroyInstance();
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {

//        gravity[0] = event.values[0]; //x軸 上負下正
//        gravity[1] = event.values[1]; //y軸 左負右正
//        gravity[2] = event.values[2]; //z軸


        if ( event.values[1] < -5 ) {

            gBlockOrNot = true;

        } else {

            gBlockOrNot = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
