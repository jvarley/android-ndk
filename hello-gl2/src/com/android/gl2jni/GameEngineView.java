package com.android.gl2jni;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class GameEngineView extends View {
	public Game game;
	public AlertDialog alert;

	GameLoop gameloop;
	
	double zoomScale = 1;
	double maxZoomScale = 2;
	double minZoomScale = .125;
	
	public LinkedList<Integer> pathX;
	public LinkedList<Integer> pathY;
	public LinkedList<Integer> scaledPathX;
	public LinkedList<Integer> scaledPathY;

	private Context context;
	
	private int launchPower;
	private double launchTheta;
	
	private int fingerPositionOnScreenX;
	private int fingerPositionOnScreenY;
	private boolean screenTouched = false;
	private int score = 0;
	private TextView scoreTextView;

	private class GameLoop extends Thread
	{
		private volatile boolean running=true;
		
		public void run()
		{
			while(running)
			{
				try{
					TimeUnit.MILLISECONDS.sleep(1);

					if (game.won){
						if (alert==null || !alert.isShowing()){
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setMessage("Level " + Constants.level + " Complete!")
							.setCancelable(false)
							.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									Constants.level += 1;
									init(context);
									dialog.dismiss();
								}
							})
							.setNegativeButton("Play Again", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									init(context);
									dialog.dismiss();
								}
							});
							alert = builder.create();
							alert.show();
							
							safeStop();
						}
						

					}else if (game.lost){
						if (alert==null || !alert.isShowing()){
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setMessage("Level " + Constants.level + " Lost!")
							.setCancelable(false)
							.setPositiveButton("try again", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									init(context);
									dialog.dismiss();
								}
							});
							alert = builder.create();
							alert.show();
							
							safeStop();
						}
						

					}
					else{
						game.step();
						postInvalidate();
						score++;
						if (scoreTextView!=null){
							scoreTextView.setText("Score: " + score);
						}
						
					}
					pause();
					
					
				}
				catch(InterruptedException ex)
				{
					running=false;
				}

			}

		}
		public void pause()
		{
			running=false;
		}
		public void start()
		{
			running=true;
			run();
		}
		public void safeStop()
		{
			running=false;
			interrupt();
		}

	}
	public void unload()
	{
		gameloop.safeStop();

	}

	public GameEngineView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GameEngineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}


	public GameEngineView(Context context, TextView scoreTextView) {
		super(context);
		this.scoreTextView = scoreTextView;
		init(context);
	}

	public void init(Context context)
	{
		this.context = context;
		this.fingerPositionOnScreenX = (int) (this.getWidth()/2.0);
		this.fingerPositionOnScreenY = (int) (this.getHeight()/2.0);
		this.screenTouched = false;
		game = new Game(context);
		gameloop = new GameLoop();
		gameloop.run();
		
	}

	@Override
	protected void onDraw(Canvas canvas) {

		for(SimpleSpriteTile sprite : game.getSprites()){
			sprite.setViewXOffset(400-game.spaceman.getXPosInScreenSpace());
			sprite.setViewYOffset(240-game.spaceman.getYPosInScreenSpace());
		}
		for(SimpleSpriteTile sprite : game.getSprites()){
			sprite.draw(canvas);
		}
		if(!game.fired && screenTouched ){
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			float x1 = fingerPositionOnScreenX;
			float y1 =  fingerPositionOnScreenY;
			float x2 = (float) ((this.getWidth()/2.0) + game.spaceman.scaledBitmap.getWidth()/2.0);
			float y2 = (float) ((this.getHeight()/2.0) + game.spaceman.scaledBitmap.getHeight()/2.0);
			canvas.drawLine(x1,y1,x2,y2,paint);
		}
		
		if(game.pathX.size() > game.scaledPathX.size()){
			game.scaledPathX.addLast((int) (game.pathX.getLast()*this.zoomScale));
			game.scaledPathY.addLast((int) (game.pathY.getLast()*this.zoomScale));
		}
		
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		for(int i = 0; i < game.pathX.size(); i++){
			float x1 = game.spaceman.viewXOffsetToCenterShip + (game.scaledPathX.get(i));
			float y1 = game.spaceman.viewYOffsetToCenterShip + (game.scaledPathY.get(i));
			float x2 = x1+1;
			float y2 = y1+1;
			canvas.drawLine(x1,y1,x2,y2,paint);
		}

		gameloop.start();
	}

	public void setZoom(double scale) {
		if((this.zoomScale * scale <= this.maxZoomScale) && (this.zoomScale * scale >= this.minZoomScale)){
			this.zoomScale *=scale;
			updateZooms();
		}
	}
	private void updateZooms() {
		for(SimpleSpriteTile sprite : game.getSprites()){
			sprite.setscaledBitmap(this.zoomScale);
		}
		for(int i = 0; i< game.scaledPathX.size() ;i ++){
			game.scaledPathX.set(i, (int) (game.pathX.get(i)*this.zoomScale));
			game.scaledPathY.set(i, (int) (game.pathY.get(i)*this.zoomScale));
		}
	}

	public void fire() {
		if(!game.fired){
			int launchPowerY = (int) (Constants.launchPowerScalingFactor*launchPower * Math.sin(launchTheta));
			int launchPowerX = (int) (Constants.launchPowerScalingFactor*launchPower * Math.cos(launchTheta));
			GL2JNILib.fire(launchPowerX,launchPowerY);
			game.fired = true;
		}
	}

	public void updateFirePosition(int x, int y) {
		if(!game.fired){
			
			this.fingerPositionOnScreenX = x;
			this.fingerPositionOnScreenY = y;
			
			int centerX = (int)(this.getWidth()/2.0 + game.spaceman.scaledBitmap.getWidth()/2.0);
			int centerY = (int)(this.getHeight()/2.0  + game.spaceman.scaledBitmap.getHeight()/2.0);
			
			launchTheta = Math.atan2(centerY - y,centerX - x);
			game.spaceman.setTheta(Math.toDegrees(launchTheta) + 45);
			
			int xsquared = (centerX -x)*(centerX - x);
			int ysquared = (centerY -y)*(centerY - y);
			
			double distance = Math.sqrt(xsquared + ysquared);
			double maxDistance = Math.sqrt(240*240+400*400);
			launchPower = (int) (2.0*distance/maxDistance * 100);
			if(launchPower > 100){
				launchPower =100 ;
			}
		}
	}

	public int getLaunchPower() {
		return launchPower;
	}
	
	public double getLaunchTheta() {
		return launchTheta;
	}

	public void setScreenTouched(boolean b) {
		this.screenTouched = b;
		
	}

	public int getScore() {
		return score ;
	}
}
