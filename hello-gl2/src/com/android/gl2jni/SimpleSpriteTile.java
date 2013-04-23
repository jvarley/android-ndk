package com.android.gl2jni;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class SimpleSpriteTile extends Drawable {
	Bitmap bitmap;
	public Bitmap scaledBitmap;
	int xPosInRealSpace = 0;
	int yPosInRealSpace = 10;
	public int viewXOffsetToCenterShip = 1000;
	public int viewYOffsetToCenterShip =1000;
	int xPosInScreenSpace = 0;
	int yPosInScreenSpace = 0;
	double theta = 45;
	private double scale= 1;
	
	public SimpleSpriteTile(Bitmap image) {
		super();
		this.bitmap = image;
		this.scaledBitmap = image;
	}
	
	@Override
	public void draw(Canvas canvas) {
		
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setDither(true);
        p.setFilterBitmap(true);

        Matrix matrix = new Matrix();       
        matrix.postRotate((float) theta,(float) (scaledBitmap.getWidth()/2),
                (float)(scaledBitmap.getHeight()/2));
        matrix.postTranslate(xPosInScreenSpace + viewXOffsetToCenterShip, yPosInScreenSpace + viewYOffsetToCenterShip);

        canvas.drawBitmap(scaledBitmap, matrix, p);

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


	public void setXPosInRealSpace(int i) {
		xPosInRealSpace = i;
		xPosInScreenSpace = (int) Math.floor(((xPosInRealSpace-scaledBitmap.getWidth()/2.0))*scale);
	}


	public void setYPosInRealSpace(int i) {
		yPosInRealSpace = i;
		yPosInScreenSpace = (int) Math.floor(((yPosInRealSpace-scaledBitmap.getHeight()/2.0))*scale);
	}
	
	public int getXPosInRealSpace() {
		return xPosInRealSpace;
	}

	public int getYPosInRealSpace() {
		return yPosInRealSpace;
	}
	
	public void setViewXOffset(int i) {
		this.viewXOffsetToCenterShip = i;
	}
	
	public int getXPosInScreenSpace() {
		return this.xPosInScreenSpace;
	}


	public int getYPosInScreenSpace() {
		return yPosInScreenSpace;
	}


	public void setViewYOffset(int i) {
		this.viewYOffsetToCenterShip= i;
	}
	
	
	public void setscaledBitmap(double scale) {
		int width = (int) Math.floor(bitmap.getWidth()*scale);
		int height = (int) Math.floor(bitmap.getHeight()*scale);
		this.scale = scale;
		scaledBitmap = Bitmap.createScaledBitmap( bitmap, width,height, false);
		
		xPosInScreenSpace = (int) Math.floor(xPosInRealSpace*scale-scaledBitmap.getWidth()/2.0);
		yPosInScreenSpace = (int) Math.floor(yPosInRealSpace*scale-scaledBitmap.getHeight()/2.0);
	}

	public void setTheta(double i) {
		this.theta = i;
		
	}

}
