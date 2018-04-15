package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


/**
 * Character is walking, jumping and shooting being that also can have an ability to fly.
 * This is mostly used as an abstract for the enemies and is not used because
 * it is not Active or Visible.
 * @author Pasi, Santeri
 */
public class Character extends Collider{
	protected float xAcceleration = 0.005f;  //how fast does character gain maximum speed
	protected int shootCooldown = 0;			//shootCoolDown for guns
	protected int dummyCooldown = 0;			//for aiming purpose
	protected Weapon currentWeapon;			//weapon that the character is holding
	protected ArrayList<Weapon> weapons;		//if character has other weapons they are stored here
	protected boolean lookingRight;			//for animation to keep track if the animation needs to be flipped
	protected Vector2f hotSpot;				//The point where the arm is connected to the body and where the bullets are supposed to be created
	protected int ascendCooldown;			//For AI movement
	protected int descendCooldown;			//For AI movement
	protected Sound jump;					//WHOOPWHOOP!
	
	public Character(int x, int y) {
		super(x,y);
		hotSpot = new Vector2f(0,0);
		
		//set the hitbox right
		height = 64; 
		width = 32;
		hitBox = new Rectangle(0, 0, width, height);
		
		//initialize weapons
		weapons = new ArrayList<>(Arrays.asList(new Weapon(Weapon.getWeapons().get(0))));
		currentWeapon = weapons.get(0);
		
		//WHOOPWHOOP!! (the jumping sound is initialized here)
		try {
			jump = new Sound("res/Jump.ogg");
		} catch(SlickException e) {
			
		}
	}
	
	public void display(Graphics g) {
		super.display(g);
		g.drawString("Weapon: " + currentWeapon.getName(),getX()-70,getY()-height-15); //for debugging to know which weapon we are using
		
		//here will be other display things for character animations in the children classes
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		
		//check all the cooldowns
		if ( shootCooldown > 0) {
			shootCooldown -= delta;
		}
		if ( dummyCooldown > 0) {
			dummyCooldown -= delta;
		}
		
		if (ascendCooldown > 0) {
			ascend(delta);
			ascendCooldown -= delta;
		}
		if (descendCooldown > 0) {
			descend(delta);
			descendCooldown -= delta;
		}
	}
	
	/**
	 * A shooting method that fires a bullet at the cooridnates given, originating from the centre of the Character.
	 * @param oList list of objects in the gamestate
	 * @param x the x-position where the bullet is created
	 * @param y the y-position where the bullet is created
	 * @author Santeri
	 */
	public void shoot(ArrayList<Object> oList, int x, int y) {
		if (shootCooldown <= 0) {
			if((!(currentWeapon.getCount() <= 0)) || currentWeapon.isEnemy()) {
				for(int i = 0; i < currentWeapon.getAmountOfBullets(); i++)
					oList.add(new Bullet((int)this.getX()+(int)hotSpot.getX(), (int)this.getY()+(int)hotSpot.getY(), x, y, currentWeapon)); //shoot from character position plus hotSpot coordinates
				if(currentWeapon.getName().equals("Flamethrower"))
					Sounds.fire.play(1, 0.1f);
				if(currentWeapon.getName().equals("Pump Shotgun"))
					Sounds.shotgun.play(1, 0.1f);
			}
			//change the animation to shooting and so on
			shootCooldown = currentWeapon.getFiringRate();
		}
	}
	
	/**
	 * A shooting method that allows a defined hotspot.
	 * @param oList
	 * @param x
	 * @param y
	 * @param destX xoffset of hotspot
	 * @param destY yoffset of hotspot
	 * @author Santeri
	 */
	public void shoot(ArrayList<Object> oList, int x, int y, int destX, int destY) {
		if (shootCooldown <= 0) {
			if((!(currentWeapon.getCount() == 0)) || currentWeapon.isEnemy()) {
				for(int i = 0; i < currentWeapon.getAmountOfBullets(); i++)
					oList.add(new Bullet(x+(int)hotSpot.getX(), y+(int)hotSpot.getY(), destX, destY, currentWeapon));
				if(currentWeapon.getName().equals("Flamethrower"))
					Sounds.fire.play(1, 0.1f);
				if(currentWeapon.getName().equals("Pump Shotgun"))
					Sounds.shotgun.play(1, 0.1f);
				
			}
			//animaation vaihtaminen ampumiseen jne.
			shootCooldown = currentWeapon.getFiringRate();
		}
	}
	
