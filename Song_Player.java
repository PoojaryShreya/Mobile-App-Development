package com.example.musica_listentillthehorizon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Musicplayer extends AppCompatActivity {
    Bundle songExtraData;
    ImageView prev,play,next;
    int position;
    SeekBar seekbar;
    static MediaPlayer mMediaPlayer;
    TextView songname;
    ArrayList<File> musicList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer);

        prev = findViewById(R.id.prev);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        seekbar = findViewById(R.id.seekbar);
        songname = findViewById(R.id.songname);

        if (mMediaPlayer!=null)
        {
            mMediaPlayer.stop();
        }
        Intent intent = getIntent();
        songExtraData = intent.getExtras();

        musicList = (ArrayList)songExtraData.getParcelableArrayList("songsList");
        position = songExtraData.getInt("position",0);

        initializeMusicPlayer(position);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < musicList.size()-1)
                {
                    position++;
                }
                else
                {
                    position = 0;
                }
                initializeMusicPlayer(position);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position<=0)
                {
                    position = musicList.size();
                }
                else
                {
                    position++;
                }
                initializeMusicPlayer(position);
            }
        });
    }

    private void initializeMusicPlayer(int position) {
        if (mMediaPlayer!=null && mMediaPlayer.isPlaying())
        {
            mMediaPlayer.reset();
        }
        String name =musicList.get(position).getName();
        songname.setText(name);

        Uri uri = Uri.parse(musicList.get(position).toString());
        mMediaPlayer = MediaPlayer.create(this,uri);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekbar.setMax(mMediaPlayer.getDuration());
                play.setImageResource(R.drawable.pause);
                mMediaPlayer.start();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.play);
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.play);

                int currentPosition = position;
                if (currentPosition < musicList.size()-1)
                {
                    currentPosition++;
                }
                else
                {
                    currentPosition =0;
                }
                initializeMusicPlayer(currentPosition);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                {
                    seekBar.setProgress(progress);
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer!=null)
                {
                    try {
                        if (mMediaPlayer.isPlaying())
                        {
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(100);
                        }
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            seekbar.setProgress(msg.what);
        }
    };
    private void play(){
        if (mMediaPlayer!=null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            play.setImageResource(R.drawable.play);
        }
        else
        {
            mMediaPlayer.start();
            play.setImageResource(R.drawable.pause);
        }
    }

}
