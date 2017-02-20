# Horizontal Picker

 A Horizontal picker library for android which supports both text and icons . :pouting_cat:


## Gradle Dependency

Add this in your root build.gradle file at the end of repositories:
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency : 
```java
dependencies {
	        compile 'com.github.GoodieBag:HorizontalPicker:v1.0'
	}
```
Sync the gradle and that's it! :+1:

## Usage

### XML : 
```xml
<com.goodiebag.horizontalpicker.HorizontalPicker
            android:id="@+id/hpicker"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:textSize="18sp"
            app:itemMargin="5dp"
            app:itemHeight="40dp"
            app:itemWidth="40dp"
            app:backgroundSelector="@drawable/selector_background_example"
            app:textColorSelector="@color/selector_text_view"/>
```

P.S : ```itemWidth``` and ```itemHeight``` are not mandatory. If ignored, it is WRAP_CONTENT by default. <br />
```textColorSelector``` is only applicable when the items are textItems.


### Java : 
```java
 //If your picker needs to have text as items :  
 HorizontalPicker hpText = (HorizontalPicker) findViewById(R.id.picker);
 
 List<HorizontalPicker.PickerItem> textItems = new ArrayList<>();
        for(int i=1;i<=4;i++){
            textItems.add(new HorizontalPicker.TextItem("S"+i));
        }
  hpText.setItems(textItems,3); //3 here signifies the default selected item. Use : hpText.setItems(textItems) if none of the items are selected by default.
  
 //If your picker takes images as items : 
HorizontalPicker hpImage = (HorizontalPicker) findViewById(R.id.hpImage);
 
List<HorizontalPicker.PickerItem> imageItems = new ArrayList<>();
imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.example));
imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.example2));
hpImage.setItems(imageItems);
```
###### Use ```hpText.setSelectedIndex(index)``` to select an item programmatically.
## Listeners : 
A listener exists to monitor the item selection change on slide or on tap.

```java
HorizontalPicker.OnSelectionChangeListener listener = new HorizontalPicker.OnSelectionChangeListener() {
      		@Override
            public void onItemSelect(HorizontalPicker picker, int index) {
                HorizontalPicker.PickerItem selected = picker.getSelectedItem();
                Toast.makeText(MainActivity.this, selected.hasDrawable() ? "Item at " + (picker.getSelectedIndex() + 1) + " is selected" : selected.getText() + " is selected", Toast.LENGTH_SHORT).show();
            }
hpText.setChangeListener(listener);

```

## LICENSE
```
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
```
