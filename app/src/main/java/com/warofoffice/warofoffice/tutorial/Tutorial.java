package com.warofoffice.warofoffice.tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Tutorial {

    public static final int TUTORIAL_START = 1;         //切換狀態的屬性
    public static final int TUTORIAL_STOP  = 0;

    public interface TutorialInterface{         //介面用來告知controller的方法
        public void updateStatus(int status);

    }

    private TutorialInterface tutorialInterface;
    private Context context;

    // 畫教學圖
    private TutorialBackground tutorialBackground;

    public Tutorial(Context context) {
        init(context);
    }
    public Tutorial(Context context,TutorialInterface tutorialInterface){      //處理有介面狀態的情況下
        init(context);
        this.tutorialInterface = tutorialInterface;
    }

    public void init(Context context){
        this.context = context;
//        this.switchPicture = 0;

        tutorialBackground = new TutorialBackground(context,0,0);
    }

    public void onPaint(Canvas canvas, Paint paint){

        tutorialBackground.onPaint(canvas,paint);
    }


    public void updateData(){

//        //用來判斷
//        updateTutorialObj(tutorialBackground);

        //
        if(tutorialInterface != null){

            if(TutorialBackground.switchPicture >= 3){

                TutorialBackground.switchPicture = 0; // 讓圖重設
                tutorialInterface.updateStatus(TUTORIAL_STOP);
            }
        }
    }

    public void updateTutorialObj(TutorialObj tutorialObj){     //更新裡面的狀態

    }

    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                tutorialBackground.touchEvent();

                break;
            case MotionEvent.ACTION_MOVE:
                //碰到之後持續觸發
                break;
            case MotionEvent.ACTION_UP:
                //放開時觸發

                break;
        }
        return true;
    }
}
