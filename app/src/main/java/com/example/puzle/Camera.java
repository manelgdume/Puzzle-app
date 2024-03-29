package com.example.puzle;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Camera {
    private Activity activity;
    private Bitmap imageCamera;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public Camera(Activity activity) {
        this.activity = activity;
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "onActivityResult");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imageCamera = imgBitmap;
        }
    }
    public Bitmap getImageCamera() {
       return imageCamera;
    }
}
