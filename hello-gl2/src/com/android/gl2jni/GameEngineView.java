package com.android.gl2jni;

/**
 *
 */

import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


public class GameEngineView extends View {
	SimpleSpriteTile st;

	GameLoop gameloop;
	private class GameLoop extends Thread
	{
		private volatile boolean running=true;
		public void run()
		{
			while(running)
			{
				try{
					TimeUnit.MILLISECONDS.sleep(1);
					postInvalidate();
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

	private void init(Context context)
	{
		Bitmap unscaledHunterImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.been_hit_e0000);
		Bitmap image = Bitmap.createScaledBitmap( unscaledHunterImage, 50,50, false);
		
		st = new SimpleSpriteTile(image, context);
		gameloop = new GameLoop();
		gameloop.run();

	}


	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		//st.setXpos(10);
		st.setYpos(1);

		st.draw(canvas);
		gameloop.start();

	}

}
