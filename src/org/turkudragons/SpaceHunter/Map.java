package org.turkudragons.SpaceHunter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Map{
	
	private int[][] tiles;
	private int tileSize = 64;
	private Image ground;
	private Image ice;
	private Image grass;
	private Image slopeR;
	private Image slopeL;
	

	public Map(){
		tiles = new int[60][60];
		
		
		try{
			ground = new Image("res/ground.png");
			slopeL = new Image("res/groundSlopeL.png");
			slopeR = new Image("res/groundSlopeR.png");
			ice = new Image("res/ice.png");
			grass = new Image("res/grass.png");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
	}
	
	
	public void display(){
		for (int i = 0; i<tiles.length; i++){
			for (int j=0; j<tiles.length; j++){
				if (tiles[i][j]==1){
					ground.draw(i*tileSize, j*tileSize, tileSize, tileSize);
				}
				else
					if (tiles[i][j]==2){
					slopeR.draw(i*tileSize, j*tileSize, tileSize, tileSize);
					}
					else
						if (tiles[i][j]==3){
							slopeL.draw(i*tileSize, j*tileSize, tileSize, tileSize);
						}
						else
							if (tiles[i][j]==4){
								grass.draw(i*tileSize, j*tileSize, tileSize, tileSize);
							}
							else
								if (tiles[i][j]==5){
									ice.draw(i*tileSize, j*tileSize, tileSize, tileSize);
								}
								
				
			}
		}
	}
	
	public boolean ground(float x, float y) {
		if(isTile(x,y)&&!isSlope(x,y)) {
			return true;
		}else if(isSlope(x,y)) {
			return checkSlope(x,y);
		}
		return false;
	}
	public boolean checkSlope(float x,float y) {
		if(tiles[(int)x/tileSize][(int)y/tileSize]==2 ) {
			float ex =(x%64)*64;
			float ey =(y%64)*64;
			if((y-ey)-(x-ex)<0) {
				return true;
			}
		}
		if(tiles[(int)x/tileSize][(int)y/tileSize]==3 ) {
			float ex =(x%64)*64;
			float ey =(y%64)*64;
			if((ex-x)+(ey-y)>64) {
				return true;
			}
		}
		
		return false;
	}
	
	public void add(int shape,int type,int startx,int starty) {
		switch(shape) {
		
		//ground
		case 1:for(int j=startx;j<startx+10;j++) {
			tiles[j][starty]=type;
		}
		break;
		//platform
		case 2:for(int j=startx;j<startx+3;j++) {
			tiles[j][starty]=type;
		}
		break;
		//slope platform
		//only for ground
		case 3:tiles[startx][starty]=3;
		tiles[startx-1][starty+1]=3;
		tiles[startx+3][starty]=2;
		tiles[startx+4][starty+1]=2;
		for(int j=startx+1;j<startx+3;j++) {
			tiles[j][starty]=type;
		}
		for(int j=startx;j<startx+4;j++) {
			tiles[j][starty+1]=type;
		}
		
		break;
		//wall 
		case 4: for(int j=starty;j>starty-5;j--) {
			             tiles[startx][j]=type;
		              } 
		break;
		//square
		case 5: tiles[startx][starty]=type;
		tiles[startx+1][starty]=type;
		tiles[startx][starty+1]=type;
		tiles[startx+1][starty+1]=type;
		break;
		default : break;       
		
		}
		
	}
	public boolean isSlope(float x, float y) {
		if(x>0 && y>0 && x<3840 && y<3840) {
			if(tiles[(int)x/tileSize][(int)y/tileSize]==2 ||tiles[(int)x/tileSize][(int)y/tileSize]==3) {
				return true;
			}
		}
		return false;
	}
	public boolean isTile(float x, float y){
		
		if(x>0 && y>0&&x<3840 && y<3840) {
			if(tiles[(int)x/tileSize][(int)y/tileSize]>0 ) {
				return true;
			}
		}
		
		return false;
	}
}
