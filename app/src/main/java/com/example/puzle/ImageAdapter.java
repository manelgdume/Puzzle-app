package com.example.puzle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private Bitmap[] images;
    private int imageWidth;
    private int numRows;
    private int emptyPosition;
    private GridView gridView;
    public ImageAdapter(Context context, Bitmap[] images, int imageWidth, int numRows, GridView gridView) {
        this.context = context;
        this.images = images;
        this.imageWidth = imageWidth;
        this.numRows = numRows;
        this.emptyPosition = -1; // La posición vacía inicialmente es la última
    }

    @Override
    public int getCount() {
        return images.length;
    }
    public Bitmap[] getImages() {
        return images ;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(0, 0, 0, 0); // Eliminar cualquier relleno adicional
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(images[position]);

        // Manejar clics en las imágenes
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("TAG", emptyPosition + " " + position);
                if (emptyPosition == -1) {
                    emptyPosition = position;
                    draw(position , imageView);
                } else if (emptyPosition == position) {
                    undraw(position , imageView);
                    emptyPosition = -1;
                } else {
                    moveImage(position, imageView);
                    emptyPosition = -1;
                }
            }
        });
        return imageView;
    }
    //draw the border
    private void draw(int position, ImageView imageView) {
        Bitmap temp = images[position];
        Bitmap borderedBitmap = Bitmap.createBitmap(temp.getWidth(), temp.getHeight(), temp.getConfig());
        Canvas canvas = new Canvas(borderedBitmap);
        canvas.drawBitmap(temp, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        canvas.drawRect(0, 0, temp.getWidth(), temp.getHeight(), paint);
        imageView.setImageBitmap(borderedBitmap);

    }
    //undraw the border
    private void undraw(int position, ImageView imageView) {
        Bitmap temp = images[position];
        imageView.setImageBitmap(temp);
    }
    //move image in the grid
    private void moveImage(int position,ImageView imageView) {
            Bitmap temp = images[position];
            //images[position] = images[emptyPosition];
            images[position] =  images[emptyPosition] ;
            images[emptyPosition] = temp;
            emptyPosition = position;
            notifyDataSetChanged(); // Notificar al adaptador sobre el cambio en los datos
            MediaPlayer mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.keyboard);
            mediaPlayer.setVolume(200,200);
            mediaPlayer.start();
    }

}