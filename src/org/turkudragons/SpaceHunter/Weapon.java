package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * 
 * A class needed for the creation of weapons for the game.
 * @author Santeri Loitomaa
 *
 */
public class Weapon{
	
	private String name; // The name of the weapon.
	private int count; // The ammo count of the weapon.
	private int damage; // The damege one bullet does.
	private int firingRate; // The time you need to wait in between shots.
	private int amountOfBullets; // The amount of bullets per shot.
	private float projectileSpeed; // The speed of a bullet.
	private boolean destroyable; // The bullet can be destroyed.
	private boolean guided; // The bullet is homing.
	private int range; // The maximum of tics the bullet is allowed to live.
	private boolean enemy; // The weapon belongs to an enemy.
	private boolean infinite; // The ammo is infinite.
	private static ArrayList<Weapon> ammoTypes; // A list of all possible weapons in the game.
	
	/**
	 * Constructor for a weapon object created from the given values.
	 * @param name
	 * @param count
	 * @param damage
	 * @param firingRate
	 * @param amountOfBullets
	 * @param projectileSpeed
	 * @param destroyable
	 * @param guided
	 * @param range
	 * @param enemy
	 * @param infinite
	 */
	public Weapon(String name, int count, int damage, int firingRate, int amountOfBullets, float projectileSpeed, boolean destroyable, boolean guided,
			int range, boolean enemy, boolean infinite) {
		this.name = name;
		this.count = count;
		this.damage = damage;
		this.firingRate = firingRate;
		this.amountOfBullets = amountOfBullets;
		this.projectileSpeed = projectileSpeed;
		this.destroyable = destroyable;
		this.guided = guided;
		this.range = range;
		this.enemy = enemy;
		this.infinite = infinite;
	}
	
	/**
	 * Creates a copy of a weapon.
	 * @param weapon
	 */
	public Weapon(Weapon weapon) {
		this.name = weapon.getName();
		this.count = weapon.getCount();
		this.damage = weapon.getDamage();
		this.firingRate = weapon.getFiringRate();
		this.amountOfBullets = weapon.getAmountOfBullets();
		this.projectileSpeed = weapon.getProjectileSpeed();
		this.destroyable = weapon.isDestroyable();
		this.guided = weapon.isGuided();
		this.range = weapon.getRange();
		this.enemy = weapon.isEnemy();
		this.infinite = weapon.isInfinite();
	}

	/**
	 * Creates a copy of a weapon with a defined enemy value.
	 * @param weapon
	 * @param enemy
	 */
	public Weapon(Weapon weapon, boolean enemy) {
		this.name = weapon.getName();
		this.count = weapon.getCount();
		this.damage = weapon.getDamage();
		this.firingRate = weapon.getFiringRate();
		this.amountOfBullets = weapon.getAmountOfBullets();
		this.projectileSpeed = weapon.getProjectileSpeed();
		this.destroyable = weapon.isDestroyable();
		this.guided = weapon.isGuided();
		this.range = weapon.getRange();
		this.enemy = enemy;
		this.infinite = weapon.isInfinite();
	}

	/**
	 * Creates all the possible weapons in the game and adds them to an ArrayList.
	 */
	public static void createWeapons() {
		ammoTypes = new ArrayList<Weapon>();
		ammoTypes.add(new Weapon("Pistol", 999, 250, 250, 1, 2.0f, false, false, 9999, false, false));
		ammoTypes.add(new Weapon("Assault Rifle", 250, 100, 100, 1, 2.0f, false, false, 9999, false, false));
		ammoTypes.add(new Weapon("Sniper Rifle", 33, 1000, 1000, 1, 3.0f, false, false, 9999, false, false));
		ammoTypes.add(new Weapon("RPG-Launcher", 25, 1750, 1750, 1, 1.0f, true, false, 9999, false, false));
		ammoTypes.add(new Weapon("Grenade-Launcher", 13, 1750, 1750, 1, 1.0f, true, false, 9999, false, false));
		ammoTypes.add(new Weapon("Guided RPG", 25, 1750, 1750, 1, 1.0f, true, true, 9999, false, false));
		ammoTypes.add(new Weapon("Pump Shotgun", 50, 250, 1250, 5, 2.0f, false, false, 500, false, false));
		ammoTypes.add(new Weapon("Flamethrower", 500, 5, 50, 15, 1.0f, false, false, 250, false, false));
	}
	
