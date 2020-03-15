package com.warofoffice.warofoffice;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

public class TransparentInputBox{
    private static class TransparentEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {

        private interface InputInterface{
            void onInputClosed(String text);
            void onInputChanged(String text);
        }

        Activity activity;
        LinearLayout linearLayout;
        InputInterface inputInterface;
        boolean isOpened;

        private TransparentEditText(Activity activity, InputInterface inputInterface) {
            super(activity);
            init(activity, inputInterface);
        }
        private TransparentEditText(Activity activity, AttributeSet attrs, InputInterface inputInterface) {
            super(activity, attrs);
            init(activity, inputInterface);
        }
        private TransparentEditText(Activity activity, AttributeSet attrs, int defStyleAttr, InputInterface inputInterface) {
            super(activity, attrs, defStyleAttr);
            init(activity, inputInterface);
        }
        private void init(Activity activity, InputInterface inputInterface){
            this.activity = activity;
            this.inputInterface = inputInterface;
            addTextChangedListener(this);
            setFocusableInTouchMode(true);
            setFocusable(true);
            this.isOpened = false;
        }

        public static TransparentEditText genEditText(final Activity activity, InputInterface inputInterface){
            // create TransparentEditText by specific method
            final TransparentEditText transparentEditText = new TransparentEditText(activity, inputInterface);
            // add this editText to current activity
            activity.addContentView(transparentEditText, new LinearLayout.LayoutParams(0, 1));

            // add mask to current activity
            transparentEditText.linearLayout = new LinearLayout(activity);
            // when use click mask => close keyboard => return Text
            transparentEditText.linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeKeyboard(activity, v, transparentEditText);
                }
            });
            return transparentEditText;
        }

        public void openKeyboard(){
            if(isOpened)
                return;
            isOpened = true;
            // add mask
            activity.addContentView(linearLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // get EditText focus
            requestFocus();

            // open keyboard
            InputMethodManager imm = (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);

            // move cursor to last char
            setSelection(getText().length());
        }
        private static void closeKeyboard(Activity activity, View v, TransparentEditText transparentEditText){
            if(!transparentEditText.isOpened)
                return;
            transparentEditText.isOpened = false;
            // remove mask
            ((ViewGroup) v.getParent()).removeView(v);

            // close keyboard
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            // return text
            transparentEditText.inputInterface.onInputClosed(transparentEditText.getText().toString());

            transparentEditText.clearFocus();
        }

        @Override
        protected void onDraw(Canvas canvas){
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            if(inputInterface != null){
                inputInterface.onInputChanged(s.toString());
            }
        }

        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event)
        {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                closeKeyboard(activity, linearLayout, this);
            }
            return false; // 這個設true會讓音量鍵壞掉
        }
    }
    public class InputBoxException extends Exception{
        public InputBoxException() {
            // TODO Auto-generated constructor stub
        }

        public InputBoxException(String message) {
            super(message);
            // TODO Auto-generated constructor stub
        }

        public InputBoxException(Throwable cause) {
            super(cause);
            // TODO Auto-generated constructor stub
        }

        public InputBoxException(String message, Throwable cause) {
            super(message, cause);
            // TODO Auto-generated constructor stub
        }
    }
    public interface InputBoxInterface{
        void onTextChanged(String key, String text);
        void onKeyboardClosed(String key, String text);
    }

    // Draw Param
    private static class Param{
        public PaintStyle paintStyle = new PaintStyle();
        public int maxLength = -1;
        public String key;
    }
    private static class PaintStyle{
        public int rectColor = Color.WHITE;
        public int rectStrokeWidth = 5;
        public boolean isFill = true;
        public int cornerRadius = 10;

        public int textColor = Color.BLACK;
        public int textSize = 50;

        public int x = 0;
        public int y = 0;
        public int width = 400;
        public int height = 100;
        public int leftPadding = 10;

        public int delayTime = 12;
    }
    public static class Builder{
        private Param param;
        public Builder(){
            param = new Param();
        }
        public Builder setMaxLength(int maxLength){
            param.maxLength = maxLength;
            return this;
        }
        public Builder setKey(String key){
            param.key = key;
            return this;
        }

        public Builder setRectColor(int color){
            param.paintStyle.rectColor = color;
            return this;
        }
        public Builder setRectStrokeWidth(int strokeWidth){
            param.paintStyle.rectStrokeWidth = strokeWidth;
            return this;
        }
        public Builder setFill(boolean isFill){
            param.paintStyle.isFill = isFill;
            return this;
        }
        public Builder setCornerRadius(int cornerRadius){
            param.paintStyle.cornerRadius = cornerRadius;
            return this;
        }

        public Builder setTextColor(int color){
            param.paintStyle.textColor = color;
            return this;
        }
        public Builder setTextSize(int textSize){
            param.paintStyle.textSize = textSize;
            return this;
        }
        public Builder setPosition(int x, int y){
            param.paintStyle.x = x;
            param.paintStyle.y = y;
            return this;
        }
        public Builder setSize(int width, int height){
            param.paintStyle.width = width;
            param.paintStyle.height = height;
            return this;
        }
        public Builder setLeftPadding(int padding){
            param.paintStyle.leftPadding = padding;
            return this;
        }
        public Builder setDelayTime(int delayTime){
            param.paintStyle.delayTime = delayTime;
            return this;
        }

        public Param getParam(){
            return param;
        }
    }

    private static TransparentInputBox transparentInputBox;

    private Activity activity;
    private TransparentEditText transparentEditText;
    private Map<String, String> inputMap;
    private Map<String, Integer> inputLimit;
    private Map<String, PaintStyle> inputStyle;
    private TransparentEditText.InputInterface inputInterface;
    private InputBoxInterface inputBoxInterface;
    private String currentKey;

    // Cursor Control
    private boolean showCursor;
    private int delayTime;
    private int currentDelayTime;
    // PaintStyle
    private Paint rectPaint;
    private Paint textPaint;
    private int x;
    private int y;
    private int height;
    private int width;
    private int cornerRadius;
    private int leftPadding;
    private int textSize;
    private Rect textBounds = new Rect();

    public static TransparentInputBox getInstance(Activity activity){
        if(transparentInputBox == null){
            transparentInputBox = new TransparentInputBox(activity);
        }
        if(transparentInputBox.activity != activity){
            transparentInputBox.reBindActivity(activity);//(bind new activity)
        }
        return transparentInputBox;
    }
    public static TransparentInputBox getInstance(Context context){
        if(transparentInputBox.activity == context){
            return transparentInputBox;
        }
        return null;
    }
    private TransparentInputBox(Activity activity){
        currentKey = "";
        this.activity = activity;
        inputInterface = new TransparentEditText.InputInterface() {
            @Override
            public void onInputClosed(String text) {
                inputMap.put(currentKey, text);
                if(inputBoxInterface != null)
                    inputBoxInterface.onKeyboardClosed(currentKey, text);
                currentKey = "";
            }

            @Override
            public void onInputChanged(String text) {
                inputMap.put(currentKey, text);
                if(inputBoxInterface != null)
                    inputBoxInterface.onTextChanged(currentKey, text);
            }
        };
        transparentEditText = TransparentEditText.genEditText(activity, inputInterface);
        inputMap = new HashMap<>();
        inputLimit = new HashMap<>();
        inputStyle = new HashMap<>();

        currentDelayTime = 0;
        showCursor = true;
    }
    private void reBindActivity(Activity activity){
        this.activity = activity;
        // clear editText from activity (unbind)
        ((ViewGroup)transparentInputBox.transparentEditText.getParent()).removeView(transparentInputBox.transparentEditText);
        // bind
        transparentEditText = TransparentEditText.genEditText(activity, inputInterface);
    }

    public void setInputBoxInterface(InputBoxInterface inputBoxInterface){
        this.inputBoxInterface = inputBoxInterface;
    }
    private void setPaintStyle(PaintStyle paintStyle){
        if(rectPaint == null)
            rectPaint = new Paint();
        rectPaint.setColor(paintStyle.rectColor);
        rectPaint.setStrokeWidth(paintStyle.rectStrokeWidth);
        if(!paintStyle.isFill)
            rectPaint.setStyle(Paint.Style.STROKE);
        else{
            rectPaint.setStyle(Paint.Style.FILL);
        }

        if(textPaint == null)
            textPaint = new Paint();
        textPaint.setColor(paintStyle.textColor);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(paintStyle.textSize);
        this.textSize = paintStyle.textSize;

        this.x = paintStyle.x;
        this.y = paintStyle.y;
        this.height = paintStyle.height;
        this.width = paintStyle.width;
        this.cornerRadius = paintStyle.cornerRadius;
        this.leftPadding = paintStyle.leftPadding;
        this.delayTime = paintStyle.delayTime;
    }
    private void setPaintStyle(String key) throws InputBoxException{
        if(!inputMap.containsKey(key)){
            throw new InputBoxException("The key doesn't exist, Parameter name: " + key);
        }
        setPaintStyle(inputStyle.get(key));
    }

    public void addInputField(Param param) throws InputBoxException{
        if(inputMap.containsKey(param.key)){
            throw new InputBoxException("key already exists, Parameter name: " + param.key);
        }
        inputMap.put(param.key, "");
        inputLimit.put(param.key, param.maxLength);
        inputStyle.put(param.key, param.paintStyle);
    }
    public String getInputString(String key) throws InputBoxException{
        if(!inputMap.containsKey(key)){
            throw new InputBoxException("The key doesn't exist, Parameter name: " + key);
        }
        return inputMap.get(key);
    }
    public void startInput(String key) throws InputBoxException{
        if(!inputMap.containsKey(key)){
            throw new InputBoxException("The key doesn't exist, Parameter name: " + key);
        }
        transparentEditText.setText(inputMap.get(key));
        int limit = inputLimit.get(key);
        if(limit == -1){
            transparentEditText.setFilters(new InputFilter[] {});
        }else{
            transparentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(limit)});
        }
        transparentEditText.openKeyboard();
        currentKey = key;
    }

    public void draw(Canvas canvas, String key) throws InputBoxException{
        setPaintStyle(key);
        String t = inputMap.get(key);

        canvas.drawRoundRect(x, y, width + x, height + y, cornerRadius, cornerRadius, rectPaint);

        textPaint.getTextBounds(t, 0, t.length(), textBounds);
        canvas.drawText(t, x + leftPadding, y + height/2 - textBounds.exactCenterY(), textPaint);

        if((++currentDelayTime)%delayTime == delayTime-1)
            showCursor = !showCursor;
        if(transparentEditText.isOpened && showCursor && key.equals(currentKey)) {
            canvas.drawRect(x + textBounds.width() + leftPadding + textSize/5,
                    y + height / 2 - textSize / 2,
                    x + textBounds.width() + leftPadding + textSize/5 + 5, y + height / 2 + textSize / 2, textPaint);
        }
    }

    public void checkTouch(float x, float y, String key) throws InputBoxException{
        if(isTouch(x, y, key)){
            touchEvent(key);
        }
    }
    public boolean isTouch(float x, float y, String key) throws InputBoxException{
        if(!inputMap.containsKey(key)){
            throw new InputBoxException("The key doesn't exist, Parameter name: " + key);
        }
        PaintStyle paintStyle = inputStyle.get(key);
        if(x < paintStyle.x || x > paintStyle.x + paintStyle.width){
            return false;
        }
        if(y < paintStyle.y || y > paintStyle.y + paintStyle.height){
            return false;
        }
        return true;
    }
    private void touchEvent(String key) throws InputBoxException{
        this.startInput(key);
    }
}
