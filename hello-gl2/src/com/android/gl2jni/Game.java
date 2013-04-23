package com.android.gl2jni;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;

public class Game {

	public List<SimpleSpriteTile> sprites;
	public List<SimpleSpriteTile> planets;
	public LinkedList<Integer> pathX;
	public LinkedList<Integer> pathY;
	public LinkedList<Integer> scaledPathX;
	public LinkedList<Integer> scaledPathY;
	public SimpleSpriteTile spaceman;
	public SimpleSpriteTile endMoon;
	public boolean fired = false;
	
	public boolean won = false;
	public boolean lost = false;
	public  double threshold = 0;
	
	
	public Game(Context context){
		GL2JNILib.reset();
		sprites = new LinkedList<SimpleSpriteTile>();
		planets = new LinkedList<SimpleSpriteTile>();
		pathX = new LinkedList<Integer>();
		pathY = new LinkedList<Integer>();
		scaledPathX = new LinkedList<Integer>();
		scaledPathY = new LinkedList<Integer>();
		
		LevelLoader levelLoader = new LevelLoader(context,this);
		levelLoader.loadLevel(Constants.level);
		
	}
	
	public List<SimpleSpriteTile> getSprites() {
		return sprites;
	}

	public void step() {
		
				GL2JNILib.step();

				int[] pos = GL2JNILib.getSpaceManPos();
				int oldX = spaceman.getXPosInRealSpace();
				int oldY = spaceman.getYPosInRealSpace();
				int newX = pos[0];
				int newY = pos[1];
				
				double theta = Math.toDegrees(Math.atan2(oldX- newX, oldY - newY));
				
				spaceman.setXPosInRealSpace(newX);
				spaceman.setYPosInRealSpace(newY);
				
				pathX.add(newX);
				pathY.add(newY);
				
				double distanceToGoal = Math.sqrt(Math.pow((spaceman.xPosInRealSpace - endMoon.xPosInRealSpace),2) + Math.pow((spaceman.yPosInRealSpace - endMoon.yPosInRealSpace),2));
				//spaceman.setTheta(theta);
				if (distanceToGoal < threshold ){
					won = true;
				}
				
				for (SimpleSpriteTile planet:planets){
					distanceToGoal = Math.sqrt(Math.pow((spaceman.xPosInRealSpace - planet.xPosInRealSpace),2) + Math.pow((spaceman.yPosInRealSpace - planet.yPosInRealSpace),2));
					//spaceman.setTheta(theta);
					if (distanceToGoal + 3 < planet.scaledBitmap.getHeight()/2.0 + spaceman.scaledBitmap.getHeight()/2.0){
						lost = true;
					}
				}
	}


	
}
