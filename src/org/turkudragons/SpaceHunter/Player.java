package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Character {
	private int maxItemSwallowDistance;
	private Image player_s;
	private Image hand;
	private Image handFlipped;
	private Image player_w1;
	private Image player_w2;
	private Image player_w3;
	private Image player_w4;
	private Image player_w5;
	private Image player_w6;
	private Image player_w7;
	private Image blank;
	private Image [] player_a = new Image[10];
	private Animation player_m;
	
	private Vector2f mouse = new Vector2f(0,0);
	private Vector2f aim;
	private Shape r;
	private int doubleDamageTimer;
	private int infiniteAmmoTimer;
	private int invulnerabilityTimer;
	@SuppressWarnings("unused")
	private Polygon viewArea;
	
	public Player(int x, int y) {
		super(x,y);
		weapons = new ArrayList<Weapon>(Weapon.getWeapons());
		
		maxItemSwallowDistance = height;
		try {
			player_s = new Image ("res/main_char_stand1.png"); 	//0
			player_w1 = new Image ("res/main_char_walk1.png"); 	//1
			player_w2 = new Image ("res/main_char_walk2.png");	//2
			player_w3 = new Image ("res/main_char_walk3.png");	//3
			player_w4 = new Image ("res/main_char_walk4.png");	//4
			player_w5 = new Image ("res/main_char_walk5.png");	//5
			player_w6 = new Image ("res/main_char_walk6.png");	//6
			player_w7 = new Image ("res/main_char_walk7.png");	//7
			blank = new Image ("res/blank.png");					//8
			
			player_a[0] = player_s;
			player_a[1] = player_w1;
			player_a[2] = player_w2;
			player_a[3] = player_w3;
			player_a[4] = player_w4;
			player_a[5] = player_w5;
			player_a[6] = player_w6;
			player_a[7] = player_w7;
			player_a[8] = player_w7;
			player_a[9] = blank;
			
			player_m = new Animation(player_a,100,true);
			
			hand = new Image("res/player_hand.png");
			handFlipped = hand.getFlippedCopy(false, true);
			hotSpot = new Vector2f(0,0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hp = 10000;
		aim = new Vector2f(0, 0);
		jumpStrength = 0.8f;
		r = new Rectangle(x,y,1,1000);
		viewArea = new Polygon();
		xMaxSpeed = 0.6f;
	}
	
	public void updateInput(GameContainer gc, Map m, int delta, ArrayList<Object> oList) {
		
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
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		pullItems(o,delta);
		
		if(doubleDamageTimer > 0) {
			doubleDamageTimer -= delta;
			if(doubleDamageTimer <= 0) {
				for(Weapon w : weapons) {
					w.setDamage(w.getDamage()/2);
				}
				doubleDamageTimer = 0;
			}
		}
		if(infiniteAmmoTimer > 0) {
			infiniteAmmoTimer -= delta;
			if(infiniteAmmoTimer <= 0) {
				for(Weapon w : weapons) {
					if(!(w.getName().equals("Pistol"))) {
						w.setInfinite(false);
					}
				}
				infiniteAmmoTimer = 0;
			}
		}
		if(invulnerabilityTimer > 0) {
			invulnerabilityTimer -= delta;
			if(invulnerabilityTimer <= 0) {
				invulnerable = false;
				invulnerabilityTimer = 0;
			}
		}
	}
	
	public void display(Graphics g) {
		super.display(g);
		//updateViewArea();
		aim = new Vector2f(0,0);
		r.setLocation(this.getX(), this.getY());
		g.drawString("Invulnerability Timer: " + invulnerabilityTimer,getX()-90,getY()-height-30);
		g.drawString("Reload Timer: " + shootCooldown,getX()-90,getY()-height-45);
		//g.draw(r.transform(Transform.createRotateTransform((float)v.getTheta() * 0.01745329252f - 1.57079632679f, this.getX(), this.getY())));
		if ( mouse.getX() < p.getX() ) lookingRight = false; else lookingRight = true;
		player_m.getCurrentFrame().getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2-16,this.getY()-height/2);
		
		//draw hand
		if ( player_m.getFrame() == 0 ) hotSpot.set(-8,-10);
		if ( player_m.getFrame() == 1 ) hotSpot.set(-6,-11);
		if ( player_m.getFrame() == 2 ) hotSpot.set(-2,-12);
		if ( player_m.getFrame() == 3 ) hotSpot.set(-2,-12);
		if ( player_m.getFrame() == 4 ) hotSpot.set(-1,-12);
		if ( player_m.getFrame() == 5 ) hotSpot.set(-2,-12);
		if ( player_m.getFrame() == 6 ) hotSpot.set(-2,-12);
		if ( player_m.getFrame() == 7 ) hotSpot.set(-2,-12);
		if ( player_m.getFrame() == 8 ) hotSpot.set(-7,-12);
		if (!lookingRight) {
			hotSpot.set(hotSpot.getX()*-1,hotSpot.getY());
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
		
		//getFlippedCopy(!lookingRight, false)
	}
	
	/*
	private void updateViewArea() {
		viewArea = new Polygon();
		for ( int i = 0; i < 32; i++) {
			Vector2f point = new Vector2f(p.getX(),p.getY());
		}
	}*/

	public void collectItem(Item i) {
		Collect type = i.getType();
		int amount = i.getAmount();
		switch (type) {
			case HP : 
				hp += 100;
				break;
			case ASSAULT_AMMO :
				weapons.get(1).setCount(weapons.get(1).getCount() + amount);
				break;
			case SNIPER_AMMO :
				weapons.get(2).setCount(weapons.get(2).getCount() + amount);
				break;
			case ROCKET : 
				weapons.get(3).setCount(weapons.get(3).getCount() + amount);
				weapons.get(5).setCount(weapons.get(5).getCount() + amount);
				break;
			case GRENADE :
				weapons.get(4).setCount(weapons.get(4).getCount() + amount);
				break;
			case PUMP_SHOTGUN_AMMO :
				weapons.get(5).setCount(weapons.get(6).getCount() + amount);
				break;
			case FLAMETHROWER_AMMO :
				weapons.get(6).setCount(weapons.get(7).getCount() + amount);
				break;
			case DOUBLE_DAMAGE :
				if (doubleDamageTimer == 0) {
					for(Weapon w : weapons) {
						w.setDamage(w.getDamage()*2);
					}
					doubleDamageTimer = amount;
					break;
				}
			case INFINITE_AMMO :
				for(Weapon w : weapons) {
					w.setInfinite(true);
				}
				infiniteAmmoTimer = amount;
				break;
			case INVULNERABILITY :
				invulnerable = true;
				invulnerabilityTimer = amount;
				break;
		}
	}
	
	public void pullItems(ArrayList<Object> items, int delta) {
		for (int i = items.size()-1; i >= 0; i--) {
			if ( items.get(i) instanceof Item) {
				Item item = (Item)items.get(i);
				Vector2f a = new Vector2f(p);
				a.sub(item.getP());
				float distance = a.length();
				if (distance < 30) {
					collectItem(item);
					items.remove(i);
					continue;
				}
				if ( distance < maxItemSwallowDistance) {
					a.normalise();
					a.scale(delta/distance/20);
					item.vAdd(a, delta);
				}
			}
		}
	}
	
	public Vector2f getMouse() {
		return mouse;
	}
	
	public int getHP() {
		return hp;
	}
	
	public Shape getLineOfFire() {
		return r.transform(Transform.createRotateTransform((float)aim.getTheta() * 0.01745329252f - 1.57079632679f, this.getX(), this.getY()));
	}
}
