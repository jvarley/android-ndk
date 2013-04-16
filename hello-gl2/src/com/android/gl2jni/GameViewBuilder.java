package com.android.gl2jni;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GameViewBuilder {

	Context context;
	private RelativeLayout topLevelView;
	Button fireButton;
	Button incVelButton;
	Button decVelButton;
	TextView velTextView;
	
	public float velocity = 80;

	public GameViewBuilder(Context context) {
		this.context = context;
		
		
		topLevelView = new RelativeLayout(context);
		
		this.fireButton = createFireButton();
		this.incVelButton = createNewIncVelButton();
		this.decVelButton = createNewDecVelButton();
		this.velTextView = createNewVelTextView();
		

		GameEngineView gameEngineView = new GameEngineView(context);
		gameEngineView.setBackgroundResource(R.drawable.been_hit_e0000);
		
		setDecVelButtonLayoutParams();
		setIncVelButtonLayoutParams();

		topLevelView.addView(gameEngineView);
		topLevelView.addView(fireButton);
		topLevelView.addView(incVelButton);
		topLevelView.addView(decVelButton);
		topLevelView.addView(velTextView);
	}

	private void setIncVelButtonLayoutParams() {
		LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		//params2.addRule(RelativeLayout.RIGHT_OF,fireButton);
		incVelButton.setLayoutParams(params2);
	}

	private void setDecVelButtonLayoutParams() {
		LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//params1.addRule(RelativeLayout.RIGHT_OF,fireButton);
		decVelButton.setLayoutParams(params1);
	}

	private TextView createNewVelTextView() {
		TextView velTextView = new TextView(context);
		velTextView.setText("Velocity: " + velocity);
		return velTextView;
	}

	private Button createNewDecVelButton() {

		Button b = new Button(context);
		b.setText("dec");
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				velocity -=1;
				velTextView.setText("Velocity: " + velocity);

			}
		});
		return b;
	}

	private Button createNewIncVelButton() {
		Button b = new Button(context);
		b.setText("inc");
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				velocity += 1;
				velTextView.setText("Velocity: " + velocity);

			}
		});
		return b;
	}

	private Button createFireButton() {
		Button b = new Button(context);
		b.setText("fire");
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GL2JNILib.fire(velocity);
			}
		});
		return b;
	}

	public View getTopLevelView() {
		return topLevelView;
	}


}
