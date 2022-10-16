package com.mobcomp.spoony;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class AudioService extends Service {

    private final IBinder audioBinder = new AudioBinder();
    private MediaPlayer bgm;

    public class AudioBinder extends Binder {
        AudioService getService() {
            return AudioService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return audioBinder;
    }

    @Override
    public void onDestroy() {
        if (bgm != null){
            bgm.stop();
            bgm.release();
        }
        super.onDestroy();
    }

    public void start() {
        bgm = MediaPlayer.create(getApplicationContext(), R.raw.game_bgm);
        bgm.start();
    }
}