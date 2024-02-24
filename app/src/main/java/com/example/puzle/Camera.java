package com.example.puzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;
import java.util.ArrayList;

public class Camera {
    private Activity activity;
    private ArrayList<Bitmap> images;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public Camera(Activity activity) {
        this.activity = activity;
        this.images = new ArrayList<>();
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(activity, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            images.add(imageBitmap);
            Toast.makeText(activity, "Imagen guardada en el ArrayList", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Error al tomar la foto", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }
}
