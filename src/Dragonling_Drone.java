import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
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
	Image[] Dradrone = new Image[15];
	Animation Dragonling_drone;
	boolean alive = true;
	boolean notChasing = false;

	public Dragonling_Drone (int defx, int defy) {
		super(defx, defy);
		hitBox = new Rectangle(defx, defy, 58, 35); // fix this
		xMaxSpeed = 0.6f;
		friction = 0.01f;
		jumpStrength = 0.9f;
		elasticity = 0.2f;
		hp = 500;
		
		try {
			blank = new Image ("/res/blank.png");
			d1 = new Image ("/res/Dragoling_Drone1.png");
			d2 = new Image ("/res/Dragoling_Drone2.png");
			d3 = new Image ("/res/Dragoling_Drone3.png");
			d4 = new Image ("/res/Dragoling_Drone4.png");
			da1 = new Image ("/res/Dragoling_Drone_a1.png");
			da2 = new Image ("/res/Dragoling_Drone_a2.png");
			da3 = new Image ("/res/Dragoling_Drone_a3.png");
			da4 = new Image ("/res/Dragoling_Drone_a4.png");
			dd1 = new Image ("/res/Dragoling_Drone_d1.png");
			dd2 = new Image ("/res/Dragoling_Drone_d2.png");
			dd3 = new Image ("/res/Dragoling_Drone_d3.png");
			dd4 = new Image ("/res/Dragoling_Drone_d4.png");
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
		Dradrone[9] = da4;
		Dradrone[10] = dd1;
		Dradrone[11] = dd2;
		Dradrone[12] = dd3;
		Dradrone[13] = dd4;
		Dradrone[14] = blank;
		Dragonling_drone = new Animation(Dradrone,200,true);
		
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
	}
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		
		if (shootCooldown>0) {
			shootCooldown-= delta;
		}
		
		if (Dragonling_drone.getFrame()==4 && alive) {
			Dragonling_drone.setCurrentFrame(0);
		}
		if (hp<=0 && alive) {
			Dragonling_drone.setCurrentFrame(10);
			Dragonling_drone.setSpeed(2);
			alive = false;
		}
		if (Dragonling_drone.getFrame()==14) {
			Dragonling_drone.stop();
			o.remove(this);
		}
		if (((Character) o.get(0)).isShooting()){
			if (((Player)o.get(0)).getLineOfFire().intersects(hitBox)) {
				jump(m);
			}
		}
		if(hitBox.intersects(((Collider)o.get(0)).getHitbox()) && shootCooldown<= 0) {
		      ((Collider)o.get(0)).takeDamage(50);
		      shootCooldown = 1000;
		}
		if(((Character)o.get(0)).getX()>this.getX()) {
			if (notChasing) {
				Dragonling_drone.setCurrentFrame(5);
				notChasing = false;
				//Do chasing
			}
		}else {
			notChasing = true;
			if (Dragonling_drone.getFrame()==9 && alive) {
				Dragonling_drone.setCurrentFrame(0);
			}
		}
		//Frames 5-9 when attacking
		if (Dragonling_drone.getFrame()==9 && alive) {
			Dragonling_drone.setCurrentFrame(5);
		}
	}

	public void display(Graphics g) {
		super.display(g);
		Dragonling_drone.draw(this.getX()-width/2-14,this.getY()-height/2-3 );
	}
}
