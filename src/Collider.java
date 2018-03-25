import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Collider {
	//Galileian kinematics
	protected Vector2f p; //position vector
	protected Vector2f v; //velocity vector
	protected Vector2f g; //gravity acceleration vector
	protected float friction = 0.001f; // how fast collider stops while no interactions
	protected float xMaxSpeed = 0.6f;  // how fast can collider travel in direction of x-axis
	protected float elasticity = 0; //value between 0-1, how much collider bounces when hitting to wall or floor
	
	protected float jumpStrength = 0.5f; // how high can a collider jump if it could jump (obviously it can't
	protected float jumpCooldown = 0; // for calculating the jumping strength of "input"
	protected float threshold = 1; // can climb 1 pixel up = 45° angled incline
	
	protected int hp = 100; // health points
	
	//Hitbox
	protected int height = 16; // the height of the hitbox
	protected int width = 16; // the width of the hitbox
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
		g.drawString("HP:" + hp,getX()-25,getY()-height);
	}
	
	public boolean groundCollision(Map m) {
		if (p.getY() > 600 - height/2) {
			return true;
		}
		if (p.getX() > 800 ) {
			return true;
		}
		if (p.getX() < 0 ) {
			return true;
		}
		if ( m.ground(p.getX(),p.getY(),height,width)) { //this might be wrong
			return true;
		}
		return false;
	}

	public boolean onGround(Map m) {
		Vector2f below = new Vector2f(0,1);
		boolean result = false;
		p.add(below); //if one pixel below there is ground
		result = groundCollision(m);
		p.sub(below); //remove side effects
		return result && !groundCollision(m);
	}
	
	private void pAddV(Map m) {
		Vector2f xstep = new Vector2f(0.1f,0);
		Vector2f ystep = new Vector2f(0,0.1f);
		for ( int i = 0; i < Math.abs(v.getY())*10; i++) {
			if ( v.getY() > 0) {
				p.add(ystep);
				if (groundCollision(m)) {
					p.sub(ystep);
					v.set(v.getX(),v.getY()*-elasticity);
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
				if ( groundCollision(m)) {
					p.sub(xstep);
					v.set(v.getY()*-elasticity,v.getY());
				}
			} else if (v.getX() < 0 ) {
				p.sub(xstep);
				if ( groundCollision(m)) {
					p.add(xstep);
					v.set(v.getY()*-elasticity,v.getY());
				}
			}
		}
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		g.scale(delta);
		v.add(g);
		v.scale(delta);
		
		if (Math.abs(v.getX()) + Math.abs(v.getY()) > 1) {
			p.add(v);
			if ( groundCollision(m) ) {
				p.sub(v);
				pAddV(m);
			}
		} else {
			pAddV(m);
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
	
	public Vector2f getP() {
		return p;
	}
	
	public float getX() {
		return p.getX();
	}
	
	public float getY() {
		return p.getY();
	}
	
	public void vAdd(Vector2f a, int delta) {
		a.scale(delta);
		v.add(a);
		a.scale(1/delta);
	}
	
	public Shape getHitbox() {
		return hitBox;
	}
	
	public void takeDamage(int amount) {
		hp -= amount;
	}
}
