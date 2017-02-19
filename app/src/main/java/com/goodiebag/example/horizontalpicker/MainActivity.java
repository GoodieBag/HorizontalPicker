package com.goodiebag.example.horizontalpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.goodiebag.horizontalpicker.HorizontalPicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HorizontalPicker hpText = (HorizontalPicker) findViewById(R.id.hpText);
        HorizontalPicker hpImage = (HorizontalPicker) findViewById(R.id.hpImage);
        HorizontalPicker.OnSelectionChangeListener listener = new HorizontalPicker.OnSelectionChangeListener() {
            @Override
            public void onItemSelect(HorizontalPicker picker, int index) {
                HorizontalPicker.PickerItem selected = picker.getSelectedItem();
                Toast.makeText(MainActivity.this, selected.hasDrawable() ? "Item at " + (picker.getSelectedIndex() + 1) + " is selected" : selected.getText() + " is selected", Toast.LENGTH_SHORT).show();
            }

        };

        List<HorizontalPicker.PickerItem> textItems = new ArrayList<>();
        for(int i=1;i<=15;i++){
            textItems.add(new HorizontalPicker.TextItem("S"+i));
        }
//        textItems.add(new HorizontalPicker.TextItem("S1"));
//        textItems.add(new HorizontalPicker.TextItem("S2"));
//        textItems.add(new HorizontalPicker.TextItem("S3"));
//        textItems.add(new HorizontalPicker.TextItem("S4"));
//        textItems.add(new HorizontalPicker.TextItem("S5"));

        hpText.setItems(textItems,3);

        List<HorizontalPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.icon_led));
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.icon_ir));

        hpImage.setItems(imageItems);
        hpImage.setSelectedIndex(0);

        hpText.setChangeListener(listener);
        hpImage.setChangeListener(listener);

    }
}
