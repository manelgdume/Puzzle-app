package com.example.puzle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzle.ImageAdapter;
import com.example.puzle.R;

import java.util.ArrayList;
import java.util.Collections;

public class PuzzleActivity extends AppCompatActivity {

    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;
    private final static int IMAGE_WIDTH = 400;
    private static Bitmap[] chunkedImages;
    private static ArrayList<Button> buttons;
    private ImageAdapter adapter;
    private ImageView backArrow;
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        init();
        shuffle();
        setBitmaps();;
        setButtonClickListener();
        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuzzleActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        Switch swMusic = findViewById(R.id.swMusica);
        swMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try{
                Intent intent = new Intent(PuzzleActivity.this, AudioIntentService.class);
                if (isChecked) {
                    intent.setAction("inici");
                } else {
                    intent.setAction("pausa");
                }

                startService(intent);
            }catch (Exception e){
                Log.d("TAG", String.valueOf(e));
            }
        });

    }
    //  Start the game and get the images
    private void init() {
        buttons = new ArrayList<>();
        chunkedImages = new Bitmap[DIMENSIONS];

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ironman);

        int chunkWidth = image.getWidth() / COLUMNS;
        int chunkHeight = image.getHeight() / COLUMNS;
        int count = 0;
        for (int y = 0; y < COLUMNS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                chunkedImages[count] = Bitmap.createBitmap(image, x * chunkWidth, y * chunkHeight, chunkWidth, chunkHeight);
                count++;
            }
        }
        game = new Game(chunkedImages);
    }
    // Order randomly the images
    private void shuffle() {
        ArrayList<Integer> list = new ArrayList<>();
        Bitmap[] imgAux = new Bitmap[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            imgAux[i] = chunkedImages[list.get(i)];
        }

        chunkedImages = imgAux;
    }

    // Set images into grid
    private void setBitmaps() {
        GridView gridView = findViewById(R.id.grid_view);
        // This adapter help us connect the images with the grid
        adapter = new ImageAdapter(this, chunkedImages, IMAGE_WIDTH, DIMENSIONS / COLUMNS,gridView);
        gridView.setAdapter(adapter);
    }

    private void setButtonClickListener() {
        //Go back Button
        Button resetButton = findViewById(R.id.back_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PuzzleActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        // Check solution button
        Button checkButton = findViewById(R.id.check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.verifySolution(adapter.getImages())){
                    Intent intent = new Intent(PuzzleActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    TextView message = findViewById(R.id.message);
                    message.setText("Answer incorrect");
                }
            }
        });
    }
}