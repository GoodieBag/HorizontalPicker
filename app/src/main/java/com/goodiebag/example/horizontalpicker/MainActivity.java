package com.goodiebag.example.horizontalpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goodiebag.horizontalpicker.HorizontalPicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HorizontalPicker picker = (HorizontalPicker) findViewById(R.id.picker);
        HorizontalPicker.OnSelectionChangeListener listener = new HorizontalPicker.OnSelectionChangeListener() {
            @Override
            public void onItemSelect(int index) {
                Log.d("selectedInt", "" + index);
            }

            @Override
            public void onItemSelect(int index, String text) {
                Log.d("selected", text + " : " + index);

            }
        };
        picker.setChangeListener(listener);
      /*  LinearLayout main = (LinearLayout) findViewById(R.id.activity_main);
        HorizontalPicker picker2 = new HorizontalPicker(this);
        picker2.setBackgroundSelector(R.drawable.selector_background_example);
        picker2.setValues(new CharSequence[]{"S1","S2","S3"});
        picker2.setTextSize(25);
        picker2.setItemHeight(100);
        picker2.setItemWidth(100);
        picker2.setItemMargin(10);
        picker2.prepareView(this);
        main.addView(picker2);*/

    }
}
