import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public class Ammo{
	
	private String name;
	private int count;
	private double damage;
	private double firingSpeed;
	private double projectileSpeed;
	private boolean destroyable;
	private boolean guided;
	private boolean arcs;
	private boolean enemy;
	private boolean infinite;
	private static ArrayList<Ammo> bullets;
	
	public Ammo(String name, int count, double damage, double firingSpeed, double projectileSpeed, boolean destroyable, boolean guided,
			boolean arcs, boolean enemy, boolean infinite) {
		this.name = name;
		this.count = count;
		this.damage = damage;
		this.firingSpeed = firingSpeed;
		this.projectileSpeed = projectileSpeed;
		this.destroyable = destroyable;
		this.guided = guided;
		this.arcs = arcs;
		this.enemy = enemy;
		this.infinite = infinite;
	}
	
	public void createBullets() {
		bullets = new ArrayList<Ammo>();
		bullets.add(new Ammo("Pistol", 0, 1.0, 1.0, 1.0, false, false, false, false, true));
		bullets.add(new Ammo("Assault Rifle", 0, 1.0, 0.25, 1.0, false, false, false, false, true));
		bullets.add(new Ammo("Sniper Rifle", 0, 5.0, 2.5, 0.75, false, false, false, false, true));
		bullets.add(new Ammo("RPG-Launcher", 0, 10.0, 5.0, 0.5, true, false, false, false, true));
		bullets.add(new Ammo("Granade-Launcher", 0, 10.0, 5.0, 0.5, true, false, true, false, true));
		bullets.add(new Ammo("Guided RPG", 0, 10.0, 5.0, 0.5, true, true, false, false, true));
	}
	public ArrayList<Ammo> getBullets() {
		return bullets;
	}
	
	public void shoot(int x, int y, int destX, int destY) {
		if(count > 0) {
			Bullet bullet = new Bullet(x, y, destX, destY, this);
			bullet.hitBox = new Rectangle(0, 0, 2, 1);
			
			count--;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCount() {
		if(!infinite) return count;
		else return 999;
	}
	public void setCount(int count) {
		if(!infinite) this.count = count;
	}
	
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public double getFiringSpeed() {
		return firingSpeed;
	}
	public void setFiringSpeed(double firingSpeed) {
		this.firingSpeed = firingSpeed;
	}
	
	public double getProjectileSpeed() {
		return projectileSpeed;
	}
	public void setProjectileSpeed(double projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
	}
	
	public boolean isDestroyable() {
		return destroyable;
	}
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}
	
	public boolean isGuided() {
		return guided;
	}
	public void setGuided(boolean guided) {
		this.guided = guided;
	}
	
	public boolean isArcs() {
		return arcs;
	}
	public void setArcs(boolean arcs) {
		this.arcs = arcs;
	}
	
	public boolean isInfinite() {
		return infinite;
	}
	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}
	
	public boolean isEnemy() {
		return enemy;
	}
	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}
	
	private class Bullet extends Collider{
		
		public Bullet(int x, int y, int destX, int destY, Ammo ammo) {
			super(x, y);
			
		}
	}
}