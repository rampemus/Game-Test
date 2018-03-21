import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class Character extends Collider{
	protected float xAcceleration = 0.005f;
	
	public Character(int x, int y) {
		super(x,y);
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
