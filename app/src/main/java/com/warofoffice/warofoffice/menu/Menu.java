package com.warofoffice.warofoffice.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.TransparentInputBox;
import com.warofoffice.warofoffice.obj.OnTouchObject;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public static final int MENU_START = 1;         //切換狀態的屬性
    public static final int MENU_STOP  = 0;
    public static final int MENU_GAME_START = 2;
    public static final int MENU_TUTORIAL = 3;
    public static final int MENU_LEADERBOARD = 4;


    public interface MenuStatusInterface{         //介面用來告知controller的方法
        public void updateStatus(int status);

    }


    private MenuStatusInterface menuStatusInterface;  //設一個介面的變數用來知道現在的狀態

    private Context context;                         //當前畫面的參數

    private List<MenuObj> menuObjs;      //一個存menuObj物件的變數用來代表ArrayList

    public Menu(Context context){       //處理沒有介面的狀態的情況下Menu要能正常運行
        init(context);

    }

    public Menu(Context context, MenuStatusInterface menuStatusInterface){      //處理有介面狀態的情況下
        init(context);
        this.menuStatusInterface = menuStatusInterface;


    }

    public void init(Context context){
        this.context = context;
        menuObjs = new ArrayList<>();

        menuObjs.add(new Background(context,0,0,0));
        menuObjs.add(new ButtonStart(context, MainActivity.screenWidth*1350/1920 ,MainActivity.screenHeight*3/11));
        menuObjs.add(new ButtonTutorial(context, MainActivity.screenWidth*1350/1920, MainActivity.screenHeight*5/11));
        menuObjs.add(new ButtonBoard(context, MainActivity.screenWidth*1350/1920,MainActivity.screenHeight*7/11));

    }

    public void onPaint(Canvas canvas, Paint paint){        //用來畫出當前物件


        for(MenuObj menuObj : menuObjs){        //跑過所有的menuObj
            menuObj.onPaint(canvas, paint);     //畫出所有的menuObj物件
        }
        try{

            TransparentInputBox inputBox = TransparentInputBox.getInstance(context);

            inputBox.draw(canvas, "a");


        }catch(TransparentInputBox.InputBoxException e){

        }

    }

    public void updateData(){

         //用來判斷

        for(MenuObj menuObj : menuObjs){
            updateMenuObj(menuObj);

        }

        //
        if(menuStatusInterface != null){
            if(ButtonStart.currentStatus == ButtonStart.NO_BUTTON || ButtonTutorial.currentStatus == ButtonTutorial.N0_BUTTON){
                menuStatusInterface.updateStatus(MENU_START);
            }

            // 遊戲開始
            if(ButtonStart.currentStatus == ButtonStart.YES_BUTTON){

                MainActivity.handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        //過0.3秒後要做的事情
                        menuStatusInterface.updateStatus(MENU_GAME_START); //讓圖示delay一下再進遊戲

                    }}, 300);
            }

            // 教學
            if(ButtonTutorial.currentStatus == ButtonTutorial.YES_BUTTON){

                MainActivity.handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        //過0.3秒後要做的事情
                        menuStatusInterface.updateStatus(MENU_TUTORIAL); //讓圖示delay一下再進教學

                    }}, 300);
            }

            // 排行榜
            if(ButtonBoard.currentStatus == ButtonBoard.YES_BUTTON){

                MainActivity.handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {

                        //過0.3秒後要做的事情
                        menuStatusInterface.updateStatus(MENU_LEADERBOARD); //讓圖示delay一下再進教學

                    }}, 300);
            }

        }
    }

    public void updateMenuObj(MenuObj menuObj){     //更新裡面的狀態

    }

    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                //手指碰到螢幕時觸發
                for(MenuObj menuObj: menuObjs){// for each
                    if(menuObj instanceof OnTouchObject){
                        OnTouchObject oto = (OnTouchObject)menuObj;
                        if(oto.isTouch(event.getX(), event.getY())){
                            oto.touchEvent();
                        }
                    }
                }
                try{
                    TransparentInputBox inputBox = TransparentInputBox.getInstance(context);

                    inputBox.checkTouch(event.getX(), event.getY(), "a");

                }catch(TransparentInputBox.InputBoxException e){
                    //Log.d("test", e.getMessage());
                }

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
    public Menu getMenu(){
        return new Menu(context, menuStatusInterface);
    }
}
