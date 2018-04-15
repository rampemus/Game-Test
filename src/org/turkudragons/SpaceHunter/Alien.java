package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import java.util.Random;
import org.newdawn.slick.geom.Rectangle;
/**
 * The alien chaces the player, aims at it, jumps over ledges and jumps to avoid fire, making it one of the
 * hardest enemies to giht.
 * @author Tommi Heikkinen (almost everything) Pasi Toivanen (Hand rotation)
 *
 */
public class Alien extends Character implements Active, Visible {
	Image alien_s;
	Image hand;
	Image handFlipped;
	Image alien_w1;
	Image alien_w2;
	Image alien_w3;
	Image alien_w4;
	Image alien_w5;
	Image alien_w6;
	Image blank;
	Image [] alien_a = new Image[8];
	Animation alien_m;
	boolean alive = true;
	boolean evade = false;
	boolean chase = false;
	int evasionT;
	private Vector2f aim;
	private Vector2f mouse;

	/**
	 * Creates the character to certain coordinates and creates all necessary animations etc.
	 * @param x x-coordinate of player
	 * @param y y-coordinate of player
	 */
	public Alien(int defx, int defy) {
		super(defx,defy);
		width = 33;
		height = 64;
		hitBox = new Rectangle(defx, defy, width, height);
		hp = 1000;
		aim = new Vector2f(100, 0);
		mouse = new Vector2f(1000,1000);
		jumpStrength = 0.7f;
		xMaxSpeed = 0.4f;
		
		try {
			alien_s = new Image ("res/humanoid_char_stand1.png");//0
			alien_w1 = new Image ("res/humanoid_char_walk1.png");//1
			alien_w2 = new Image ("res/humanoid_char_walk2.png");	//2
			alien_w3 = new Image ("res/humanoid_char_walk3.png");	//3
			alien_w4 = new Image ("res/humanoid_char_walk4.png");	//4
			alien_w5 = new Image ("res/humanoid_char_walk5.png");	//5
			alien_w6 = new Image ("res/humanoid_char_walk6.png");	//6
			blank = new Image ("res/humanoid_char_walk1.png");	//7
			
			alien_a[0] = alien_s;
			alien_a[1] = alien_w1;
			alien_a[2] = alien_w2;
			alien_a[3] = alien_w3;
			alien_a[4] = alien_w4;
			alien_a[5] = alien_w5;
			alien_a[6] = alien_w6;
			alien_a[7] = blank;
			
			alien_m = new Animation(alien_a,200,true);
			
			hand = new Image("res/humanoid_hand.png");
			handFlipped = hand.getFlippedCopy(false, true);
			hotSpot = new Vector2f(0,0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random r = new Random();
		int x = r.nextInt(9);
		if (x>0 && !(x==4)&& x<8) {
			weapons.add(new Weapon(Weapon.getWeapons().get(x)));
			currentWeapon = weapons.get(1);
		}
		if (x == 8) {
			weapons.add(new Weapon(Weapon.getWeapons().get(1)));
			currentWeapon = weapons.get(1);
		}
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
	}
	/**
	 * updates the character on the playground. Super.update-has all the kinematics and common stuff with character
	 * Has the necessary functions to make the alien dodge bullets, shoot, jump over pits etc.
	 * @param o oList of the gamestate
	 * @param m Map of the gamestate
	 * @param delta How many steps of millisecs to be taken
	 */
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		// animation
		if(alien_m.getFrame() == 0 && !chase) {
			alien_m.stop();
		}
		
		if ((canSeeCharacter((Character)o.get(0),m,500)) && !chase){
			alien_m.start();
			chase = true;
		}
		if (!(canSeeCharacter((Character)o.get(0),m,500)) && chase){
			alien_m.setCurrentFrame(0);
			alien_m.stop();
			chase = false;
		}
		if (alien_m.getFrame()==6) {
			alien_m.setCurrentFrame(1);
		}
		if(hp <= 0) {
			alien_m.setCurrentFrame(7);
				Random r = new Random();
				int i = r.nextInt(10);
				if (i > 6) {
					o.add(new Item((int)this.getX(),(int)this.getY(),Collect.HP_LARGE));
				}
			o.remove(this);
			
		}
		//evasion
		if (canSeeCharacter((Character)o.get(0),m,500) && ((Character) o.get(0)).isShooting() && alive){
			if (((Player)o.get(0)).getLineOfFire().intersects(hitBox)) {
				evade = true;
				evasionT = 150;
			}
		}
		if (evade == true) {
			if (((Player)o.get(0)).getX()<this.getX()) {
				walkRight(delta);
				jump(m);
			}else {
				walkLeft(delta);
				jump(m);
			}
			evasionT = evasionT-delta;
			if (evasionT <= 0) {
				evade = false;
			}
		}
		//Attacking
		if ((canSeeCharacter((Character)o.get(0),m,500) && alive)){
			shoot(o, (int)((Player)o.get(0)).getX(), (int)((Player)o.get(0)).getY());
			if (((Player)o.get(0)).getX()<this.getX()) {
				walkLeft(delta);
			}else {
				walkRight(delta);
			}
		}
		// Jumping
		if (!checkMap(m,-36,64)&&(canSeeCharacter((Character)o.get(0),m,500))){
			jump(m);
		}
		if (!checkMap(m,36,64)&&(canSeeCharacter((Character)o.get(0),m,500))){
			jump(m);
		}
		
		//updates the hand
		alien_m.update(delta);
		if (canSeeCharacter((Character)o.get(0),m)) {
			mouse = new Vector2f(((Player)o.get(0)).getP());
		}
	}
	/**
	 * Draws the Alien
	 * @author Pasi Toivanen (hand) Tommi Heikkinen (the rest)
	 * @param g Ghraphics object of the gamestate
	 */
	public void display(Graphics g) {
		super.display(g);

		if ( mouse.getX() < p.getX() ) lookingRight = false; else lookingRight = true;	 
		
		alien_m.getCurrentFrame().getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2-10,this.getY()-height/2);
		//draw hand
		if ( alien_m.getFrame() == 0 ) hotSpot.set(-6,-10);
		if ( alien_m.getFrame() == 1 ) hotSpot.set(-6,-9);
		if ( alien_m.getFrame() == 2 ) hotSpot.set(0,-9);
		if ( alien_m.getFrame() == 3 ) hotSpot.set(6,-8);
		if ( alien_m.getFrame() == 4 ) hotSpot.set(13,-9);
		if ( alien_m.getFrame() == 5 ) hotSpot.set(6,-9);
		if ( alien_m.getFrame() == 6 ) hotSpot.set(4,-9);
		
		if (!lookingRight) {
			hotSpot.set(hotSpot.getX()*-1+8,hotSpot.getY());
		}
		
		aim = new Vector2f(mouse);
		aim.sub(p);
		if ( lookingRight ) {
			hand.setRotation((float)aim.getTheta());
			hand.draw(this.getX()+hotSpot.getX() - 32,this.getY()+ hotSpot.getY()-32);
		} else {
			handFlipped.setRotation((float)aim.getTheta());
			handFlipped.draw(this.getX()+hotSpot.getX()-32,this.getY()+hotSpot.getY()-32);
		}
		
	}
		public Vector2f getMouse() {
			return mouse;
	}
}
