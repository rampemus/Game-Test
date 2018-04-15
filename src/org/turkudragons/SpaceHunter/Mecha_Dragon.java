package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
public class Mecha_Dragon extends Character implements Visible, Active {
	
	boolean alive = true;
	boolean active = false;
	boolean activate = false;
	int startup = 3;
	int timer = 0;
	private Sound DragonRoar;
	private Sound Warning;
	Image blank;
	Image ds1;
	Image dr1;
	Image dr2;
	Image dr3;
	Image drm1;
	Image drm2;
	Image drm3;
	Image[] Dragon = new Image[61];
	Animation Dragon_Boss;
	
	public Mecha_Dragon (int defx, int defy) { //(1500,3740) default lokaatio
		super(defx, defy);
		width = 72;
		height = 30;
		hitBox = new Rectangle(defx, defy, width, height);
		xMaxSpeed = 1.01f;
		friction = 0.01f;
		jumpStrength = 0.9f;
		elasticity = 0.2f;
		hp = 50000;
		airborne = true;
		invulnerable = true;
		try {
		dr1 = new Image("res/Mecha_Dragon_w.png");
		dr2 = new Image("res/Mecha_Dragon_w2.png");
		dr3 = new Image("res/Mecha_Dragon_w3.png");
		drm1 = new Image("res/Mecha_Dragon_wm.png");
		drm2 = new Image("res/Mecha_Dragon_wm2.png");
		drm3 = new Image("res/Mecha_Dragon_wm3.png");
		DragonRoar = new Sound("res/DragonRoar.ogg");
		Warning = new Sound("res/Warning.ogg");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dragon[0] = dr1;
		Dragon[1] = dr2;
		Dragon[2] = dr3;
		Dragon[3] = dr1;
		Dragon[4] = dr2;
		Dragon[5] = dr3;
		Dragon[6] = dr1;
		Dragon[7] = dr2;
		Dragon[8] = dr3;
		Dragon[9] = dr1;
		Dragon[10] = dr2;
		Dragon[11] = dr3;
		Dragon[12] = dr1;
		Dragon[13] = dr2;
		Dragon[14] = dr3;
		Dragon[15] = drm1;
		Dragon[16] = drm2;
		Dragon[17] = drm3;
		Dragon[18] = drm1;
		Dragon[19] = drm2;
		Dragon[20] = drm3;
		Dragon[21] = dr1;
		Dragon[22] = dr2;
		Dragon[23] = dr3;
		Dragon[24] = dr1;
		Dragon[25] = dr2;
		Dragon[26] = dr3;
		Dragon[27] = dr1;
		Dragon[28] = dr2;
		Dragon[29] = dr3;
		Dragon[30] = dr1;
		Dragon[31] = dr2;
		Dragon[32] = dr3;
		Dragon[33] = dr1;
		Dragon[34] = dr2;
		Dragon[35] = dr3;
		Dragon[36] = dr1;
		Dragon[37] = dr2;
		Dragon[38] = dr3;
		Dragon[39] = dr1;
		Dragon[40] = dr2;
		Dragon[41] = dr3;
		Dragon[42] = drm1;
		Dragon[43] = drm2;
		Dragon[44] = drm3;
		Dragon[45] = drm1;
		Dragon[46] = drm2;
		Dragon[47] = drm3;
		Dragon[48] = drm1;
		Dragon[49] = drm2;
		Dragon[50] = drm3;
		Dragon[51] = drm1;
		Dragon[52] = drm2;
		Dragon[53] = drm3;
		Dragon[54] = drm1;
		Dragon[55] = drm2;
		Dragon[56] = drm3;
		Dragon[57] = drm1;
		Dragon[58] = drm2;
		Dragon[59] = drm3;
		Dragon[60] = drm3;
		
		Dragon_Boss = new Animation(Dragon,250,true);
		
		weapons.add(new Weapon(Weapon.getWeapons().get(7)));
		weapons.add(new Weapon(Weapon.getWeapons().get(5)));
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
		weapons.get(1).setDamage(7);
		weapons.get(1).setRange(350);
		currentWeapon = weapons.get(1);
	}
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		if (hp<=0) {
			alive = false;
			o.remove(this);
		}
		
		if (!active) {
			if (((Player)o.get(0)).getX() < this.getX() && ((Player)o.get(0)).getX() > this.getX()-500 && alive) {
				Dragon_Boss.start();
				active =true;
				Warning.play(1, 0.35f);
			}else {
				Dragon_Boss.stop();
			}
		}
		//the intro for the boss
		if (Dragon_Boss.getFrame() == 4 && startup >1 && active && alive) {
			Dragon_Boss.setCurrentFrame(0);
			startup = startup -1;
		}else if (Dragon_Boss.getFrame() == 4 && startup >0) {
			DragonRoar.play(1, 0.35f);
			Dragon_Boss.setCurrentFrame(0);
			startup = startup -1;
		}else if (Dragon_Boss.getFrame() == 4 && startup ==0) {
			invulnerable = false;
			activate = true;
		}
		//the action sequence of the boss starts
		// Breath fire and chase
		if (Dragon_Boss.getFrame() <12  && activate && alive) {
			// fire breathe
			if (((Player)o.get(0)).getX()<this.getX()+30) {
				shoot(o, (int)this.getX()-34,(int)this.getY(),(int)this.getX()-60,(int)this.getY()+35);
			}else {
				shoot(o, (int)this.getX()+59,(int)this.getY(),(int)this.getX()+84,(int)this.getY()+25);
			}
			// adjusting height
			if (((Player)o.get(0)).getY()<this.getY()+100) {
				ascend(delta*2);
			}else if (((Player)o.get(0)).getY()>this.getY()+150) {
				descend(delta*2);
			}
			// Adjusting horizontal position when player is on the left
			//turning needs to be fixed
			if (((Player)o.get(0)).getX()<this.getX()-200) {
				walkLeft(delta);
			}else if (((Player)o.get(0)).getX()>this.getX()-100) {
				walkRight(delta);
			}
			// Adjusting horizontal position when player is on the Right
			if (((Player)o.get(0)).getX()>this.getX()+200) {
				walkRight(delta);
			}else if (((Player)o.get(0)).getX()<this.getX()+100) {
				walkLeft(delta);
			}
		}
		// Flies back
		if (Dragon_Boss.getFrame() <15 && Dragon_Boss.getFrame() >=12 && activate && alive) {
			ascend(delta*2);
			if (((Player)o.get(0)).getX()<this.getX()) {
				walkRight(delta);
			} else {
				walkLeft(delta);
			}
		}
		//Shoots missile
		if (Dragon_Boss.getFrame() <21 && Dragon_Boss.getFrame() >=15 && activate && alive) {
			currentWeapon = weapons.get(2);
			shoot(o, (int)this.getX(),(int)this.getY(),(int)((Player)o.get(0)).getX(),(int)((Player)o.get(0)).getY());
		}
		//shoots fire and comes closer
		if (Dragon_Boss.getFrame() <27 && Dragon_Boss.getFrame() >=21 && activate && alive) {
			currentWeapon = weapons.get(1);
			if (((Player)o.get(0)).getX()<this.getX()) {
				walkLeft(delta);
				shoot(o, (int)this.getX()-34,(int)this.getY(),(int)this.getX()-60,(int)this.getY()+25);
			} else {
				walkRight(delta);
				shoot(o, (int)this.getX()+59,(int)this.getY(),(int)this.getX()+84,(int)this.getY()+25);
			}
			if (((Player)o.get(0)).getY()<this.getY()) {
				ascend(delta);
			} else {
				descend(delta);
			}
		}
		//ram
		if (Dragon_Boss.getFrame() <33 && Dragon_Boss.getFrame() >=27 && activate && alive) {
			xAcceleration = 0.0055f;
			if (((Player)o.get(0)).getX()<this.getX()) {
				walkLeft(delta*2);
			} else {
				walkRight(delta*2);
			}
			if (((Player)o.get(0)).getY()<this.getY()) {
				ascend(delta*2);
			} else {
				descend(delta*2);
			}
			if(hitBox.intersects(((Collider)o.get(0)).getHitbox())) {
			    ((Collider)o.get(0)).takeDamage(2);
			}
		}
		//ram
		if (Dragon_Boss.getFrame() <42 && Dragon_Boss.getFrame() >=33 && activate && alive) {
			xAcceleration = 0.005f;
			ascend(delta*2);
			if (((Player)o.get(0)).getX()<this.getX()) {
				walkRight(delta);
			} else {
				walkLeft(delta);
			}	
		}
		timer = timer - delta;
		//The dragon spawns little friends
		if (Dragon_Boss.getFrame() <54 && Dragon_Boss.getFrame() >=42 && activate && alive) {
			DragonRoar.play(1, 0.35f);
			if (timer <0) {
				o.add(new Dragonling_Drone((int)this.getX(),(int)this.getY()));
				timer = 1000;
			}
		}
		//fires missiles
		if (Dragon_Boss.getFrame() <60 && Dragon_Boss.getFrame() >=54 && activate && alive) {
			if (Dragon_Boss.getFrame() <21 && Dragon_Boss.getFrame() >=15 && activate && alive) {
				currentWeapon = weapons.get(2);
				shoot(o, (int)this.getX(),(int)this.getY(),(int)((Player)o.get(0)).getX(),(int)((Player)o.get(0)).getY());
			}
		}
		//loop over
		if (Dragon_Boss.getFrame() == 60 && activate && alive) {
			currentWeapon = weapons.get(1);
		}
		
		Dragon_Boss.update(delta);
	}
	public void display(Graphics g) {
		super.display(g);
		Dragon_Boss.getCurrentFrame().getFlippedCopy(lookingRight, false).draw(this.getX()-width/2-10,this.getY()-height/2-50 );
	}
}
