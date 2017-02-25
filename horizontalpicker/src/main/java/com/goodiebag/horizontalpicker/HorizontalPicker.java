/*
MIT License

Copyright (c) 2017 GoodieBag

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */


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
 * This class implements a horizontal picker views for android.
 * It can be used as a widget in android to select an item from a list of items.
 * It is extended from a LinearLayout and it is responsive to touch as well as scroll.
 * Supports DrawableItem(s) as well as TextItem(s) as items for the picker (supports mixture of both as well).
 * Also monitors the selection change event using listeners.
 * Date : 11/02/17
 * @author  Krishanu
 * @author  Pavan
 * @author  Koushik
 */
public class HorizontalPicker extends LinearLayout implements View.OnTouchListener {
    private final float DENSITY = getContext().getResources().getDisplayMetrics().density;
    /**
     * List of attributes with their default values.
     * They can be set either from XML or using their respective setters.
     */
    @DrawableRes
    private int backgroundSelector = R.drawable.selector_background;
    @ColorRes
    private int colorSelector = R.color.selector_tv;
    private int textSize = 12;
    private int selectedIndex=-1;
    private int itemHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int itemWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int itemMargin = 20;

    private List<PickerItem> items = new ArrayList<>();
    OnSelectionChangeListener changeListener;

    /**
     * Constructor
     * @param context
     */
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

    /**
     * A method to initialise the attributes from the XML or the default values.
     * textSize and itemMargin are multiplied with DENSITY to maintain consistency of the views across various device resolutions.
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {

        textSize *= DENSITY;
        itemMargin *= DENSITY;
        selectedIndex = -1;
        /**
         * Getting values of the attributes from the XML.
         * The default value of the attribute is retained if it is not set from the XML or setters.
         */
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

    /**
     * A method to initialise the views.
     * The LayoutParams listeners are initialised in this method.
     * All the items are iterated to check if the current item is a DrawableItem or a TextItem and it takes respective action.
     * This mechanism supports a mixture of DrawableItem(s) as well as TextItem(s).
     */
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

    /**
     * A method to style each view in the picker.
     * The already initialised LayoutParams is set to the view here and in case of a TextItem,
     * extra measures such as text size and color is taken care of.
     * @param view
     */
    private void initStyle(View view) {
        LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemHeight);
        view.setLayoutParams(params);
        view.setBackgroundResource(backgroundSelector);
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            ((TextView) view).setTextColor(getResources().getColorStateList(colorSelector));
        }
    }

    /**
     * Used to style all the children after an attribute change is made (mostly called from setters).
     */
    private void initStyles() {
        for (int i = 0; i < getChildCount(); i++) {
            initStyle(getChildAt(i));
        }
    }

    /**
     * An interface which should be implemented by all the Item classes.
     * The picker only accepts items in the form of PickerItem.
     */
    public interface PickerItem {
        String getText();

        @DrawableRes
        int getDrawable();

        boolean hasDrawable();
    }

    /**
     * A PickerItem which supports text.
     */
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

    /**
     * A PickerItem which supports drawables.
     */
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

    /**
     * Interface for the onItemSelect event.
     */
    public interface OnSelectionChangeListener {
        void onItemSelect(HorizontalPicker picker, int index);
    }

    /**
     * Monitors the touch event.
     * If the action is ACTION_DOWN or ACTION_MOVE, the LinearLayout is traversed to get the hitRect of each child.
     * Each child hitRect is checked to see if it contains x,y touch co-ordinates.
     * If it does, selectChild(index) method is called.
     * @param view
     * @param motionEvent
     * @return
     */
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

    /**
     * Selects the child at a given index and de-selects the rest.
     * @param index
     */
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

    /**
     * A method to set the picker items.
     * No item is selected by default.
     * @param items
     */
    public void setItems(List<PickerItem> items) {
        this.items = items;
        initViews();
        selectChild(-1);
    }

    /**
     * A method to set the picker items.
     * The selectedIndex is selected by default.
     * @param items
     * @param selectedIndex
     */
    public void setItems(List<PickerItem> items, int selectedIndex) {
        setItems(items);
        selectChild(selectedIndex);
    }

    /**
     * Gets the picker items.
     * @return
     */
    public List<PickerItem> getItems() {
        return items;
    }

    /**
     * Getters and setters.
     */

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

    public OnSelectionChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(OnSelectionChangeListener changeListener) {
        this.changeListener = changeListener;
    }

}
