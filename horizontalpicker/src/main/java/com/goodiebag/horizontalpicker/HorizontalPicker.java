package com.goodiebag.horizontalpicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kai on 11/02/17.
 */

public class HorizontalPicker extends LinearLayout implements View.OnTouchListener {
    private final float DENSITY = getContext().getResources().getDisplayMetrics().density;
    //attrs
    private CharSequence values[];
    @DrawableRes
    private int backgroundSelector = R.drawable.selector_background;
    private int colorSelector = R.drawable.selector_tv;
    private int textSize = 12;

    private List<TextView> tvList =  new ArrayList<>();

    public HorizontalPicker(Context context) {
        this(context,null);
    }

    public HorizontalPicker(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr);
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if(attrs != null){
            textSize *= DENSITY;
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalPicker, defStyleAttr, 0);
            values = array.getTextArray(R.styleable.HorizontalPicker_values);
            backgroundSelector = array.getResourceId(R.styleable.HorizontalPicker_backgroundSelector, backgroundSelector);
            colorSelector = array.getResourceId(R.styleable.HorizontalPicker_textColorSelector, colorSelector);
            textSize = (int) array.getDimension(R.styleable.HorizontalPicker_textSize, textSize);

            array.recycle();
        }

    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr) {
        //Linear Layout
        this.setOrientation(HORIZONTAL);
        //this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setOnTouchListener(this);
        //TextViews
        for(int i = 0; i < values.length; i++){
            TextView tv = new TextView(context);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
            tv.setTextSize(textSize);
            tv.setTextColor(getResources().getColorStateList(colorSelector));
            tv.setBackgroundResource(backgroundSelector);
            tv.setGravity(Gravity.CENTER);
            tv.setText(values[i]);
            this.addView(tv);
            tvList.add(tv);
        }

    }

    public void selectOneAndDeselectTheRest(View view){
        for(int i = 0 ; i < values.length ; i++){
            tvList.get(i).setSelected(view == (tvList.get(i)));
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                Rect hitRect = new Rect();
                View v;
                for (int i = 0; i < tvList.size(); i++) {
                    v = this.getChildAt(i);
                    v.getHitRect(hitRect);
                    if (hitRect.contains(x, y)) {
                        selectOneAndDeselectTheRest(v);
                        Log.d("touched", "gotView");
                        break;
                    }
                }
                Log.d("touched", "here");
                break;
            default:
                break;
        }

        return true;
    }
}
