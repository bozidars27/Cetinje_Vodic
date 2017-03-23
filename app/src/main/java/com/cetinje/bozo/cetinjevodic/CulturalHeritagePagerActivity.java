package com.cetinje.bozo.cetinjevodic;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CulturalHeritagePagerActivity extends AppCompatActivity {

    private Context context;
    private DatabaseHelper db;
    private Intent i;
    private ArrayList<Video> videos;
    private MediaController mediaControls;
    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultural_heritage_pager);
        myVideoView  = (VideoView) findViewById(R.id.culturalHeritagevideoView);
        context = getApplicationContext();
        db = new DatabaseHelper(context);
        i = getIntent();
        File file = getApplicationContext().getFileStreamPath(i.getStringExtra("name"));
        Uri uri = Uri.parse(String.valueOf(file));
        //videos = db.getCulturalHeritageVideos(i.getStringExtra("id"));
        playVideo(uri);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }

    public void playVideo(Uri uri)
    {
        if (mediaControls == null) {
            mediaControls = new MediaController(CulturalHeritagePagerActivity.this);
        }
        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(CulturalHeritagePagerActivity.this);
        // set a title for the progress bar
        progressDialog.setTitle(String.valueOf(uri));
        // set a message for the progress bar
        progressDialog.setMessage("Loading...");
        //set the progress bar not cancelable on users' touch
        progressDialog.setCancelable(false);
        // show the progress bar
        progressDialog.show();
        try {
            //set the media controller in the VideoView
            myVideoView.setMediaController(mediaControls);

            //set the uri of the video to be played
            myVideoView.setVideoURI(uri);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                myVideoView.seekTo(position);
                if (position == 0) {
                    myVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    myVideoView.pause();
                }
            }
        });
    }
}
