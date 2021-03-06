package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
/**
 * These flyign enemis follow the player around while avoiding shooting.
 * @author Tommi Heikkinen
 *
 */
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
	int cooldown = 0;
	boolean evade = false;
	int evasionT = 0;
	/**
	 * Creates the character to certain coordinates and creates all necessary animations etc.
	 * @param x x-coordinate of player
	 * @param y y-coordinate of player
	 */
	public Dragonling_Drone (int defx, int defy) {
		super(defx, defy);
		width = 37;
		height = 22;
		hitBox = new Rectangle(defx, defy, width, height);
		xMaxSpeed = 0.80f;
		xAcceleration = 0.0107f;
		friction = 0.01f;
		jumpStrength = 0.9f;
		elasticity = 0.2f;
		hp = 500;
		airborne = true;
		
		try {
			blank = new Image ("res/blank.png");
			d1 = new Image ("res/Dragoling_Drone1.png");
			d2 = new Image ("res/Dragoling_Drone2.png");
			d3 = new Image ("res/Dragoling_Drone3.png");
			d4 = new Image ("res/Dragoling_Drone4.png");
			da1 = new Image ("res/Dragoling_Drone_a1.png");
			da2 = new Image ("res/Dragoling_Drone_a2.png");
			da3 = new Image ("res/Dragoling_Drone_a3.png");
			da4 = new Image ("res/Dragoling_Drone_a4.png");
			dd1 = new Image ("res/Dragoling_Drone_d1.png");
			dd2 = new Image ("res/Dragoling_Drone_d2.png");
			dd3 = new Image ("res/Dragoling_Drone_d3.png");
			dd4 = new Image ("res/Dragoling_Drone_d4.png");
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
		Dragonling_drone = new Animation(Dradrone,300,true);
		
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
	}
	/**
	 * updates the character on the playground. Super.update-has all the kinematics and common stuff with character
	 * Knows how to avoid shooting and chaces player while he is in his sight
	 * @param o oList of the gamestate
	 * @param m Map of the gamestate
	 * @param delta How many steps of millisecs to be taken
	 */
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		//basic animation
		if (Dragonling_drone.getFrame()==4 && alive) {
			Dragonling_drone.setCurrentFrame(0);
		}
		if (hp<=0 && alive) {
			Dragonling_drone.setCurrentFrame(10);
			Dragonling_drone.setSpeed(2);
			alive = false;
		}
		if (Dragonling_drone.getFrame()==14) {
			Random r = new Random();
			int i = r.nextInt(10);
			if (i == 8) {
				o.add(new Item((int)this.getX(),(int)this.getY(),Collect.HP_LARGE));
			}
			if (i == 9) {
				o.add(new Item((int)this.getX(),(int)this.getY(),Collect.HP));
			}
			Dragonling_drone.stop();
			o.remove(this);
		}
		//evasion
		if (((Character) o.get(0)).isShooting() && alive){
			if (((Player)o.get(0)).getLineOfFire().intersects(hitBox)) {
				evade = true;
				evasionT = 150;
			}
		}
		if (evade == true) {
			if (m.isTile(this.getX(), this.getY()-20)) {
				descend(delta*10);
			}else {
				ascend(delta*10);
			}
			evasionT = evasionT-delta;
			if (evasionT <= 0) {
				evade = false;
			}
		} 
		//attacks
		if (shootCooldown>0) {
			shootCooldown-= delta;
		}
		if(hitBox.intersects(((Collider)o.get(0)).getHitbox()) && shootCooldown<= 0 && alive) {
			Sounds.bite.play(1, 0.15f);
		    ((Collider)o.get(0)).takeDamage(100);
		    shootCooldown = 300;
		}

		//attacks to the left
		if((((Character)o.get(0)).getX()<this.getX())&&(((Character)o.get(0)).getX()>this.getX()-600) && alive&&canSeeCharacter((Character)o.get(0),m,700)) {
			if (m.isTile(this.getX(), this.getY()+20)) {
				ascend(delta*3);
			}
			if (((Character)o.get(0)).getY()<this.getY()){
				ascend(delta*5);
			}else {
				descend(delta*5);
			}
			walkLeft(delta);
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
		
		//attacking to the right
		if((((Character)o.get(0)).getX()>this.getX())&&(((Character)o.get(0)).getX()<this.getX()+600) && alive&&canSeeCharacter((Character)o.get(0),m,700)) {
			if (m.isTile(this.getX(), this.getY()+20)) {
				ascend(delta*3);
			}
			if (((Character)o.get(0)).getY()<this.getY()){
				ascend(delta*5);
			}else {
				descend(delta*5);
			}
			walkRight(delta);
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
		Dragonling_drone.update(delta);
	}
	/**
	 * Draws the drone
	 * @param g Ghraphics object of the gamestate
	 */
	public void display(Graphics g) {
		super.display(g);
		Dragonling_drone.getCurrentFrame().getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2-7,this.getY()-height/2-20 );
	}
}
