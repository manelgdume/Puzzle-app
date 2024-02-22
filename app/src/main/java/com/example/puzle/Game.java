package com.example.puzle;

import android.graphics.Bitmap;
import android.util.Log;

public class Game {
    private Bitmap[] solution;

    public Game(Bitmap[] solution) {
        this.solution = solution;
    }
    public boolean verifySolution(Bitmap[] answer){
        for(int i = 0; i< answer.length; i++){
            if(answer[i]!=solution[i]){
                return false;
            }
        }
        return true;
    }
}
