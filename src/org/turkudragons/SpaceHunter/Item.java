package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * Class of all collectable items in the game. Holds information of which type of item it is and how much does it hold items.
 * Constructor takes care of how big amount of ammo or health is included. If it is not declared in the constructor which type
 * of item is needed then a random item is dropped to the x and y coordinates.
 * @author Pasi Toivanen
 *
 */
public class Item extends Collider implements Active,Visible{
	private Collect type;
	private int amount;
	private int age;
	private Image texture;
	
	public Item(int x, int y, Collect type) {
		super(x, y);
		Random r = new Random();
		v.set(((float)r.nextInt(200))/100-0.5f,((float)r.nextInt(200))/100-0.5f);
		elasticity = 0.5f;
		xMaxSpeed = 10f;
		invulnerable = true;
		age = 10000;
		this.type = type;
		switch (type) {
			case HP : 
				amount = 500;
				try {
					texture = new Image("res/HP.png");
				}catch(SlickException e) {
					
				}
				break;
			case HP_LARGE : 
				amount = 5000;
				try {
					texture = new Image("res/HPL.png");
				}catch(SlickException e) {
					
				}
				break;
			case ASSAULT_AMMO :
				amount = 200;
				try {
					texture = new Image("res/a_ammo.png");
				}catch(SlickException e) {
					
				}
				break;
			case SNIPER_AMMO :
				amount = 20;
				try {
					texture = new Image("res/s_ammo.png");
				}catch(SlickException e) {
					
				}
				break;
			case ROCKET : 
				amount = 10;
				try {
					texture = new Image("res/r_ammo.png");
				}catch(SlickException e) {
					
				}
				break;
			case GRENADE :
				amount = 10;
				try {
					texture = new Image("res/g_ammo.png");
				}catch(SlickException e) {
					
				}
				break;
			case PUMP_SHOTGUN_AMMO :
				amount = 40;
				try {
					texture = new Image("res/p_ammo.png");
				}catch(SlickException e) {
					
				}
				break;
			case FLAMETHROWER_AMMO :
				amount = 250;
				try {
					texture = new Image("res/f_ammo.png");
				}catch(SlickException e) {
					
				}
				break;
			case DOUBLE_DAMAGE :
				amount = 10000;
				try {
					texture = new Image("res/double.png");
				}catch(SlickException e) {
					
				}
				break;
			case INFINITE_AMMO :
				amount = 10000;
				try {
					texture = new Image("res/infinite.png");
				}catch(SlickException e) {
					
				}
				break;
			case INVULNERABILITY :
				amount = 10000;
				try {
					texture = new Image("res/invulnerability.png");
				}catch(SlickException e) {
					
				}
				break;
		}
	}
	
	/**
	 * display the item on the screen. Now it is mostly for debugging. Need to implement real graphics here.
	 */
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		//g.draw(hitBox);
		texture.draw(p.getX()-33,p.getY()-35);
	}
	
	/**
	 * normal update and then check if hp is too low
	 */
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		age -= delta;
		if(age < 0) {
			o.remove(this);
		}
	}
	
	/**
	 * for player interactions
	 * @return
	 */
	public int getAmount() { return amount; }
	public Collect getType() { return type; }
}

enum Collect {
	HP, ASSAULT_AMMO, SNIPER_AMMO, ROCKET, GRENADE, PUMP_SHOTGUN_AMMO, FLAMETHROWER_AMMO, DOUBLE_DAMAGE, INFINITE_AMMO, INVULNERABILITY, HP_LARGE;
	
	//method for getting a random item
	private static Random r = new Random();
	private static final List<Collect> items = Collections.unmodifiableList(Arrays.asList(values()));
	public static Collect randomItem() {
		return items.get(r.nextInt(items.size()));
	}
}