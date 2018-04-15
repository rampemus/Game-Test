package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Blockade_Barrel extends Character implements Visible, Active{
//anything at all
	Image b;
	Image barrel;
	Image barrel1;
	Image barrel2;
	Image barrel3;
	Image barrel4;
	Image barrel5;
	Image[] barrels = new Image [7];
	Animation barrel_phases;
	boolean alive = true;
	private int drops;
	
	/**
	 * Creates the character to certain coordinates and creates all necessary animations etc.
	 * @param x x-coordinate of player
	 * @param y y-coordinate of player
	 */
	public Blockade_Barrel(int defx, int defy) {
		super(defx,defy);
		width = 31;
		height = 54;
		hitBox = new Rectangle(defx, defy, width, height);
		xMaxSpeed = 0.3f;
		friction = 0.01f;
		jumpStrength = 0.5f;
		elasticity = 0.2f;
		hp = 1000;
		Random r = new Random();
		drops = r.nextInt(3)+2;
		
		try {
			b = new Image ("res/blank.png");
			barrel = new Image ("res/Barrel.png");
			barrel1 = new Image ("res/Barrel_b1.png");
			barrel2 = new Image ("res/Barrel_b2.png");
			barrel3 = new Image ("res/Barrel_b3.png");
			barrel4 = new Image ("res/Barrel_b4.png");
			barrel5 = new Image ("res/Barrel_b5.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		barrels[0] = barrel;
		barrels[1] = barrel1;
		barrels[2] = barrel2;
		barrels[3] = barrel3;
		barrels[4] = barrel4;
		barrels[5] = barrel5;
		barrels[6] = b;
		barrel_phases = new Animation(barrels,100,true);
		barrel_phases.stop();
		
		
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
	}
	/**
	 * updates the character on the playground. Super.update-has all the kinematics and common stuff with character
	 * sits still and drops stuff
	 * @param o oList of the gamestate
	 * @param m Map of the gamestate
	 * @param delta How many steps of millisecs to be taken
	 */
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		
		//Animation & drops
		if(hp <=600 && alive)  {
			barrel_phases.setCurrentFrame(1);
		}
		if(hp <=300 && alive)  {
			barrel_phases.setCurrentFrame(2);
		}
		if(hp<=0 && alive) {
			alive = false;
			barrel_phases.start();
		}
		if (barrel_phases.getFrame() == 6) {
			barrel_phases.stop();
			for(int i = 0; i < drops; i++) {
				o.add(new Item((int)this.getX(), (int)this.getY(), Collect.randomItem()));
			}
			o.remove(this);
		}
	}
	/**
	 * Draws the barrel
	 * @param g Ghraphics object of the gamestate
	 */
	public void display(Graphics g) {
		super.display(g);
		barrel_phases.draw(this.getX()-width/2-16,this.getY()-height/2-5 );
	}
	
}