	/**
	 * Returns an ArrayList containing every weapon in the game.
	 * @return ArrayList<Weapon> ammoTypes
	 */
	public static ArrayList<Weapon> getWeapons() {
		return ammoTypes;
	}
	
	/**
	 * Getter for the name of the weapon.
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Setter for the name of the weapon.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the ammo count of the weapon.
	 * @return
	 */
	public int getCount() {
		if(!infinite) return count;
		else return 999;
	}
	/**
	 * Setter for the ammo count of the weapon.
	 * @param count
	 */
	public void setCount(int count) {
		if(!infinite) this.count = count;
	}
	
	/**
	 * Getter for the damage of the weapon.
	 * @return
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * Setter for the damage of the weapon.
	 * @param damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Getter for the firing rate of the weapon.
	 * @return
	 */
	public int getFiringRate() {
		return firingRate;
	}
	/**
	 * Setter for the firing rate of the weapon.
	 * @param firingRate
	 */
	public void setFiringRate(int firingRate) {
		this.firingRate = firingRate;
	}
	
	/**
	 * Getter for the amount of bullets per shot of the weapon.
	 * @return
	 */
	public int getAmountOfBullets() {
		return amountOfBullets;
	}
	/**
	 * Setter for the amount of bullets per shot of the weapon.
	 * @param amountOfBullets
	 */
	public void setAmountOfBullets(int amountOfBullets) {
		this.amountOfBullets = amountOfBullets;
	}
	
	/**
	 * Getter for the projectile speed of the bullets of the weapon.
	 * @return
	 */
	public float getProjectileSpeed() {
		return projectileSpeed;
	}
	/**
	 * Setter for the projectile speed of the bullets of the weapon.
	 * @param projectileSpeed
	 */
	public void setProjectileSpeed(float projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
	}
	
	/**
	 * Getter for the destroyability of the bullets of the weapon.
	 * @return
	 */
	public boolean isDestroyable() {
		return destroyable;
	}
	/**
	 * Setter for the destroyability of the bullets of the weapon.
	 * @param destroyable
	 */
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	
	/**
	 * Getter for the guidability of the bullets of the weapon.
	 * @return
	 */
	public boolean isGuided() {
		return guided;
	}
	/**
	 * Setter for the guidability of the bullets of the weapon.
	 * @param guided
	 */
	public void setGuided(boolean guided) {
		this.guided = guided;
	}
	
	/**
	 * Getter for the range of the bullets of the weapon.
	 * @return
	 */
	public int getRange() {
		return range;
	}
	/**
	 * Setter for the range of the bullets of the weapon.
	 * @param arcs
	 */
	public void setRange(int range) {
		this.range = range;
	}
	
	/**
	 * Getter for the infinity of the bullets of the weapon.
	 * @return
	 */
	public boolean isInfinite() {
		return infinite;
	}
	/**
	 * Setter for the infinity of the bullets of the weapon.
	 * @param infinite
	 */
	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}
	
	/**
	 * Getter for the owner of the weapon (Is it an enemy or not?).
	 * @return
	 */
	public boolean isEnemy() {
		return enemy;
	}
	/**
	 * Setter for the owner of the weapon (Is it an enemy or not?).
	 * @param enemy
	 */
	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}
	
}

/**
 * This class is used to create and uphold a bullet object so the shooting can work.
 * @author Santeri Loitomaa
 *
 */
class Bullet implements Active,Visible{
	
	private Vector2f pm; // Location of the mouse.
	private Vector2f p; // Location of the bullet.
	private Vector2f v; // Direction of the bullet.
	private Vector2f g; // Gravity of the bullet.
	Shape hitBox; // Hit box of the bullet.
	private Shape oldLineOfFire; // The direction of the mouse.
	Weapon currentWeapon; // The weapon used for the bullet.
	private int gravityAccelerationCycle; // Used to define how much gravity has to be added.
	private int bulletSpeedSlowerCycle; // Used to slow down slower bullets.
	private int range; // The range of the bullet.
	private boolean destroyed; // The bullet is destroyed.
	private Image texture; // The texture of the bullet.
	private boolean removeKebab; // Is there a reason to remove a rocket from the weapons count.
	private float oldTheta; // The old direction of the bullet.
	
