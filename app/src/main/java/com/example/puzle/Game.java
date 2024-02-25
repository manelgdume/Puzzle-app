package com.example.puzle;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

public class Game {
    private Bitmap[] solution;
    private BD bd = new BD();
    long tiempoInicial = System.currentTimeMillis();

    public Game(Bitmap[] solution) {
        this.solution = solution;
    }
    public boolean verifySolution(Bitmap[] answer){
        for(int i = 0; i< answer.length; i++){
            if(answer[i]!=solution[i]){
                return false;
            }
        }
        long tiempo_final = System.currentTimeMillis()-tiempoInicial ;
        Log.d("TAG", String.valueOf(formatTime(tiempo_final)));
        Result r = new Result(tiempo_final);
        bd.saveScore(r);
        return true;
    }
    private void saveSolution(String time){

    }
    @SuppressLint("DefaultLocale")
    private String formatTime(long milisegundos) {
        long minutos = (milisegundos / 1000) / 60;
        long segundos = (milisegundos / 1000) % 60;

        return String.format("%02d:%02d", minutos, segundos);
    }
}
