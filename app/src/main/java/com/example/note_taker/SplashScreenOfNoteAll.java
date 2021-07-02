package com.example.note_taker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreenOfNoteAll extends AppCompatActivity {

    private ProgressBar mprogressbar;
    private int progress;
    private TextView mtextview;
    private Typeface mtypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_of_note_all);

        mprogressbar = findViewById(R.id.progressbarofsplashscreen);
        mtextview = findViewById(R.id.splashtextid);

        mtypeface = Typeface.createFromAsset(getAssets(),"kaushanscript/KaushanScriptRegular.otf");
        mtextview.setTypeface( mtypeface);

        Thread mthread = new Thread(new Runnable() {
            @Override
            public void run() {
                activity();
                startNoteAll();
            }
        });

        mthread.start();
    }

    public void activity(){

        for(progress=20; progress<=100; progress = progress+20){
            try {
                Thread.sleep(1000);
                mprogressbar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void  startNoteAll(){
        Intent intent = new Intent(SplashScreenOfNoteAll.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}