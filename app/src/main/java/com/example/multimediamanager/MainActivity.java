package com.example.multimediamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private MediaPlayer player;

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
        final Button btnService = findViewById(R.id.activity_main__btn__service);
        btnService.setOnClickListener(this);

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);


        //option 2
        AssetFileDescriptor ins = getResources().openRawResourceFd(R.raw.bensoundcountryboy);
        try {
            player.setDataSource(ins.getFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setOnPreparedListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.activity_main__btn__play:
                if (player.isPlaying()) {
                    player.stop();
                }
                player.prepareAsync();
                break;
            case R.id.activity_main__btn__stop:
                if (player.isPlaying()) {
                    player.stop();
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

                break;
                default:

        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
