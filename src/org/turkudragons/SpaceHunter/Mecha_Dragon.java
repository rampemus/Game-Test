package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
public class Mecha_Dragon extends Character implements Visible, Active {
	
	boolean alive = true;
	
	public Mecha_Dragon (int defx, int defy) {
		super(defx, defy);
		width = 120;
		height = 120;
		hitBox = new Rectangle(defx, defy, width, height);
		xMaxSpeed = 1.01f;
		friction = 0.01f;
		jumpStrength = 0.9f;
		elasticity = 0.2f;
		hp = 50000;
		airborne = true;
		
		//lacks animations stuff
		
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
	}
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
	}
	public void display(Graphics g) {
		super.display(g);
		//is missing draw
	}
}
