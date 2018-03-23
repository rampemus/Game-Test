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
		tiles[0][8] = 3;
		tiles[1][8] = 1;
		tiles[2][8] = 2;
		tiles[3][8] = 0;
		tiles[4][8] = 3;
		tiles[5][8] = 1;
		tiles[6][8] = 2;
		tiles[7][8] = 0;
		tiles[8][8] = 3;
		tiles[9][8] = 1;
		tiles[10][8] = 2;
		tiles[11][8] = 4;
		tiles[12][8] = 5;
		try{
			ground = new Image("res/ground.jpg");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
			slopeL = new Image("res/ground.jpg");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
			slopeR = new Image("res/ground.jpg");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
			ice = new Image("res/ground.jpg");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		try{
			grass = new Image("res/ground.jpg");
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
	public int getTile(float x, float y){
		return 0;
	}
}
