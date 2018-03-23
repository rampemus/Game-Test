import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Weapon{
	
	private String name;
	private int count;
	private int damage;
	private int firingSpeed;
	private float projectileSpeed;
	private boolean destroyable;
	private boolean guided;
	private boolean arcs;
	private boolean enemy;
	private boolean infinite;
	private static ArrayList<Weapon> ammoTypes;
	
	public Weapon(String name, int count, int damage, int firingSpeed, float projectileSpeed, boolean destroyable, boolean guided,
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
	
	public static void createAmmo() {
		ammoTypes = new ArrayList<Weapon>();
		ammoTypes.add(new Weapon("Pistol", 999, 100, 1000, 1.0f, false, false, false, false, true));
		ammoTypes.add(new Weapon("Assault Rifle", 0, 100, 2500, 1.0f, false, false, false, false, true));
		ammoTypes.add(new Weapon("Sniper Rifle", 0, 500, 2500, 1.5f, false, false, false, false, true));
		ammoTypes.add(new Weapon("RPG-Launcher", 0, 1000, 5000, 0.7f, true, false, false, false, true));
		ammoTypes.add(new Weapon("Granade-Launcher", 0, 1000, 5000, 0.7f, true, false, true, false, true));
		ammoTypes.add(new Weapon("Guided RPG", 0, 1000, 5000, 0.7f, true, true, false, false, true));
	}
	public static ArrayList<Weapon> getAmmo() {
		return ammoTypes;
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
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getFiringSpeed() {
		return firingSpeed;
	}
	public void setFiringSpeed(int firingSpeed) {
		this.firingSpeed = firingSpeed;
	}
	
	public float getProjectileSpeed() {
		return projectileSpeed;
	}
	public void setProjectileSpeed(float projectileSpeed) {
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

class Bullet implements Active,Visible{
	
	private Vector2f pm;
	private Vector2f p;
	private Vector2f v;
	private Vector2f g;
	private Shape hitBox;
	private int ammo;
	private boolean hit;
	
	public Bullet(int x, int y, int destX, int destY, int ammo) {
		
		if(x == destX && y == destY) {
			destY++;
		}
		
		if(Weapon.getAmmo().get(ammo).getCount() > 0) {
			if(Weapon.getAmmo().get(ammo).getName().equals("Pistol") || Weapon.getAmmo().get(ammo).getName().equals("Assault Rifle") ||
					Weapon.getAmmo().get(ammo).getName().equals("Sniper Rifle")) {
				hitBox = new Rectangle(0, 0, 9, 3);
				p = new Vector2f(x,y);
				pm = new Vector2f(destX, destY);
				v = new Vector2f(0,0);
				g = new Vector2f(0,0);
				this.ammo = ammo;
				hit = false;
				
				v.sub(p);
				v.add(pm);
				v.normalise();
				if(!Weapon.getAmmo().get(ammo).isInfinite())
					Weapon.getAmmo().get(ammo).setCount(Weapon.getAmmo().get(ammo).getCount()-1);
			}
			
		}
	}
	
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		g.draw(hitBox);
		//g.drawString("v:" + v.getX() + "," + v.getY(), p.getX(), p.getY());
	}
	
	public void update(ArrayList<Object> oList, Map m, int delta) {
		if(Weapon.getAmmo().get(ammo).getName().equals("Pistol") || Weapon.getAmmo().get(ammo).getName().equals("Assault Rifle") ||
				Weapon.getAmmo().get(ammo).getName().equals("Sniper Rifle")) {
			for(int i = 0; i < Weapon.getAmmo().get(ammo).getProjectileSpeed()*delta; i++) {
				p.add(v);
				if(groundCollision(m)) {
					oList.remove(this);
					break;
				}
			}
		}
	}
	
	public boolean isHit() {
		return hit;
	}
	
	public boolean groundCollision(Map m) {
		if (p.getY() > 600) {
			return true;
		}
		if (p.getX() > 800 ) {
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
	
	public int doDamage() {
		hit = true;
		return Weapon.getAmmo().get(ammo).getDamage();
	}
	
}