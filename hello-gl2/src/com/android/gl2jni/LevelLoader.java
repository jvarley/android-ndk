package com.android.gl2jni;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LevelLoader {
	
	private Context context;
	private Game game;
	
	int spaceManX = 100;
	int spaceManY = 200;
	int spaceManR = 25;
	int spaceManM = 10;
	
	int moonX = 100;
	int moonY = 400;
	int moonR = 100;
	int moonM = 100;

	public LevelLoader(Context context, Game game){
		this.context= context;
		this.game = game;
	}
	
	public void loadLevel(int i){

		
		if(i ==1){

		}
		else if(i ==2){
			createPlanet(400,300,150,100,R.drawable.planet1);
		}
		else if(i ==3){
			createPlanet(1300,300,150,500,R.drawable.planet1);
			createPlanet(500,1500,150,500,R.drawable.mercury);
		}
		else if(i ==4){
			createPlanet(400,300,50,100,R.drawable.planet1);
		}
		
		createSpaceMan(spaceManX,spaceManY,spaceManR,spaceManM);
		createEndMoon(moonX,moonY,moonR,moonM);
		game.threshold = moonR + spaceManR;
	}
	
	private void createPlanet(int x,int y,int radius,int mass, int bitmapId) {
		Bitmap unscaledPlanet = BitmapFactory.decodeResource(context.getResources(),bitmapId);
		Bitmap planetimage = Bitmap.createScaledBitmap( unscaledPlanet,radius*2,radius*2, false);
		
		SimpleSpriteTile planet1 = new SimpleSpriteTile(planetimage);
		planet1.setXPosInRealSpace(x);
		planet1.setYPosInRealSpace(y);
		GL2JNILib.addBody(planet1.xPosInRealSpace,planet1.yPosInRealSpace,radius,mass);
		game.planets.add(planet1);
		game.sprites.add(planet1);
	}

	private void createSpaceMan(int x, int y, int radius, int mass) {
		Bitmap unscaledHunterImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.spaceship);
		Bitmap image = Bitmap.createScaledBitmap( unscaledHunterImage, radius*2,radius*2, false);
		game.spaceman = new SimpleSpriteTile(image);
		game.spaceman.setXPosInRealSpace(x);
		game.spaceman.setYPosInRealSpace(y);
		game.sprites.add(game.spaceman);
		GL2JNILib.addSpaceMan(game.spaceman.xPosInRealSpace, game.spaceman.yPosInRealSpace,radius,mass);
	}
	
	private void createEndMoon(int x, int y, int radius, int mass) {
		Bitmap moonImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.moon);
		Bitmap image = Bitmap.createScaledBitmap( moonImage, radius*2,radius*2, false);
		game.endMoon = new SimpleSpriteTile(image);
		game.endMoon.setXPosInRealSpace(x);
		game.endMoon.setYPosInRealSpace(y);
		game.sprites.add(game.endMoon);
		GL2JNILib.addEndMoon(game.endMoon.xPosInRealSpace, game.endMoon.yPosInRealSpace, radius,mass);
	}
}
