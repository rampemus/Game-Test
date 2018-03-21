import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Collider {
	//Galileian kinematics
	protected Vector2f p;
	protected Vector2f v;
	protected Vector2f g;
	protected float friction = 0.001f;
	protected float xMaxSpeed = 0.6f;
	
	protected float jumpStrength = 0.5f;
	protected float jumpCooldown = 0;
	protected float threshold = 1; // can climb 1 pixel up = 45° angled incline
	
	//Hitbox
	protected int height = 64;
	protected int width = 32;
	protected Shape hitBox = null;
	
	public Collider(int x, int y) {
		hitBox = new Rectangle(0, 0, width, height);
		p = new Vector2f(x,y);
		v = new Vector2f(0,0);
		g = new Vector2f(0,0.005f);
	}
	
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		g.draw(hitBox);
		g.drawString("v:" + v.getX() + "," + v.getY(), p.getX(), p.getY());
	}
	
	public boolean groundCollision() {
		if (p.getY() > 600 - height/2) {
			return true;
		}
		if (p.getX() > 800 ) {
			return true;
		}
		if (p.getX() < 0 ) {
			return true;
		}
		return false;
	}

	public boolean onGround() {
		Vector2f below = new Vector2f(0,1);
		boolean result = false;
		p.add(below); //if one pixel below there is ground
		result = groundCollision();
		p.sub(below); //remove side effects
		return result && !groundCollision();
	}
	
	private void pAddV() {
		Vector2f xstep = new Vector2f(0.1f,0);
		Vector2f ystep = new Vector2f(0,0.1f);
		for ( int i = 0; i < Math.abs(v.getY())*10; i++) {
			if ( v.getY() > 0) {
				p.add(ystep);
				if (groundCollision()) {
					p.sub(ystep);
					v.set(v.getX(),0);
					break;
				}
			} else {
				p.sub(ystep);
			}
		}
		float speed = Math.abs(v.getX());
		for ( int i = 0; i < speed*10; i++) {
			if ( v.getX() > 0) {
				p.add(xstep);
				if ( groundCollision()) {
					p.sub(xstep);
					v.set(0,v.getY());
				}
			} else if (v.getX() < 0 ) {
				p.sub(xstep);
				if ( groundCollision()) {
					p.add(xstep);
					v.set(0,v.getY());
				}
			}
		}
	}
	
	public void update(int delta) {
		g.scale(delta);
		v.add(g);
		v.scale(delta);
		
		if (Math.abs(v.getX()) + Math.abs(v.getY()) > 1) {
			p.add(v);
			if ( groundCollision() ) {
				p.sub(v);
				pAddV();
			}
		} else {
			pAddV();
		}
		
		//reset
		v.scale(1f/delta);
		g.scale(1f/delta);
		
		if (jumpCooldown > jumpStrength/400) {
			jumpCooldown -= jumpStrength/200*delta; //jump takes framerate in account
		} else {
			jumpCooldown = 0;
		}
		
		//stop the character
		if (v.getX() < -delta*friction) {
			v.set(v.getX() + delta*friction,v.getY());
		} else if (v.getX() > delta*friction) {
			v.set(v.getX() - delta*friction,v.getY());
		} else {
			v.set(0,v.getY());
		}
	}
}
