package com.android.gl2jni;


import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class ScoreScreenActivity extends Activity{
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        setContentView(R.layout.score_screen_layout);
	    }
}
