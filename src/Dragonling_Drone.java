import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class Dragonling_Drone extends Character implements Visible, Active {
	Image blank;
	Image d1;
	Image d2;
	Image d3;
	Image d4;
	Image da1;
	Image da2;
	Image da3;
	Image da4;
	Image dd1;
	Image dd2;
	Image dd3;
	Image dd4;
	Image[] Dradrone = new Image[14];
	Animation Dragonling_drone;
	boolean alive = true;

	public Dragonling_Drone (int defx, int defy) {
		super(defx, defy);
		hitBox = new Rectangle(defx, defy, 58, 35); // fix this
		xMaxSpeed = 0.6f;
		friction = 0.01f;
		jumpStrength = 0.5f;
		elasticity = 0.2f;
		hp = 500;
		
		try {
			blank = new Image ("/res/blank.png");
			d1 = new Image ("/res/Dragoling_Drone1.png");
			d2 = new Image ("/res/Dragoling_Drone2.png");
			d3 = new Image ("/res/Dragoling_Drone3.png");
			d4 = new Image ("/res/Dragoling_Drone4.png");
			da1 = new Image ("/res/Dragoling_Drone_a1.png");
			da2 = new Image ("/res/Dragoling_Drone1_a2.png");
			da3 = new Image ("/res/Dragoling_Drone1_a3.png");
			da4 = new Image ("/res/Dragoling_Drone1_a4.png");
			dd1 = new Image ("/res/Dragoling_Drone1_d1.png");
			dd2 = new Image ("/res/Dragoling_Drone1_d2.png");
			dd3 = new Image ("/res/Dragoling_Drone1_d3.png");
			dd4 = new Image ("/res/Dragoling_Drone1_d4.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dradrone[0] = d1;
		Dradrone[1] = d2;
		Dradrone[2] = d3;
		Dradrone[3] = d4;
		Dradrone[4] = d4;
		Dradrone[5] = da1;
		Dradrone[6] = da2;
		Dradrone[7] = da3;
		Dradrone[8] = da4;
		Dradrone[9] = dd1;
		Dradrone[10] = dd2;
		Dradrone[11] = dd3;
		Dradrone[12] = dd4;
		Dradrone[13] = blank;
		Dragonling_drone = new Animation(Dradrone,200,true);
		
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
	}
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
	}
	public void display(Graphics g) {
		super.display(g);
		Dragonling_drone.draw(this.getX()-width/2-14,this.getY()-height/2-3 );
	}
}
