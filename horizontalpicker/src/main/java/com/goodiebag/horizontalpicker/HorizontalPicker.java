package com.goodiebag.horizontalpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private int drawableIcons[];
    @DrawableRes
    private int backgroundSelector = R.drawable.selector_background;
    private int colorSelector = R.drawable.selector_tv;
    private int textSize = 12;
    private String selectedItem;
    private int index;

    private List<TextView> tvList =  new ArrayList<>();
    private List<ImageView> ivList = new ArrayList<>();

    private boolean isIcon = false;
    private int iconHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    //Listeners
    OnSelectionChangeListener changeListener;

    public OnSelectionChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(OnSelectionChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public HorizontalPicker(Context context) {
        this(context,null);
    }

    public HorizontalPicker(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs, defStyleAttr);
        if(isIcon){
            initIvViews(context, attrs, defStyleAttr);
        }else {
            initTvViews(context, attrs, defStyleAttr);
        }
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        if(attrs != null){
            textSize *= DENSITY;
            iconHeight *= DENSITY;
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalPicker, defStyleAttr, 0);
            backgroundSelector = array.getResourceId(R.styleable.HorizontalPicker_backgroundSelector, backgroundSelector);
            colorSelector = array.getResourceId(R.styleable.HorizontalPicker_textColorSelector, colorSelector);
            textSize = (int) array.getDimension(R.styleable.HorizontalPicker_textSize, textSize);
            iconHeight = (int) array.getDimension(R.styleable.HorizontalPicker_icon_height, iconHeight);
            values = array.getTextArray(R.styleable.HorizontalPicker_values);
            isIcon = array.getBoolean(R.styleable.HorizontalPicker_icons, isIcon);
            if(isIcon) {
                drawableIcons = new int[values.length];
                for(int i = 0; i < values.length; i++)
                drawableIcons[i] = getResources().getIdentifier(values[i].toString(), "drawable", context.getPackageName());
            }
            array.recycle();
        }

    }

    private void initTvViews(Context context, AttributeSet attrs, int defStyleAttr) {
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

    private void initIvViews(Context context, AttributeSet attrs, int defStyleAttr){
        //Linear Layout
        this.setOrientation(HORIZONTAL);
        //this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setOnTouchListener(this);
        //ImageViews
        for(int i = 0; i < values.length; i++){
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(new LinearLayout.LayoutParams(0, iconHeight,1.0f));
            iv.setBackgroundResource(backgroundSelector);
            iv.setImageResource(drawableIcons[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER);
            this.addView(iv);
            ivList.add(iv);
        }

    }

    public void selectOneAndDeselectTheRest(View view){
        if(!isIcon) {
            for (int i = 0; i < values.length; i++) {
                TextView tvSelected = tvList.get(i);
                tvSelected.setSelected(view == (tvSelected));
                if(tvSelected.isSelected()){
                    selectedItem = tvSelected.getText().toString();
                    index = i;
                    changeListener.onItemSelect(index);
                    changeListener.onItemSelect(index,selectedItem);
                }

            }
        }else{
            for (int i = 0; i < values.length; i++) {
                ImageView ivSelected = ivList.get(i);
                ivSelected.setSelected(view == (ivList.get(i)));
                if(ivSelected.isSelected()){
                    index = i;
                    changeListener.onItemSelect(index);
                }
            }
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
                if(!isIcon) {
                    for (int i = 0; i < tvList.size(); i++) {
                        v = this.getChildAt(i);
                        v.getHitRect(hitRect);
                        if (hitRect.contains(x, y)) {
                            selectOneAndDeselectTheRest(v);
                            Log.d("touched", "gotView");
                            break;
                        }
                    }
                }else{
                    for (int i = 0; i < ivList.size(); i++) {
                        v = this.getChildAt(i);
                        v.getHitRect(hitRect);
                        if (hitRect.contains(x, y)) {
                            selectOneAndDeselectTheRest(v);
                            Log.d("touched", "gotView");
                            break;
                        }
                    }
                }
                Log.d("touched", "here");
                break;
            default:
                break;
        }

        return true;
    }

    public String getSelectedItem(){
        if(!isIcon) {
            return selectedItem;
        }else{
            return " ";
        }
    }

    public int getIndex(){
        return index;
    }

    public interface OnSelectionChangeListener{
        void onItemSelect(int index);
        void onItemSelect(int index, String text);
    }
}
