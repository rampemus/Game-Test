package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

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
		width = 58;
		height = 31;
		hitBox = new Rectangle(defx, defy, width, height); // fix this
		xMaxSpeed = 0.3f;
		friction = 0.01f;
		jumpStrength = 0.5f;
		elasticity = 0.2f;
		hp = 1000;
		
		try {
			blank = new Image ("res/blank.png");
			ft1 = new Image ("res/Flare_Tank1.png");
			ft2 = new Image ("res/Flare_Tank2.png");
			ftb1 = new Image ("res/Flare_Tank_b1.png");
			ftb2 = new Image ("res/Flare_Tank_b2.png");
			ftb3 = new Image ("res/Flare_Tank_b3.png");
			ftb4 = new Image ("res/Flare_Tank_b4.png");
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
		
		weapons.add(new Weapon(Weapon.getWeapons().get(7)));
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
		currentWeapon = weapons.get(1);
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
				Random r = new Random();
				int i = r.nextInt(10)+1;
				if (i<8) {
					o.add(new Item((int)this.getX(),(int)this.getY(),Collect.FLAMETHROWER_AMMO));
				}
				if (i==8) {
					o.add(new Item((int)this.getX(),(int)this.getY(),Collect.HP));
				}
				Flame_tank.stop();
				o.remove(this);
			}
			// Walks left
			if ((((Character) o.get(0)).getX() > this.getX() -500) && ((Character) o.get(0)).getX() < this.getX() && alive&&canSeeCharacter((Character)o.get(0),m)){
				if ((((Character) o.get(0)).getY() > this.getY() -50) && ((Character) o.get(0)).getY() < this.getY() +50){
					if (checkMap(m,-36,36)){
						walkLeft(delta);
					}
				}
			}
			// Shoots left
			if ((((Character) o.get(0)).getX() > this.getX() -300) && ((Character) o.get(0)).getX() < this.getX() && alive&&canSeeCharacter((Character)o.get(0),m)){
				if ((((Character) o.get(0)).getY() > this.getY() -50) && ((Character) o.get(0)).getY() < this.getY() +50){
					shoot(o, (int)this.getX()-30,(int)this.getY()-5,(int)((Character) o.get(0)).getX(), (int)this.getY());
				}
			}
			if ((((Character) o.get(0)).getX() < this.getX() +500) && ((Character) o.get(0)).getX() > this.getX() && alive&&canSeeCharacter((Character)o.get(0),m)){
				if ((((Character) o.get(0)).getY() > this.getY() -50) && ((Character) o.get(0)).getY() < this.getY() +50){
					if (checkMap(m,36,36)){
						walkRight(delta);
					}
				}
			}
			//Shoots right
			if ((((Character) o.get(0)).getX() < this.getX() +300) && ((Character) o.get(0)).getX() > this.getX() && alive&&canSeeCharacter((Character)o.get(0),m)){
				if ((((Character) o.get(0)).getY() > this.getY() -50) && ((Character) o.get(0)).getY() < this.getY() +50){
					shoot(o,(int)this.getX()+30,(int)this.getY()-5,(int)((Character) o.get(0)).getX(), (int)this.getY());
					
				}
			}
			Flame_tank.update(delta);
		}
	
	
	public void display(Graphics g) {
		super.display(g);
		Flame_tank.getCurrentFrame().getFlippedCopy(lookingRight, false).draw(this.getX()-width/2-3,this.getY()-height/2-34 );
	}
}
