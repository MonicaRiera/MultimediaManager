package com.example.multimediamanager;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.multimediamanager.R;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnPlay = findViewById(R.id.activity_main__btn__play);
        btnPlay.setOnClickListener(this);
        final Button btnStop = findViewById(R.id.activity_main__btn__stop);
        btnStop.setOnClickListener(this);
        final Button btnSound = findViewById(R.id.activity_main__btn__sound);
        btnSound.setOnClickListener(this);
        final Button btnPlayInService = findViewById(R.id.activity_main__btn__service);
        btnPlayInService.setOnClickListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // option 1:
//        mediaPlayer = MediaPlayer.create(this, R.raw.bensoundbrazilsamba);

        // option 2:
        AssetFileDescriptor ins = getResources().openRawResourceFd(R.raw.bensoundpsychedelic);
        try {
            mediaPlayer.setDataSource(ins.getFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main__btn__play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.prepareAsync();
                break;
            case R.id.activity_main__btn__stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                break;
            case R.id.activity_main__btn__sound:
                Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                MediaPlayer mediaPlayerSound = new MediaPlayer();
                mediaPlayerSound.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                try {
                    mediaPlayerSound.setDataSource(this, ringtoneUri);
                    mediaPlayerSound.setOnPreparedListener(this);
                    mediaPlayerSound.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.activity_main__btn__service:
                Intent intent = new Intent(this, MyMultimediaService.class);
                startService(intent);
                break;
            default:
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

}