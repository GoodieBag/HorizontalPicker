package com.goodiebag.horizontalpicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kai on 11/02/17.
 */

public class HorizontalPicker extends LinearLayout{
    private final float DENSITY = getContext().getResources().getDisplayMetrics().density;
    //attrs
    private CharSequence values[];
    private ColorStateList textColor;
    private float textSize;

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
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalPicker, defStyleAttr, 0);
            values = array.getTextArray(R.styleable.HorizontalPicker_values);
            textColor = array.getColorStateList(R.styleable.HorizontalPicker_android_textColor);
            if (textColor == null) {
                textColor = ColorStateList.valueOf(0xFF000000);
            }
            float textSize = array.getDimension(R.styleable.HorizontalPicker_android_textSize, -1);

            array.recycle();
        }

    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr) {
        //Linear Layout
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //TextViews
        for(int i = 0; i < values.length; i++){
            TextView tv = new TextView(context);
            if(textSize != -1)
                tv.setTextSize(textSize);
            tv.setTextColor(textColor);
            tv.setText(values[i]);

        }
    }


}
