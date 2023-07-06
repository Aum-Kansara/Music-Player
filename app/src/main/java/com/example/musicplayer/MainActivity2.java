package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private Button button;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private TextView songName_View,songDuration_View,seconds;
    private String songName;
    private boolean running=false;
    ImageView imageView;
//    RotateAnimation rotateAnimation;
    private Integer duration;
    private ArrayList songs;
    private int position;
//    Thread rotater;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        rotater=new Thread(){
//            @Override
//            public void run(){
//                rotateAnimation=new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,.5f,RotateAnimation.RELATIVE_TO_SELF,.5f);
//                rotateAnimation.setDuration(10000);
//                rotateAnimation.setRepeatMode(Animation.INFINITE);
//                imageView.startAnimation(rotateAnimation);
//            }
//
//        };
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        button=findViewById(R.id.button);
        seekBar=findViewById(R.id.seekBar);
        songName_View=findViewById(R.id.textView2);
        songName_View.setSelected(true);
        imageView=findViewById(R.id.imageView);
        songDuration_View=findViewById(R.id.textView7);
        seconds=findViewById(R.id.textView6);
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        songs=(ArrayList)bundle.getParcelableArrayList("songList");
        songName=intent.getStringExtra("currentSong");
        songName_View.setText(songName);
        position=intent.getIntExtra("position",0);
        Uri uri= Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(this,uri);
        duration=mediaPlayer.getDuration();
        seekBar.setMax(duration);
        songDuration_View.setText((duration).toString());
        mediaPlayer.start();
        button.setText("Pause");
        running=true;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running) {
                    try {
                        mediaPlayer.pause();
                        button.setText("Play");
                        running = false;
                    }catch (Exception e){}
                }
                else {
                    mediaPlayer.start();
                    button.setText("Pause");
                    running=true;
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mediaPlayer.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();

            }
        });




    }
}