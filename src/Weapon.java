import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Weapon{
	
	private String name;
	private int count;
	private int damage;
	private int firingRate;
	private float projectileSpeed;
	private boolean destroyable;
	private boolean guided;
	private boolean arcs;
	private boolean enemy;
	private boolean infinite;
	private static ArrayList<Weapon> ammoTypes;
	
	public Weapon(String name, int count, int damage, int firingRate, float projectileSpeed, boolean destroyable, boolean guided,
			boolean arcs, boolean enemy, boolean infinite) {
		this.name = name;
		this.count = count;
		this.damage = damage;
		this.firingRate = firingRate;
		this.projectileSpeed = projectileSpeed;
		this.destroyable = destroyable;
		this.guided = guided;
		this.arcs = arcs;
		this.enemy = enemy;
		this.infinite = infinite;
	}
	
	public Weapon(Weapon weapon) {
		this.name = weapon.getName();
		this.count = weapon.getCount();
		this.damage = weapon.getDamage();
		this.firingRate = weapon.getFiringRate();
		this.projectileSpeed = weapon.getProjectileSpeed();
		this.destroyable = weapon.isDestroyable();
		this.guided = weapon.isGuided();
		this.arcs = weapon.isArcs();
		this.enemy = weapon.isEnemy();
		this.infinite = weapon.isInfinite();
	}
	
	public Weapon(Weapon weapon, boolean enemy) {
		this.name = weapon.getName();
		this.count = weapon.getCount();
		this.damage = weapon.getDamage();
		this.firingRate = weapon.getFiringRate();
		this.projectileSpeed = weapon.getProjectileSpeed();
		this.destroyable = weapon.isDestroyable();
		this.guided = weapon.isGuided();
		this.arcs = weapon.isArcs();
		this.enemy = enemy;
		this.infinite = weapon.isInfinite();
	}

	public static void createWeapons() {
		ammoTypes = new ArrayList<Weapon>();
		ammoTypes.add(new Weapon("Pistol", 999, 200, 500, 1.0f, false, false, false, false, true));
		ammoTypes.add(new Weapon("Assault Rifle", 0, 100, 100, 1.0f, false, false, false, false, true));
		ammoTypes.add(new Weapon("Sniper Rifle", 0, 500, 2500, 2.0f, false, false, false, false, true));
		ammoTypes.add(new Weapon("RPG-Launcher", 0, 1000, 5000, 0.7f, true, false, false, false, true));
		ammoTypes.add(new Weapon("Grenade-Launcher", 0, 1000, 5000, 0.7f, true, false, true, false, true));
		ammoTypes.add(new Weapon("Guided RPG", 0, 1000, 5000, 0.7f, true, true, false, false, true));
	}
	public static ArrayList<Weapon> getWeapons() {
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
	
	public int getFiringRate() {
		return firingRate;
	}
	public void setFiringRate(int firingRate) {
		this.firingRate = firingRate;
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
	private Weapon currentWeapon;
	
	public Bullet(int x, int y, int destX, int destY, Weapon currentWeapon) {
		
		if(x == destX && y == destY) {
			destY++;
		}
		
		if(currentWeapon.getName().equals("Pistol") || currentWeapon.getName().equals("Assault Rifle") ||
				currentWeapon.getName().equals("Sniper Rifle")) {
			hitBox = new Rectangle(0, 0, 9, 3);
			p = new Vector2f(x,y);
			pm = new Vector2f(destX, destY);
			v = new Vector2f(0,0);
			g = new Vector2f(0,0);
			this.currentWeapon = currentWeapon;
			
			v.sub(p);
			v.add(pm);
			v.normalise();
			if(!currentWeapon.isInfinite() || !currentWeapon.isEnemy())
				currentWeapon.setCount(currentWeapon.getCount()-1);
		}
	}
	
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		g.draw(hitBox);
		//g.drawString("v:" + v.getX() + "," + v.getY(), p.getX(), p.getY());
	}
	
	public void update(ArrayList<Object> oList, Map m, int delta) {
		if(currentWeapon.getName().equals("Pistol") || currentWeapon.getName().equals("Assault Rifle") ||
				currentWeapon.getName().equals("Sniper Rifle")) {
			for(int i = 0; i < currentWeapon.getProjectileSpeed()*delta; i++) {
				p.add(v);
				if(groundCollision(m) || enemyCollision(oList)) {
					oList.remove(this);
					break;
				}
			}
		}
	}

	//hits an enemy
	private boolean enemyCollision(ArrayList<Object> oList) {
		for(Object o : oList) {
			if(o instanceof Collider) {
				if(o instanceof Player && currentWeapon.isEnemy()) {
					if(hitBox.intersects(((Collider)o).getHitbox())) {
						((Collider)o).takeDamage(currentWeapon.getDamage());
						return true;
					}
				}
				if (!(o instanceof Player) && !currentWeapon.isEnemy()) {
					if(hitBox.intersects(((Collider)o).getHitbox())) {
						((Collider)o).takeDamage(currentWeapon.getDamage());
						return true;
					}
				}
			}
		}
		return false;
	}

	//hits the ground or goes out of the map
	private boolean groundCollision(Map m) {
		if (p.getY() > 600) {
			return true;
		}
		if (p.getX() > 800 ) {
			return true;
		}
		if (p.getX() < 0 ) {
			return true;
		}
		if ( m.ground(p.getX(),p.getY(),9,3)) {
			return true;
		}
		return false;
	}
	
}