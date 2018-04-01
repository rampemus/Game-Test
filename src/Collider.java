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
	
	/**
	 * Creates collider to given coordinates with zero velocity and with gravitation
	 * @author Pasi Toivanen
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Collider(int x, int y) {
		hitBox = new Rectangle(0, 0, width, height);
		p = new Vector2f(x,y);
		v = new Vector2f(0,0);
		g = new Vector2f(0,0.005f);
	}
	
	/**
	 * Draws collider to graphics object (mostly for debugging)
	 * @param g Graphics object from the gameState
	 * @author Pasi Toivanen
	 */
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		g.draw(hitBox);
		g.drawString("HP: " + hp,getX()-25,getY()-height);
	}
	
	/**
	 * Will tell if the collider's feet are overlapping to the existing map
	 * @param m Map object that is called through the update-method
	 * @return true, if there is anything to collide with
	 * @author Pasi Toivanen
	 */
	public boolean groundCollision(Map m) {
		if (p.getY() > 2480 - height/2) {
			return true;
		}
		if (p.getX() > 2480 ) {
			return true;
		}
		if (p.getX() < 0 ) {
			return true;
		}
		if ( m.ground(p.getX(),p.getY()+height/2)) { //this might be wrong in the future
			return true;
		}
		return false;
	}
	
	/**
	 * Will tell if the collider's upper body is overlapping to the existing map
	 * @param m Map object that is called through the update-method
	 * @return true, if there is anything to collide with
	 * @author Pasi Toivanen
	 */
	public boolean headCollision( Map m ) { //detects if player head is touching ground
		return m.ground(p.getX(),p.getY()-height/2+1);
	}
	
	/**
	 * Will tell if the collider's upper body's above pixel is overlapping to the existing map
	 * @param m Map object that is called through the update-method
	 * @return true, if there is anything to collide with
	 * @author Pasi Toivanen
	 */
	public boolean underCeiling( Map m ) {
		return m.ground(p.getX(),p.getY()-height/2);
	}
	
	/**
	 * Will tell if the collider's right side is overlapping to the existing map
	 * @param m Map object that is called through the update-method
	 * @return true, if there is anything to collide with
	 * @author Pasi Toivanen
	 */
	public boolean rightCollision( Map m ) { //detects if players right side is overlapping with an obstacle
		return m.ground(p.getX()+width/2,p.getY()+height/2 - width/2);
	}
	
	/**
	 * Will tell the collider's left side is overlapping to the existing map
	 * @param m Map object that is called through the update-method
	 * @return true, if there is anything to collide with
	 * @author Pasi Toivanen
	 */
	public boolean leftCollision( Map m ) { //detects if players right side is overlapping with an obstacle
		return m.ground(p.getX()-width/2,p.getY()+height/2 - width/2);
	}
	
	/**
	 * Will tell if any pixel relative to collider is overlapping to the existing map
	 * Will be used to create AI
	 * @param m Map object that is called through the update-method
	 * @return true, if there is anything to collide with
	 * @author Pasi Toivanen
	 */
	public boolean checkMap(Map m, float x, float y ) { //returns if there is ground if you add x,y to collider's coordinates
		if ( m.ground(p.getX() + x,p.getY()+y)) { 
			return true;
		}
		return false;
	}
	
	/**
	 * Will tell if the collider's feet are on top of existing map tile
	 * @param m Map object that is called through the update-method
	 * @return true, if there is anything to collide with
	 * @author Pasi Toivanen
	 */
	public boolean onGround(Map m) {
		Vector2f below = new Vector2f(0,1);
		boolean result = false;
		p.add(below); //if one pixel below there is ground
		result = groundCollision(m);
		p.sub(below); //remove side effects
		return result && !groundCollision(m);
	}
	
	/**
	 * Method that will make p.add(v) (adding velocity to position vector) in a certain steps to prevent any overlapping with the map-object
	 * 
	 * @param m Map object that is called through the update-method
	 * @author Pasi Toivanen
	 */
	private void pAddV(Map m) {
		Vector2f xstep = new Vector2f(0.1f,0);
		Vector2f ystep = new Vector2f(0,0.1f);
		
		//detecting ground underneath
		for ( int i = 0; i < Math.abs(v.getY())*10; i++) {
			if ( v.getY() > 0) {
				p.add(ystep);
				if (groundCollision(m)) {
					p.sub(ystep);
					v.set(v.getX(),v.getY()*-elasticity);
					break;
				}
			} else if ( v.getY() < 0 ) {
				p.sub(ystep);
				if (headCollision(m)) {
					p.add(ystep);
					v.set(v.getX(),v.getY()*-elasticity);
					break;
				}
			} 
		}
		while ( leftCollision(m) ) { //if player is overlapping with obstacle during y-axis movement, this will prevent overlapping
			p.add(xstep);
		}
		while ( rightCollision(m) ) {
			p.sub(xstep);
		}
		float speed = Math.abs(v.getX());
		for ( int i = 0; i < speed*10; i++) {
			if ( v.getX() > 0) {
				p.add(xstep);
				//detect ground next to leg
				if ( groundCollision(m) || headCollision(m) || rightCollision(m)) {
					p.sub(xstep);
					v.set(v.getY()*-elasticity,v.getY());
				} 
			} else if (v.getX() < 0 ) {
				p.sub(xstep);
				if ( groundCollision(m) || headCollision(m) || leftCollision(m) ) {
					p.add(xstep);
					v.set(v.getY()*-elasticity,v.getY());
				}
			}
		}
	}
	
	/**
	 * updates collider so that it will obey the mechanics with map and other objects in the gameState
	 * 
	 * @param o Other objects in the gameState
	 * @param m Map object of the gameState
	 * @param delta Number of updates to do in single run
	 */
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		g.scale(delta);
		v.add(g);
		v.scale(delta);
		
		if (Math.abs(v.getX()) + Math.abs(v.getY()) > 1) {
			p.add(v);
			if ( groundCollision(m) || headCollision(m) || rightCollision(m) || leftCollision(m) ) {
				p.sub(v);
				pAddV(m);
			}
		} else {
			pAddV(m);
		}
		
		//reset
		v.scale(1f/delta);
		g.scale(1f/delta);
		
		//make the collider jump if it anything has made it to jump
		if (jumpCooldown > jumpStrength/400 && !headCollision(m)) {
			jumpCooldown -= jumpStrength/200*delta; //jump takes framerate in account
		} else {
			jumpCooldown = 0;
		}
		
		//stop the character using friction
		if (v.getX() < -delta*friction) {
			v.set(v.getX() + delta*friction,v.getY());
		} else if (v.getX() > delta*friction) {
			v.set(v.getX() - delta*friction,v.getY());
		} else {
			v.set(0,v.getY());
		}
	}
	
	/**
	 * Get collider position
	 * @return Vector2f
	 */
	public Vector2f getP() {
		return p;
	}
	
	/**
	 * Get collider x-position
	 * @return float
	 */
	public float getX() {
		return p.getX();
	}
	
	/**
	 * Get collider y-position
	 * @return float
	 */
	public float getY() {
		return p.getY();
	}
	
	public void vAdd(Vector2f a, int delta) {
		a.scale(delta);
		v.add(a);
		a.scale(1/delta);
	}
	
	public Shape getHitbox() {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		return hitBox;
	}
	
	public void takeDamage(int amount) {
		hp -= amount;
	}
}
