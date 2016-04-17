package com.example.AddGesture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MyActivity extends Activity {
    EditText editText;
    GestureOverlayView gestureOverlayView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText=(EditText)findViewById(R.id.gesture_name);
        gestureOverlayView=(GestureOverlayView) findViewById(R.id.gesture);
        gestureOverlayView.setGestureColor(Color.RED);
        gestureOverlayView.setGestureStrokeWidth(4);
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlayView, final Gesture gesture) {
                View saveDialog=getLayoutInflater().inflate(R.layout.save,null);
                ImageView imageView=(ImageView)saveDialog.findViewById(R.id.show);
                final EditText getstureName=(EditText)saveDialog.findViewById(R.id.gesture_name);
                Bitmap bitmap=gesture.toBitmap(128,
                        128,10,0xffff0000);
                imageView.setImageBitmap(bitmap);
                new AlertDialog.Builder(MyActivity.this).setView(saveDialog).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GestureLibrary gestureLibrary= GestureLibraries.fromFile("/mnt/sdcard/mygestures");
                        gestureLibrary.addGesture(getstureName.getText().toString(),
                                gesture);
                        gestureLibrary.save();
                    }
                }).setNegativeButton("取消",null).show();
            }
        });
    }
}
