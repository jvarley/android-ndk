package com.android.gl2jni;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ZoomButtonsController;

public class GameViewBuilder {

	Context context;
	private RelativeLayout topLevelView;
	
	Button zoomIn;
	Button zoomOut;
	Button resetShip;
	
	GameEngineView gameEngineView;
	
	TextView velTextView;
	

	public GameViewBuilder(Context context) {
		this.context = context;
		
		topLevelView = new RelativeLayout(context);
		

		this.zoomIn = createNewZoomInButton();
		this.zoomOut = createNewZoomOutButton();
		this.resetShip = createResetShipButton();

		
		

		gameEngineView = new GameEngineView(context);
		this.velTextView = createNewVelTextView();
		gameEngineView.setBackgroundResource(R.drawable.space);
		gameEngineView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				int centerX = (int) (v.getWidth()/2.0);
				int centerY = (int)(v.getHeight()/2.0);
				
				switch (event.getAction()){
				case MotionEvent.ACTION_UP:


					GL2JNILib.fire(centerX-x,centerY-y);
					gameEngineView.game.fired = true;
					break;
					
				case MotionEvent.ACTION_MOVE:
					gameEngineView.InitVelX = x;
					gameEngineView.InitVelY = y;
					
					int xsquared = (centerX -gameEngineView.InitVelX)*(centerX - gameEngineView.InitVelX);
					int ysquared = (centerY -gameEngineView.InitVelY)*(centerY - gameEngineView.InitVelY);
					
					double launchImpulse = Math.sqrt(xsquared + ysquared);
					velTextView.setText("Velocity: " + launchImpulse);
					break;
				}

			

				
				return true;
			}
		});
		
		setResetButtonLayoutParams();
		setZoomInButtonLayoutParams();
		setZoomOutButtonLayoutParams();
		
		
		
		topLevelView.addView(gameEngineView);
		topLevelView.addView(zoomIn);
		topLevelView.addView(zoomOut);
		topLevelView.addView(resetShip);
		topLevelView.addView(velTextView);
		
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
	

	private TextView createNewVelTextView() {
		TextView velTextView = new TextView(context);
		double launchImpulse = Math.sqrt(gameEngineView.InitVelX*gameEngineView.InitVelX + gameEngineView.InitVelY*gameEngineView.InitVelY);
		velTextView.setText("Velocity: " + launchImpulse);
		return velTextView;
	}
	


	private Button createNewZoomOutButton() {
		Button b = new Button(context);
		b.setText("z out");
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gameEngineView.zoom(0.5);
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
				gameEngineView.zoom(2);
			}
		});
		return b;
	}


	public View getTopLevelView() {
		return topLevelView;
	}


}
