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
            if (extras != null) {
                Log.d("TAG", "img is NOT null ");
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                imageCamera = imgBitmap;
                // Notificar al listener (si está definido) que la imagen ha sido capturada
                if (onImageCapturedListener != null) {
                    onImageCapturedListener.onImageCaptured(imageCamera);
                }
            } else {
                Log.d("TAG", "img is null ");
            }
        }
    }

    // Interfaz para el listener
    public interface OnImageCapturedListener {
        void onImageCaptured(Bitmap image);
    }

    private OnImageCapturedListener onImageCapturedListener;

    // Método para establecer el listener
    public void setOnImageCapturedListener(OnImageCapturedListener listener) {
        this.onImageCapturedListener = listener;
    }

    public Bitmap getImageCamera() {
       return imageCamera;
    }
}
