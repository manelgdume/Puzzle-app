package com.example.puzle;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AudioIntentService extends Service {

    private MediaPlayer mp;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        AudioIntentService getService() {
            return AudioIntentService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.musica);
        mp.start();
        Log.d("Tag","Servei iniciat");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String operacio = intent.getAction();
            switch (operacio) {
                case "inici":
                    mp.start();
                    Log.d("TAG", "Ha començat");

                    break;
                case "pausa":
                    mp.pause();
                    break;
                default:
                    break;
            }
        }
        else {
            Log.d("TAG","Error");
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
        }
    }
}
