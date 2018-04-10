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

public class Flame_Tank extends Character implements Visible, Active {
	Image blank;
	Image ft1;
	Image ft2;
	Image ftb1;
	Image ftb2;
	Image ftb3;
	Image ftb4;
	Image[] ftank = new Image [8];
	Animation Flame_tank;
	boolean alive = true;

	public Flame_Tank(int defx, int defy) {
		super(defx,defy);
		hitBox = new Rectangle(defx, defy, 58, 31); // fix this
		xMaxSpeed = 0.3f;
		friction = 0.01f;
		jumpStrength = 0.5f;
		elasticity = 0.2f;
		hp = 1000;
		
		try {
			blank = new Image ("/res/blank.png");
			ft1 = new Image ("/res/Flare_Tank1.png");
			ft2 = new Image ("/res/Flare_Tank2.png");
			ftb1 = new Image ("/res/Flare_Tank_b1.png");
			ftb2 = new Image ("/res/Flare_Tank_b2.png");
			ftb3 = new Image ("/res/Flare_Tank_b3.png");
			ftb4 = new Image ("/res/Flare_Tank_b4.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ftank[0] = ft1;
		ftank[1] = ft2;
		ftank[2] = ft2;
		ftank[3] = ftb1;
		ftank[4] = ftb2;
		ftank[5] = ftb3;
		ftank[6] = ftb4;
		ftank[7] = blank;
		Flame_tank = new Animation(ftank,200,true);
		
		weapons.add(new Weapon(Weapon.getWeapons().get(3)));
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
		currentWeapon = weapons.get(0);
	}
		public void update(ArrayList<Object> o, Map m, int delta) {
			super.update(o, m, delta);
			if (Flame_tank.getFrame()==2 && alive) {
				Flame_tank.setCurrentFrame(0);
			}
			if (hp<=0 && alive) {
				Flame_tank.setCurrentFrame(3);
				Flame_tank.setSpeed(2);
				alive = false;
			}
			if (Flame_tank.getFrame()==7) {
				Flame_tank.stop();
				o.remove(this);
			}
			if ((((Character) o.get(0)).getX() > this.getX() -300) && ((Character) o.get(0)).getX() < this.getX()){
				if ((((Character) o.get(0)).getY() > this.getY() -50) && ((Character) o.get(0)).getY() < this.getY() +50){
					walkLeft(delta);
					shoot(o, (int)((Character) o.get(0)).getX(), (int)this.getY());
				}
			}
			if ((((Character) o.get(0)).getX() < this.getX() +300) && ((Character) o.get(0)).getX() > this.getX()){
				if ((((Character) o.get(0)).getY() > this.getY() -50) && ((Character) o.get(0)).getY() < this.getY() +50){
					walkRight(delta);
					shoot(o, (int)((Character) o.get(0)).getX(), (int)this.getY());
					//Needs to turn + flamethrower
				}
			}
		}
	
	
	public void display(Graphics g) {
		super.display(g);
		Flame_tank.draw(this.getX()-width/2-14,this.getY()-height/2-16 );
	}
}