	/**
	 * A shooting method that can be given an additional boolean for more constant firing. Used by aiming.
	 * @param oList
	 * @param x
	 * @param y
	 * @param kk
	 * @author Santeri
	 */
	public void shoot(ArrayList<Object> oList, int xTarget, int yTarget, boolean kk) {
		if (kk && dummyCooldown <= 0) {
			if((!(currentWeapon.getCount() == 0)) || kk) {
				for(int i = 0; i < currentWeapon.getAmountOfBullets(); i++)
					oList.add(new Bullet((int)this.getX(), (int)this.getY(), xTarget, yTarget, ((Player)(oList.get(0))).getLineOfFire()));
				dummyCooldown = 250;
			}
			//animaation vaihtaminen ampumiseen jne.
		}
	}
	
	/**
	 * For AI
	 * @return true if character is shooting
	 * @author Pasi
	 */
	public boolean isShooting() {
		if ( shootCooldown > currentWeapon.getFiringRate()-50) return true;
		return false;
	}
	
	/**
	 * Walking right and left
	 * @param delta how many steps
	 * @author Pasi
	 */
	public void walkRight(int delta) {
		if ( v.getX() < xMaxSpeed ) {
			v.add(new Vector2f(xAcceleration*delta,0));
		}
		lookingRight = true;
	}
	public void walkLeft(int delta) {
		if ( v.getX() > -xMaxSpeed ) {
			v.add(new Vector2f(-xAcceleration*delta,0));
		}
		lookingRight = false;
	}
	
	/**
	 * Jump if there is now roof above and there is ground underneath characters feet
	 * @param m Map to check where is ground
	 * @author Pasi
	 */
	public void jump(Map m) {
		if ( onGround(m) && !underCeiling(m) && !headCollision(m) ) {
			jumpCooldown = jumpStrength;
			jump.play(1, 0.03f);
		}
		
		if (jumpCooldown > jumpStrength/400 && !headCollision(m)) {
			v.set(v.getX(),-jumpStrength);
		}
	}
	
	/**
	 * For airborne colliders moving method when the character is flying
	 * @author Pasi
	 */
	public void ascend(int delta) {
		if (airborne && v.getY() > -yMaxSpeed) {
			v.set(v.getX(), v.getY()+b.getY()*delta);
		}
	}
	public void ascend(int delta, int millisecs) {
		ascendCooldown = millisecs;
	}
	public void descend(int delta, int millisecs) {
		descendCooldown = millisecs;
	}
	public void descend(int delta) {
		if (airborne && v.getY() < yMaxSpeed ) {
			v.set(v.getX(), v.getY()-b.getY()*delta);
		}
	}
	
	/**
	 * you can make character fly with this. Note that you need to use
	 * ascend and descend -methods to move the character
	 * @param airborne
	 */
	public void setFlying(boolean airborne) {
		this.airborne = airborne;
	}
	
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	/**
	 * Returns true, if this can see character from the parameter
	 * This is a bit slow, so: DO NOT call this method during every single update
	 * if there is more than 100 enemies
	 */
	public boolean canSeeCharacter(Character c, Map m) {
		int steplength = 10; //change this one to 1 if you see any walls that the enemies can see through
		
		//vector magic, stepping every single coordinate between character this to character c
		Vector2f start = new Vector2f(p);
		Vector2f end = new Vector2f(c.getP());
		Vector2f step = new Vector2f(end);
		step.sub(start);
		int distance = (int)step.length();
		int numberOfSteps = distance/steplength;
		step.normalise();
		step.scale(steplength);
		for ( int i = 0; i < numberOfSteps; i++) {
			start.add(step);
			if ( m.ground(start.getX(), start.getY()) ) return false;
		}
		return true;
	}
	
	public boolean canSeeCharacter(Character c, Map m, int maxDistance) {
		int steplength = 10; //change this one to 1 if you see any walls that the enemies can see through
		
		//vector magic, stepping every single coordinate between character this to character c
		Vector2f start = new Vector2f(p);
		Vector2f end = new Vector2f(c.getP());
		Vector2f step = new Vector2f(end);
		step.sub(start);
		int distance = (int)step.length();
		if (distance > maxDistance) {
			return false;
		}
		int numberOfSteps = distance/steplength;
		step.normalise();
		step.scale(steplength);
		for ( int i = 0; i < numberOfSteps; i++) {
			start.add(step);
			if ( m.ground(start.getX(), start.getY()) ) return false;
		}
		return true;
	}
}
