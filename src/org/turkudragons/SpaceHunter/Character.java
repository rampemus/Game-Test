package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Character extends Collider{
	protected float xAcceleration = 0.005f;
	protected int shootCooldown = 0;
	protected int dummyCooldown = 0;
	protected Weapon currentWeapon;
	protected ArrayList<Weapon> weapons;
	protected boolean lookingRight;
	protected Vector2f hotSpot;
	protected int ascendCooldown;
	protected int descendCooldown;
	
	public Character(int x, int y) {
		super(x,y);
		hotSpot = new Vector2f(0,0);
		height = 64; //resize hitbox
		width = 32;
		hitBox = new Rectangle(0, 0, width, height);
		weapons = new ArrayList<>(Arrays.asList(new Weapon(Weapon.getWeapons().get(0))));
		currentWeapon = weapons.get(0);
	}
	
	public void display(Graphics g) {
		super.display(g);
		g.drawString("Weapon: " + currentWeapon.getName(),getX()-70,getY()-height-15);
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		
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
	 * @param oList
	 * @param x
	 * @param y
	 */
	public void shoot(ArrayList<Object> oList, int x, int y) {
		if (shootCooldown <= 0) {
			if((!(currentWeapon.getCount() == 0)) || currentWeapon.isEnemy()) {
				for(int i = 0; i < currentWeapon.getAmountOfBullets(); i++)
					oList.add(new Bullet((int)this.getX(), (int)this.getY(), x, y, currentWeapon));
			}
			//animaation vaihtaminen ampumiseen jne.
			shootCooldown = currentWeapon.getFiringRate();
		}
	}
	
	/**
	 * A shooting method that allows a defined hotspot.
	 * @param oList
	 * @param x
	 * @param y
	 * @param destX
	 * @param destY
	 */
	public void shoot(ArrayList<Object> oList, int x, int y, int destX, int destY) {
		if (shootCooldown <= 0) {
			if((!(currentWeapon.getCount() == 0)) || currentWeapon.isEnemy()) {
				for(int i = 0; i < currentWeapon.getAmountOfBullets(); i++)
					oList.add(new Bullet(x, y, destX, destY, currentWeapon));
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
	 */
	public void shoot(ArrayList<Object> oList, int xTarget, int yTarget, boolean kk) {
		if (kk && dummyCooldown <= 0) {
			if((!(currentWeapon.getCount() == 0)) || kk) {
				for(int i = 0; i < currentWeapon.getAmountOfBullets(); i++)
					oList.add(new Bullet((int)this.getX()+(int)hotSpot.getX(), (int)this.getY()+(int)hotSpot.getY(), xTarget, yTarget, ((Player)(oList.get(0))).getLineOfFire()));
				dummyCooldown = 250;
			}
			//animaation vaihtaminen ampumiseen jne.
		}
	}
	
	public boolean isShooting() {
		if ( shootCooldown > currentWeapon.getFiringRate()-50) return true;
		return false;
	}
	
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
	
	public void jump(Map m) {
		if ( onGround(m) && !underCeiling(m) && !headCollision(m) ) {
			jumpCooldown = jumpStrength;
		}
		
		if (jumpCooldown > jumpStrength/400 && !headCollision(m)) {
			v.set(v.getX(),-jumpStrength);
		}
	}
	
	/**
	 * For airborne colliders moving method when the character is flying
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
	
	public void setFlying(boolean airborne) {
		this.airborne = airborne;
	}
	
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
}
