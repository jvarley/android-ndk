
package com.android.gl2jni;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

	@Override protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		GameViewBuilder gvb = new GameViewBuilder(this);
		setContentView(gvb.getTopLevelView());
	}

	@Override 
	protected void onPause() {
		super.onPause();
	}

	@Override 
	protected void onResume() {
		super.onResume();
		GameViewBuilder gvb = new GameViewBuilder(this);
		setContentView(gvb.getTopLevelView());
	}
}
