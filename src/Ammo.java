import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

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
	
	public static void createBullets() {
		bullets = new ArrayList<Ammo>();
		bullets.add(new Ammo("Pistol", 999, 1.0, 1.0, 1.0, false, false, false, false, true));
		bullets.add(new Ammo("Assault Rifle", 0, 1.0, 0.25, 1.0, false, false, false, false, true));
		bullets.add(new Ammo("Sniper Rifle", 0, 5.0, 2.5, 0.75, false, false, false, false, true));
		bullets.add(new Ammo("RPG-Launcher", 0, 10.0, 5.0, 0.5, true, false, false, false, true));
		bullets.add(new Ammo("Granade-Launcher", 0, 10.0, 5.0, 0.5, true, false, true, false, true));
		bullets.add(new Ammo("Guided RPG", 0, 10.0, 5.0, 0.5, true, true, false, false, true));
	}
	public static ArrayList<Ammo> getBullets() {
		return bullets;
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
	
}

class Bullet{
	
	private Vector2f pm;
	private Vector2f p;
	private Vector2f v;
	private Vector2f g;
	private Shape hitBox;
	private Ammo ammo;
	
	public Bullet(int x, int y, int destX, int destY, Ammo ammo) {
		if(ammo.getCount() > 0) {
			
			hitBox = new Rectangle(0, 0, 20, 8);
			p = new Vector2f(x,y);
			pm = new Vector2f(destX, destY);
			v = new Vector2f(0,0);
			g = new Vector2f(0,0.00f);
			this.ammo = ammo;
			
			v.sub(pm);
			v.add(p);
			v.normalise();
			
			if(ammo.getName().equals("Pistol") || ammo.getName().equals("Assault Rifle") || ammo.getName().equals("Sniper Rifle"))
				hitBox = new Rectangle(0, 0, 9, 3);
			if(ammo.isArcs())
				
			if(!ammo.isInfinite())
				ammo.setCount(ammo.getCount()-1);
		}
	}
	
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		g.draw(hitBox);
		//g.drawString("v:" + v.getX() + "," + v.getY(), p.getX(), p.getY());
	}
	
	public void doDamage() {
		ammo.getDamage();
	}
	
}