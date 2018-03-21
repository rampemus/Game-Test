import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Character extends Collider{
	protected float xAcceleration = 0.005f;
	
	public Character(int x, int y) {
		super(x,y);
		height = 64; //resize hitbox
		width = 32;
		hitBox = new Rectangle(0, 0, width, height);
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
	
	public void jump() {
		if ( onGround() ) {
			jumpCooldown = jumpStrength;
		}
		
		if (jumpCooldown > jumpStrength/400) {
			v.set(v.getX(),-jumpStrength);
		}
	}
}
