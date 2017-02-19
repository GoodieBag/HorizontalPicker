package com.goodiebag.horizontalpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kai on 11/02/17.
 */

public class HorizontalPicker extends LinearLayout implements View.OnTouchListener {
    private final float DENSITY = getContext().getResources().getDisplayMetrics().density;
    @DrawableRes
    private int backgroundSelector = R.drawable.selector_background;

    @ColorRes
    private int colorSelector = R.color.selector_tv;
    private int textSize = 12;

    private int selectedIndex;

    private int itemHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int itemWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int itemMargin = 20;

    private List<PickerItem> items = new ArrayList<>();


    //Listeners
    OnSelectionChangeListener changeListener;

    public OnSelectionChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(OnSelectionChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public HorizontalPicker(Context context) {
        this(context, null);
    }

    public HorizontalPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs, defStyleAttr);
        this.setGravity(Gravity.CENTER);
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {

        textSize *= DENSITY;
        itemMargin *= DENSITY;
        selectedIndex = -1;

        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalPicker, defStyleAttr, 0);
            backgroundSelector = array.getResourceId(R.styleable.HorizontalPicker_backgroundSelector, backgroundSelector);
            colorSelector = array.getResourceId(R.styleable.HorizontalPicker_textColorSelector, colorSelector);
            textSize = array.getDimensionPixelSize(R.styleable.HorizontalPicker_textSize, textSize);
            itemHeight = array.getDimensionPixelSize(R.styleable.HorizontalPicker_itemHeight, itemHeight);
            itemWidth = array.getDimensionPixelSize(R.styleable.HorizontalPicker_itemWidth, itemWidth);
            itemMargin = array.getDimensionPixelSize(R.styleable.HorizontalPicker_itemMargin, itemMargin);
            array.recycle();
        }

    }

    private void initViews() {
        removeAllViews();
        this.setOrientation(HORIZONTAL);
        this.setOnTouchListener(this);
        LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
        TextView textView;
        ImageView imageView;
        for (PickerItem pickerItem : items) {
            if (pickerItem.hasDrawable()) {
                imageView = new ImageView(getContext());
                imageView.setImageResource(pickerItem.getDrawable());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                initStyle(imageView);
                this.addView(imageView);
            } else {
                if (pickerItem.getText() != null) {
                    textView = new TextView(getContext());
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(pickerItem.getText());
                    initStyle(textView);
                    this.addView(textView);
                }
            }
        }
    }

    private void initStyle(View view) {
        LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
        view.setLayoutParams(params);
        view.setBackgroundResource(backgroundSelector);
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            ((TextView) view).setTextColor(getResources().getColorStateList(colorSelector));
        }
    }

    private void initStyles() {
        for (int i = 0; i < getChildCount(); i++) {
            initStyle(getChildAt(i));

        }
    }

    public void setItems(List<PickerItem> items) {
        this.items = items;
        initViews();
        selectChild(-1);
    }

    public void setItems(List<PickerItem> items, int selectedIndex) {
        setItems(items);
        selectChild(selectedIndex);
    }

    public List<PickerItem> getItems() {
        return items;
    }

    private void selectChild(int index) {
        if (selectedIndex != index) {
            selectedIndex = -1;
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setSelected(i == index);
                if (i == index) {
                    selectedIndex = index;
                }
            }

            if (changeListener != null)
                changeListener.onItemSelect(this, selectedIndex);
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

                for (int i = 0; i < getChildCount(); i++) {
                    v = getChildAt(i);
                    v.getHitRect(hitRect);
                    if (hitRect.contains(x, y)) {
                        selectChild(i);
                        break;
                    }
                }
                break;
            default:
                break;
        }

        return true;
    }

    public void setSelectedIndex(int selectedIndex) {
        selectChild(selectedIndex);

    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public PickerItem getSelectedItem() {
        return items.get(selectedIndex);
    }

    @DrawableRes
    public int getBackgroundSelector() {
        return backgroundSelector;
    }

    public void setBackgroundSelector(@DrawableRes int backgroundSelector) {
        this.backgroundSelector = backgroundSelector;
        initStyles();
    }

    @ColorRes
    public int getColorSelector() {
        return colorSelector;
    }

    public void setColorSelector(@ColorRes int colorSelector) {
        this.colorSelector = colorSelector;
        initStyles();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        initStyles();
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
        initStyles();
    }

    public int getItemMargin() {
        return itemMargin;
    }

    public void setItemMargin(int itemMargin) {
        this.itemMargin = itemMargin;
        initStyles();
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
        initStyles();
    }

    public interface PickerItem {
        String getText();

        @DrawableRes
        int getDrawable();

        boolean hasDrawable();
    }

    public static class TextItem implements PickerItem {
        private String text;

        public TextItem(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public int getDrawable() {
            return 0;
        }

        @Override
        public boolean hasDrawable() {
            return false;
        }
    }

    public static class DrawableItem implements PickerItem {
        @DrawableRes
        private int drawable;

        public DrawableItem(@DrawableRes int drawable) {
            this.drawable = drawable;
        }

        @Override
        public String getText() {
            return null;
        }

        @DrawableRes
        public int getDrawable() {
            return drawable;
        }

        @Override
        public boolean hasDrawable() {
            return true;
        }
    }

    public interface OnSelectionChangeListener {
        void onItemSelect(HorizontalPicker picker, int index);
    }


}
