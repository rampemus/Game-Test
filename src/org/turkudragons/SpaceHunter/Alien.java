package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.geom.Rectangle;

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
	
	private Vector2f aim;
	private Vector2f mouse;

	
	public Alien(int defx, int defy) {
		super(defx,defy);
		width = 37;
		height = 22;
		hitBox = new Rectangle(defx, defy, width, height);
		hp = 1000;
		aim = new Vector2f(100, 0);
		jumpStrength = 0.8f;
		xMaxSpeed = 0.6f;
		
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
			
			alien_m = new Animation(alien_a,100,true);
			
			hand = new Image("res/humanoid_hand.png");
			handFlipped = hand.getFlippedCopy(false, true);
			hotSpot = new Vector2f(0,0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void updateInput(GameContainer gc, Map m, int delta, ArrayList<Object> oList) {
		
		Input input = gc.getInput();
		
		mouse.set(input.getMouseX()+(int)getX()-400,input.getMouseY()+(int)getY() - 300);
		
		// animation
		if(player_m.getFrame() == 0) {
			player_m.stop();
		}
		if (player_m.getFrame()==8) {
			player_m.setCurrentFrame(1);
		}
		//if (!input.isKeyDown(Input.KEY_D)&&!input.isKeyDown(Input.KEY_A)) {
			
		//}
		if (input.isKeyDown(Input.KEY_D)||input.isKeyDown(Input.KEY_A)) {
			player_m.start();
		} else {
			player_m.setCurrentFrame(0);
		}
		player_m.update(delta);
		//horizontal movement
		if ( input.isKeyDown(Input.KEY_D)) {
			walkRight(delta);
		} else if ( input.isKeyDown(Input.KEY_A)) {
			walkLeft(delta);
		} 
		
		//vertical movement, when airborne
		if ( input.isKeyDown(Input.KEY_W)) {
			ascend(delta);
		} else if ( input.isKeyDown(Input.KEY_S)) {
			descend(delta);
		} 
		
		//jetpack by pressing J (for testing flying)
		if ( input.isKeyDown(Input.KEY_J)) {
			airborne = true;
		} else {
			airborne = false;
		}
		
		//jump mecanic
		if ( input.isKeyDown(Input.KEY_SPACE) ){
			jump(m);
		}
		
		//shoot!!!
		if ( input.isMouseButtonDown(0)) {
			shoot(oList, input.getMouseX()+(int)getX()-400,input.getMouseY()+(int)getY() - 300);
		}
		
		if(currentWeapon.getName().equals("Grenade-Launcher")) {
			shoot(oList, input.getMouseX()+(int)getX()-400,input.getMouseY()+(int)getY() - 300, true);
		}
		
		//Change weapon
		if(org.lwjgl.input.Mouse.hasWheel()) {
			int x = org.lwjgl.input.Mouse.getDWheel();
			int y = weapons.indexOf(currentWeapon);
			if(x < 0 && y > 0) {
				currentWeapon = weapons.get(y-1);
			}
			if(x > 0 && y < 7) {
				currentWeapon = weapons.get(y+1);
			}
		}
		if(input.isKeyPressed(Input.KEY_1)) {
			currentWeapon = weapons.get(0);
		}
		if(input.isKeyPressed(Input.KEY_2)) {
			currentWeapon = weapons.get(1);
		}
		if(input.isKeyPressed(Input.KEY_3)) {
			currentWeapon = weapons.get(2);
		}
		if(input.isKeyPressed(Input.KEY_4)) {
			currentWeapon = weapons.get(3);
		}
		if(input.isKeyPressed(Input.KEY_5)) {
			currentWeapon = weapons.get(4);
		}
		if(input.isKeyPressed(Input.KEY_6)) {
			currentWeapon = weapons.get(5);
		}
		if(input.isKeyPressed(Input.KEY_7)) {
			currentWeapon = weapons.get(6);
		}
		if(input.isKeyPressed(Input.KEY_8)) {
			currentWeapon = weapons.get(7);
		}
	}*/
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		// animation
		if(alien_m.getFrame() == 0) {
			alien_m.stop();
		}
		if (alien_m.getFrame()==6) {
			alien_m.setCurrentFrame(1);
		}
		alien_m.update(delta);
	}
	
	public void display(Graphics g) {
		super.display(g);
		//updateViewArea();
		
		
		aim = new Vector2f(0,0);

		//g.draw(r.transform(Transform.createRotateTransform((float)v.getTheta() * 0.01745329252f - 1.57079632679f, this.getX(), this.getY())));
		/*if ( mouse.getX() < p.getX() ) lookingRight = false; else lookingRight = true;
		alien_m.getCurrentFrame().getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2-16,this.getY()-height/2);		 
		 */
		alien_m.getCurrentFrame().getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2-7,this.getY()-height/2-20 );
		//draw hand
		if ( alien_m.getFrame() == 0 ) hotSpot.set(-8,-10);
		if ( alien_m.getFrame() == 1 ) hotSpot.set(-6,-11);
		if ( alien_m.getFrame() == 2 ) hotSpot.set(-2,-12);
		if ( alien_m.getFrame() == 3 ) hotSpot.set(-2,-12);
		if ( alien_m.getFrame() == 4 ) hotSpot.set(-1,-12);
		if ( alien_m.getFrame() == 5 ) hotSpot.set(-2,-12);
		if ( alien_m.getFrame() == 6 ) hotSpot.set(-2,-12);
		
		if (!lookingRight) {
			hotSpot.set(hotSpot.getX()*-1,hotSpot.getY());
		}
		/*
		aim = new Vector2f(mouse);
		aim.sub(p);
		if ( lookingRight ) {
			hand.setRotation((float)aim.getTheta());
			hand.draw(this.getX()+hotSpot.getX() - 32,this.getY()+ hotSpot.getY()-32);
		} else {
			handFlipped.setRotation((float)aim.getTheta());
			handFlipped.draw(this.getX()+hotSpot.getX()-32,this.getY()+hotSpot.getY()-32);
		}
		*/
		//getFlippedCopy(!lookingRight, false)
		
	}
	
	public Vector2f getMouse() {
		return mouse;
	}
}
