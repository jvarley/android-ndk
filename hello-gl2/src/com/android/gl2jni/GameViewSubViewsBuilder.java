package com.android.gl2jni;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GameViewSubViewsBuilder {

	Context context;
	private RelativeLayout topLevelView;
	
	Button zoomIn;
	Button zoomOut;
	Button resetShip;
	
	GameEngineView gameEngineView;
	
	TextView launchPowerTextView;
	TextView launchThetaTextView;
	TextView scoreTextView;
	

	public GameViewSubViewsBuilder(Context context) {
		this.context = context;
		
		topLevelView = new RelativeLayout(context);

		this.zoomIn = createNewZoomInButton();
		this.zoomOut = createNewZoomOutButton();
		this.resetShip = createResetShipButton();

		
		gameEngineView = new GameEngineView(context,scoreTextView);
		this.launchPowerTextView = createNewLaunchPowerTextView();
		this.launchThetaTextView = createNewLaunchThetaTextView();
		this.scoreTextView = createNewScoreTextView();
		gameEngineView.setBackgroundResource(R.drawable.space);
		gameEngineView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				
				switch (event.getAction()){
				case MotionEvent.ACTION_UP:
					gameEngineView.fire();
					break;
					
				case MotionEvent.ACTION_DOWN:
					gameEngineView.setScreenTouched(true);
					break;
					
				case MotionEvent.ACTION_MOVE:
					gameEngineView.updateFirePosition(x,y);
					launchPowerTextView.setText("Power: " + String.valueOf(gameEngineView.getLaunchPower()));
					launchThetaTextView.setText("Theta: " + String.valueOf(gameEngineView.getLaunchTheta()));
					break;
				}
				return true;
			}
		});
		
		setResetButtonLayoutParams();
		setZoomInButtonLayoutParams();
		setZoomOutButtonLayoutParams();
		setLaunchPowerLayoutParams();
		setThetaLayoutParams();
		setScoreLayoutParams();
		
		
		
		topLevelView.addView(gameEngineView);
		topLevelView.addView(zoomIn);
		topLevelView.addView(zoomOut);
		topLevelView.addView(resetShip);
		topLevelView.addView(launchPowerTextView);
		topLevelView.addView(launchThetaTextView);
		topLevelView.addView(scoreTextView);
		
	}


	private void setScoreLayoutParams() {
		LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.BELOW, 10002);
		this.scoreTextView.setLayoutParams(params2);
	}
	

	private void setThetaLayoutParams() {
		LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.BELOW, 10001);
		this.launchThetaTextView.setLayoutParams(params2);
		this.launchThetaTextView.setId(10002);
	}
	
	private void setLaunchPowerLayoutParams() {
		LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.launchPowerTextView.setId(10001);
		this.launchPowerTextView.setLayoutParams(params2);
	}



	private void setResetButtonLayoutParams() {
		LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		this.resetShip.setLayoutParams(params2);
	}
	
	private void setZoomInButtonLayoutParams() {
		LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		this.zoomIn.setLayoutParams(params2);
	}

	private void setZoomOutButtonLayoutParams() {
		LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params1.addRule(RelativeLayout.LEFT_OF, 101);
		this.zoomOut.setLayoutParams(params1);
	}
	

	private TextView createNewLaunchPowerTextView() {
		TextView velTextView = new TextView(context);
		velTextView.setText("Power: " + gameEngineView.getLaunchPower());
		return velTextView;
	}
	
	private TextView createNewLaunchThetaTextView() {
		TextView velTextView = new TextView(context);
		velTextView.setText("Theta: " + gameEngineView.getLaunchTheta());
		return velTextView;
	}
	
	private TextView createNewScoreTextView() {
		TextView velTextView = new TextView(context);
		velTextView.setText("Score: " + gameEngineView.getScore());
		return velTextView;
	}


	


	private Button createNewZoomOutButton() {
		Button b = new Button(context);
		b.setText("z out");
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gameEngineView.setZoom(0.5);
			}
		});
		return b;
	}


	private Button createResetShipButton() {
		Button b = new Button(context);
		b.setText("reset");
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//GL2JNILib.reset();
				//gameEngineView = new GameEngineView(context);
				gameEngineView.init(context);
			}
		});
		return b;
	}


	private Button createNewZoomInButton() {
		Button b = new Button(context);
		b.setText("z in");
		b.setId(101);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gameEngineView.setZoom(2);
			}
		});
		return b;
	}


	public View getTopLevelView() {
		return topLevelView;
	}


}
