package com.android.gl2jni;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class SimpleSpriteTile extends Drawable {
	Bitmap bitmap;
	private Context context; 
	int xPos = 0;
	int yPos = 10;
	
	public SimpleSpriteTile(Bitmap image, Context context) {
		super();
		this.bitmap = image;
		this.context = context;
	}

	
	@Override
	public void draw(Canvas canvas) {

		Rect dest = new Rect(0+xPos, 0+yPos, 50+xPos,50+yPos);
		Rect rclip = new Rect(0, 0, 50,50);
		canvas.drawBitmap(bitmap, xPos,yPos, null);
		
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}


	public void setXpos(int i) {
		xPos = i;
		
	}


	public void setYpos(int i) {
		yPos += i;
		
	}

}
