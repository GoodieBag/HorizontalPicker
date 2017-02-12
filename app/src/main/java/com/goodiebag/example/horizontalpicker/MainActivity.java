package com.goodiebag.example.horizontalpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    }
}