	/**
	 * A constructor for a bullet. It needs the current weapon and coordinates of the shooter and the destination to work.
	 * @param x
	 * @param y
	 * @param destX
	 * @param destY
	 * @param currentWeapon
	 */
	public Bullet(int x, int y, int destX, int destY, Weapon currentWeapon) {
		
		/*if(x == destX && y == destY) {
			destY++;
		}*/
		
		if(currentWeapon.getName().equals("Grenade-Launcher")) {
			hitBox = new Rectangle(0, 0, 9, 9);
			p = new Vector2f(x,y);
			pm = new Vector2f(destX, destY);
			v = new Vector2f(0,0);
			g = new Vector2f(0,0.005f);
			this.currentWeapon = currentWeapon;
			this.gravityAccelerationCycle = 0;
			this.bulletSpeedSlowerCycle = 0;
			try {
				texture = new Image("res/Granade.png");
				Sounds.plop.play(1, 0.1f);
			}catch(SlickException e) {
				
			}
			v.sub(p);
			v.add(pm);
			int i = (int) v.length();
			v.normalise();
			Vector2f force = new Vector2f(v);
			force.scale(0.003f);
			while(i > 1) {
				v.add(force);
				i--;
			}
			texture.rotate((float)v.getTheta());
		}
		else if(currentWeapon.getName().equals("Pistol") || currentWeapon.getName().equals("Assault Rifle") ||
				currentWeapon.getName().equals("Sniper Rifle")) {
			hitBox = new Rectangle(0, 0, 3, 3);
			p = new Vector2f(x,y);
			pm = new Vector2f(destX, destY);
			v = new Vector2f(0,0);
			g = new Vector2f(0,0);
			this.currentWeapon = currentWeapon;
			this.range = currentWeapon.getRange();
			try {
				texture = new Image("res/bullet.png");
			}catch(SlickException e) {
				
			}
			v.sub(p);
			v.add(pm);
			v.normalise();
			texture.rotate((float)v.getTheta());
			if(currentWeapon.getName().equals("Pistol") || currentWeapon.getName().equals("Assault Rifle")) {
				Sounds.bullet.play(1, 0.2f);
			} else {
				Sounds.rifle.play(1, 0.1f);
			}
		}
		else if(currentWeapon.getName().equals("Pump Shotgun") || currentWeapon.getName().equals("Flamethrower")) {
			p = new Vector2f(x,y);
			pm = new Vector2f(destX, destY);
			v = new Vector2f(0,0);
			g = new Vector2f(0,0);
			this.currentWeapon = currentWeapon;
			this.range = currentWeapon.getRange();
			Random r = new Random();
			if(currentWeapon.getName().equals("Flamethrower")) {
				try {
					hitBox = new Rectangle(0, 0, 9, 9);
					texture = new Image("res/Fire.png");
				}catch(SlickException e) {
					
				}
				if(r.nextInt(4) == 0) {
					currentWeapon.setFiringRate(200);
				}
				else {
					currentWeapon.setFiringRate(50);
				}
				
			}
			else {
				try {
					hitBox = new Rectangle(0, 0, 3, 3);
					texture = new Image("res/bullet.png");
				}catch(SlickException e) {
					
				}
			}
			
			v.sub(p);
			v.add(pm);
			int i = r.nextInt(21);
			if(i > 10) {
				v.add(i-10);
			}
			else {
				v.sub(i);
			}
			v.normalise();
			texture.rotate((float)v.getTheta());
		}
		else if(currentWeapon.getName().equals("RPG-Launcher") || currentWeapon.getName().equals("Guided RPG")) {
			hitBox = new Rectangle(0, 0, 9, 9);
			p = new Vector2f(x,y);
			pm = new Vector2f(destX, destY);
			v = new Vector2f(0,0);
			g = new Vector2f(0,0);
			this.currentWeapon = currentWeapon;
			this.range = currentWeapon.getRange();
			this.bulletSpeedSlowerCycle = 0;
			try {
				texture = new Image("res/Guided.png");
				Sounds.missile.play(1, 0.1f);
			}catch(SlickException e) {
				
			}
			v.sub(p);
			v.add(pm);
			v.normalise();
			texture.rotate((float)v.getTheta());
			this.oldTheta = (float)v.getTheta();
		}
		
		if(!currentWeapon.isInfinite() || !currentWeapon.isEnemy()) {
			if(currentWeapon.getName().equals("RPG-Launcher") || currentWeapon.getName().equals("Guided RPG")) {
				removeKebab = true;
			}
			else {
				currentWeapon.setCount(currentWeapon.getCount()-1);
			}
			
		}
	}
	
