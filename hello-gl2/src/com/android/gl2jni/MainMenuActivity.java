package com.android.gl2jni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainMenuActivity extends Activity{
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        setContentView(R.layout.main_menu_layout);
	    }
	 
	 public void launchGame(View v){
	    	Intent launchShowNumberIntent = new Intent(MainMenuActivity.this, GameActivity.class);
			startActivity(launchShowNumberIntent);
	 }
}
