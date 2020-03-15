package com.warofoffice.warofoffice;

import android.content.Context;

import static com.warofoffice.warofoffice.MainActivity.soundPool;

public class SoundPoolSystem {
    //感覺在建構先創造出五個不同Boss的音效
    public static int attackSound1;
    public static int attackSound2;
    public static int attackSound3;
    public static int attackSound4;
    public static int attackSound5;

    public static int propSound;

    public static int skillSound; //放技能的音效

    public static int moneySound;

    public static int hitSound;    //Boss被打到的音效

    public static int buttonSound;

    public static int hintSound;  //音效用int

    public SoundPoolSystem(Context context){
        attackSound1 = soundPool.load(context, R.raw.attack1,1);
        attackSound2 = soundPool.load(context, R.raw.attack2,1);
        attackSound3 = soundPool.load(context, R.raw.attack3,1);
        attackSound4 = soundPool.load(context, R.raw.attack4,1);
        attackSound5 = soundPool.load(context, R.raw.attack5,1);

        hitSound = soundPool.load(context, R.raw.hit,2);

        propSound = soundPool.load(context, R.raw.prop,0);

        skillSound = soundPool.load(context, R.raw.skill,0);

        moneySound = soundPool.load(context, R.raw.money, 0);

        buttonSound = soundPool.load(context, R.raw.button,0);

        //創建音效
        hintSound = soundPool.load(context,R.raw.play_fight,0);

    }
}
