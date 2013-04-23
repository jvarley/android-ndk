package com.android.gl2jni;

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


public class GameEngineView extends View {
	public Game game;
	public AlertDialog alert;

	GameLoop gameloop;
	
	double zoomScale = 1;
	double maxZoomScale = 2;
	double minZoomScale = .125;
	
	int InitVelX = 0;
	int InitVelY = 0;
	
	

	private Context context;

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
							builder.setMessage("Level " + Constants.level + " Complete")
							.setCancelable(false)
							.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									Constants.level += 1;
									init(context);
									dialog.dismiss();
								}
							});
							alert = builder.create();
							alert.show();
							
							safeStop();
						}
						

					}else{
						game.step();
						postInvalidate();
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

	public GameEngineView(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context)
	{
		this.context = context;
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
		if(!game.fired){
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			float x1 = InitVelX;
			float y1 =  InitVelY;
			float x2 = (float) ((this.getWidth()/2.0) + game.spaceman.scaledBitmap.getWidth()/2.0);
			float y2 = (float) ((this.getHeight()/2.0) + game.spaceman.scaledBitmap.getHeight()/2.0);
			canvas.drawLine(x1,y1,x2,y2,paint);
		}
	

		gameloop.start();
	}

	public void zoom(double scale) {
		if((this.zoomScale * scale <= this.maxZoomScale) && (this.zoomScale * scale >= this.minZoomScale)){
			this.zoomScale *=scale;
			for(SimpleSpriteTile sprite : game.getSprites()){
				sprite.setscaledBitmap(this.zoomScale);
			}
		}
	}
}
