import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Character extends Collider{
	protected float xAcceleration = 0.005f;
	protected int shootCooldown = 0;
	
	public Character(int x, int y) {
		super(x,y);
		height = 64; //resize hitbox
		width = 32;
		hitBox = new Rectangle(0, 0, width, height);
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		
		if ( shootCooldown > 0) {
			shootCooldown -= delta;
		} else {
			shootCooldown = 0;
		}
	}
	
	public void shoot() {
		if (shootCooldown == 0) {
			// bulletin luominen
			//animaation vaihtaminen ampumiseen jne.
			//shootCooldown = weaponArray.get(joku).fireRate
		}
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
		if ( onGround(m) ) {
			jumpCooldown = jumpStrength;
		}
		
		if (jumpCooldown > jumpStrength/400) {
			v.set(v.getX(),-jumpStrength);
		}
	}
}
