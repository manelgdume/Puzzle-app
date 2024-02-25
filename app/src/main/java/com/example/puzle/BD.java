package com.example.puzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BD {
    private final DatabaseReference DBScore = FirebaseDatabase.getInstance().getReference("Games");;

    private void showResults() {

    }

    public BD() {
    }
    public void saveScore(Result result){
        String userId = DBScore.child("Games").push().getKey();
        DBScore.child("Scores").child(userId).setValue(result)
                .addOnSuccessListener(aVoid -> Log.d("MainActivity", "Entrada agregada exitosamente"))
                .addOnFailureListener(e -> Log.e("MainActivity", "Error al agregar entrada", e));

    }
    public void getResults(TableLayout tableLayout, Context context){
        DatabaseReference GamesRef = DBScore.child("Scores");
        ArrayList<Result> results = new ArrayList<>();
        GamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey(); // Obtener la clave (userId)
                    Result result = snapshot.getValue(Result.class); // Obtener el objeto Result
                     if (result != null) {
                         results.add(result);                    }
                }
                showResults(tableLayout,results,context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de lectura
                Log.e("MainActivity", "Error al leer datos", databaseError.toException());
            }

        });
    }
    @SuppressLint("DefaultLocale")
    public void showResults(TableLayout tableLayout, ArrayList<Result> results , Context context){
        int i=0;
        for (Result r : results) {
            TableRow tableRow = new TableRow(context);
            TextView textView = new TextView(context);
            TextView textView1 = new TextView(context);
            textView.setText(String.format("%d. ", i));
            textView1.setText(formatTime(r.getScore()));
            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableLayout.addView(tableRow);
            i++;
        }

    }
    @SuppressLint("DefaultLocale")
    private String formatTime(long milisegundos) {
        long minutos = (milisegundos / 1000) / 60;
        long segundos = (milisegundos / 1000) % 60;

        return String.format("%02d:%02d", minutos, segundos);
    }
}
