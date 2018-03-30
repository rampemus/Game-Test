
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
		tiles = new int[40][40];
		
		//tiles[0][8]=3;
		//tiles[1][8]=1;
		//tiles[2][8]=2;
		//tiles[4][8]=3;
		//tiles[4][8]=1;
		//tiles[5][8]=1;
		//tiles[6][8]=4;
		//tiles[7][7]=5;
		//tiles[8][8]=3;
		//tiles[9][8]=1;
		//tiles[10][8]=2;
		
		try{
			ground = new Image("res/ground.png");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
			slopeL = new Image("res/groundSlopeL.png");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
			slopeR = new Image("res/groundSlopeR.png");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
			ice = new Image("res/ice.png");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
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
					//g.drawString(i + "," + j, i*tileSize, j*tileSize);
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
	
	public boolean ground(float x, float y,int height,int width) { //bullets are using this one
		if(isTile(x,y+height/2) || isTile(x,y-height/2)) {
			return true;
		}
		if(isTile(x+width/2,y) || isTile(x-width/2,y)) {
			return true;
		}
		return false;
	}
	
	public boolean ground(float x, float y) {
		if(isTile(x,y)) {
			return true;
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
		default : break;       
		
		}
		
	}
	public boolean isTile(float x, float y){
		
		if(x>0 && y>0) {
			if(tiles[(int)x/tileSize][(int)y/tileSize]>0 ) {
				return true;
			}
		}if(x<0) {
			if(tiles[0][(int)y/tileSize]>0) {
				return true;
			}
		}if(y<0) {
			if(tiles[(int)x/tileSize][0]>0) {
				return true;
			}

		}
		
		return false;
	}
}
