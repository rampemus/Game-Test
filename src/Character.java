import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Character extends Collider{
	protected float xAcceleration = 0.005f;
	protected int shootCooldown = 0;
	protected Weapon currentWeapon;
	protected ArrayList<Weapon> weapons;
	
	public Character(int x, int y) {
		super(x,y);
		height = 64; //resize hitbox
		width = 32;
		hitBox = new Rectangle(0, 0, width, height);
		weapons = new ArrayList<>(Arrays.asList(new Weapon(Weapon.getWeapons().get(0))));
		currentWeapon = weapons.get(0);
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		
		if ( shootCooldown > 0) {
			shootCooldown -= delta;
		} else {
			shootCooldown = 0;
		}
	}
	
	public void shoot(ArrayList<Object> oList, int x, int y) {
		if (shootCooldown == 0) {
			if((!(currentWeapon.getCount() == 0)) || currentWeapon.isEnemy()) {
				oList.add(new Bullet((int)this.getX(), (int)this.getY(), x, y, currentWeapon));
			}
			//animaation vaihtaminen ampumiseen jne.
			shootCooldown = currentWeapon.getFiringRate();
		}
	}
	
	public boolean isShooting() {
		if ( shootCooldown == currentWeapon.getFiringRate() - 1) return true;
		return false;
	}
	
	public void walkRight(int delta) {
		if ( v.getX() < xMaxSpeed ) {
			v.add(new Vector2f(xAcceleration*delta,0));
		}
	}
	
	public void walkLeft(int delta) {
		if ( v.getX() > -xMaxSpeed ) {
			v.add(new Vector2f(-xAcceleration*delta,0));
		}
	}
	
	public void jump(Map m) {
		if ( onGround(m) && !headCollision(m)) {
			jumpCooldown = jumpStrength;
		}
		
		if (jumpCooldown > jumpStrength/400 && !headCollision(m)) {
			v.set(v.getX(),-jumpStrength);
		}
	}
}
