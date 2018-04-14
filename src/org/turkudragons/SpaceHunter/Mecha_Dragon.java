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
	Image blank;
	Image ds1;
	Image dr1;
	Image dr2;
	Image dr3;
	Image[] Dragon = new Image[3];
	Animation Dragon_Boss;
	
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
		try {
		dr1 = new Image("res/Mecha_Dragon_w.png");
		dr2 = new Image("res/Mecha_Dragon_w2.png");
		dr3 = new Image("res/Mecha_Dragon_w3.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dragon[0] = dr1;
		Dragon[1] = dr2;
		Dragon[2] = dr3;
		
		Dragon_Boss = new Animation(Dragon,100,true);
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
		Dragon_Boss.getCurrentFrame().getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2,this.getY()-height/2 );
	}
}
