package com.example.multimediamanager;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MyMultimediaService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayerInService;

    public MyMultimediaService() {
        mediaPlayerInService = new MediaPlayer();
        mediaPlayerInService.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayerInService.setOnPreparedListener(this);
        mediaPlayerInService.setOnCompletionListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AssetFileDescriptor ins = getResources().openRawResourceFd(R.raw.bensoundcountryboy);
        try {
            mediaPlayerInService.setDataSource(ins.getFileDescriptor());
            //this.mediaPlayerInService.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
        mp = null;
    }
}