	/**
	 * Constructor for a dummy bullet used in grenade aiming.
	 * @param x
	 * @param y
	 * @param destX
	 * @param destY
	 * @param test
	 */
	public Bullet(int x, int y, int destX, int destY, Shape test) {
		if(x == destX && y == destY) {
			destY++;
		}

		hitBox = new Rectangle(0, 0, 3, 3);
		p = new Vector2f(x,y);
		pm = new Vector2f(destX, destY);
		v = new Vector2f(0,0);
		g = new Vector2f(0,0.005f);
		this.gravityAccelerationCycle = 0;
		this.bulletSpeedSlowerCycle = 0;
		this.oldLineOfFire = test;
		try {
			texture = new Image("res/Aim.png");
		}catch(SlickException e) {
			
		}
		v.sub(p);
		v.add(pm);
		int i = (int) v.length();
		v.normalise();
		Vector2f force = new Vector2f(v);
		force.scale(0.003f);
		while(i > 1) {
			v.add(force);
			i--;
		}
	}
	
	/**
	 * Implementation of visibility.
	 */
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		//g.draw(hitBox);
		texture.draw(p.getX()-32, p.getY()-32);
		//g.drawString("v:" + v.getX() + "," + v.getY(), p.getX(), p.getY());
	}
	
	/**
	 * Implementation of activity.
	 */
	public void update(ArrayList<Object> oList, Map m, int delta) {
		if(currentWeapon == null) {
			for(int i = 0; i < 1*delta; i++) {
				if(bulletSpeedSlowerCycle == 0) {
					gravityAccelerationCycle++;
					bulletSpeedSlowerCycle++;
					p.add(v);
					for(int x = 0; x < gravityAccelerationCycle; x++) {
						p.add(g);
					}
					texture.rotate(0.1f);
					if(groundCollision(m) || thingsMoved(oList)) {
						oList.remove(this);
						break;
					}
				}
				else bulletSpeedSlowerCycle--;
			}
		}
		else if(currentWeapon.getName().equals("Grenade-Launcher")) {
			for(int i = 0; i < currentWeapon.getProjectileSpeed()*delta; i++) {
				if(bulletSpeedSlowerCycle == 0) {
					gravityAccelerationCycle++;
					bulletSpeedSlowerCycle++;
					p.add(v);
					texture.rotate(1);
					for(int x = 0; x < gravityAccelerationCycle; x++) {
						p.add(g);
					}
					if(groundCollision(m) || enemyCollision(oList) || getDestroyed()) {
						oList.remove(this);
						Sounds.grenade.play(1, 0.1f);
						break;
					}
				}
				else bulletSpeedSlowerCycle--;
			}
		}
		else if(currentWeapon.getName().equals("Pistol") || currentWeapon.getName().equals("Assault Rifle") ||
				currentWeapon.getName().equals("Sniper Rifle") ||
				currentWeapon.getName().equals("Pump Shotgun") || currentWeapon.getName().equals("Flamethrower")) {
			for(int i = 0; i < currentWeapon.getProjectileSpeed()*delta; i++) {
				p.add(v);
				if(groundCollision(m) || enemyCollision(oList) || range <= 0 || getDestroyed()) {
					oList.remove(this);
					break;
				}
				range--;
			}
		}
		else if(currentWeapon.getName().equals("RPG-Launcher")) {
			for(int i = 0; i < currentWeapon.getProjectileSpeed()*delta; i++) {
				if(bulletSpeedSlowerCycle == 0) {
					bulletSpeedSlowerCycle++;
					p.add(v);
					if(groundCollision(m) || enemyCollision(oList) || range <= 0 || getDestroyed()) {
						oList.remove(this);
						Sounds.grenade.play(1, 0.1f);
						break;
					}
					range--;
				}
				else bulletSpeedSlowerCycle--;
			}
		}
		else if(currentWeapon.getName().equals("Guided RPG")) {
			for(int i = 0; i < currentWeapon.getProjectileSpeed()*delta; i++) {
				if(currentWeapon.isEnemy()) {
					pm = new Vector2f(((Player)oList.get(0)).getP());
					v = new Vector2f(0,0);
					v.sub(p);
					v.add(pm);
					v.normalise();
				}
				else {
					pm = new Vector2f(((Player)oList.get(0)).getMouse());
					v = new Vector2f(0,0);
					v.sub(p);
					v.add(pm);
					v.normalise();
				}
				texture.rotate((float)v.getTheta()-oldTheta);
				oldTheta = (float)v.getTheta();
				if(bulletSpeedSlowerCycle == 0) {
					bulletSpeedSlowerCycle++;
					p.add(v);
					if(groundCollision(m) || enemyCollision(oList) || range <= 0 || getDestroyed()) {
						oList.remove(this);
						Sounds.grenade.play(1, 0.1f);
						break;
					}
					range--;
				}
				else bulletSpeedSlowerCycle--;
			}
		}
		if(removeKebab) {
			((Player)(oList.get(0))).getWeapons().get(3).setCount(((Player)(oList.get(0))).getWeapons().get(3).getCount() - 1);
			((Player)(oList.get(0))).getWeapons().get(5).setCount(((Player)(oList.get(0))).getWeapons().get(5).getCount() - 1);
			removeKebab = false;
		}
	}

	/**
	 * Checks for changes in the cursor's position.
	 * @param oList
	 * @return
	 */
	private boolean thingsMoved(ArrayList<Object> oList) {
		if((int)(oldLineOfFire.getCenterX()) != (int)(((Player)oList.get(0)).getLineOfFire().getCenterX())) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the bullet is hitting anything and acts accordingily.
	 * @param oList
	 * @return
	 */
	private boolean enemyCollision(ArrayList<Object> oList) {
		for(Object o : oList) {
			if(o instanceof Collider) {
				if(o instanceof Player && currentWeapon.isEnemy()) {
					if(hitBox.intersects(((Collider)o).getHitbox())) {
						((Collider)o).takeDamage(currentWeapon.getDamage());
						return true;
					}
				}
				else if (!(o instanceof Player) && !currentWeapon.isEnemy()) {
					if(hitBox.intersects(((Collider)o).getHitbox())) {
						((Collider)o).takeDamage(currentWeapon.getDamage());
						return true;
					}
				}
				else if(o instanceof Blockade_Barrel) {
					if(hitBox.intersects(((Collider)o).getHitbox())) {
						((Collider)o).takeDamage(currentWeapon.getDamage());
						return true;
					}
				}
			}
			else if(o instanceof Bullet) {
				try {
					if(((Bullet)o).currentWeapon.isDestroyable() && !o.equals(this)) {
						if(hitBox.intersects(((Bullet)o).hitBox)) {
							((Bullet)o).setDestroyed();
							return true;
						}
					}
				} catch(NullPointerException e) {
					
				}
			}
		}
		return false;
	}
	
	public boolean getDestroyed() {
		return destroyed;
	}
	public void setDestroyed() {
		destroyed = true;
	}

	/**
	 * Checks if the bullets are hitting the ground or fly off the map.
	 * @param m
	 * @return
	 */
	private boolean groundCollision(Map m) {
		if (p.getY() > 3840) {
			return true;
		}
		if (p.getX() > 3840 ) {
			return true;
		}
		if (p.getX() < 0 ) {
			return true;
		}
		if ( m.ground(p.getX(),p.getY())) {
			return true;
		}
		return false;
	}
	
}