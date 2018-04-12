import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Character {
	private int maxItemSwallowDistance;
	private Image character;
	private Vector2f dp = new Vector2f(-12f,-32f); //position of the drone
	private Vector2f mouse = new Vector2f(0,0);
	private Vector2f v;
	private Shape r;
	
	public Player(int x, int y) {
		super(x,y);
		weapons = Weapon.getWeapons();
		
		maxItemSwallowDistance = height;
		try {
			character = new Image ("/res/main_char_stand1.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hp = 1000;
		jumpStrength = 0.8f;
		r = new Rectangle(x,y,1,1000);
	}
	
	public void updateInput(GameContainer gc, Map m, int delta, ArrayList<Object> oList) {
		
		Input input = gc.getInput();
		
		mouse.set(input.getMouseX()+(int)getX()-400,input.getMouseY()+(int)getY() - 300);
		
		//horozontal movement
		if ( input.isKeyDown(Input.KEY_D)) {
			walkRight(delta);
		} else if ( input.isKeyDown(Input.KEY_A)) {
			walkLeft(delta);
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
		if(input.isKeyPressed(input.KEY_1)) {
			currentWeapon = weapons.get(0);
		}
		if(input.isKeyPressed(input.KEY_2)) {
			currentWeapon = weapons.get(1);
		}
		if(input.isKeyPressed(input.KEY_3)) {
			currentWeapon = weapons.get(2);
		}
		if(input.isKeyPressed(input.KEY_4)) {
			currentWeapon = weapons.get(3);
		}
		if(input.isKeyPressed(input.KEY_5)) {
			currentWeapon = weapons.get(4);
		}
		if(input.isKeyPressed(input.KEY_6)) {
			currentWeapon = weapons.get(5);
		}
		if(input.isKeyPressed(input.KEY_7)) {
			currentWeapon = weapons.get(6);
		}
		if(input.isKeyPressed(input.KEY_8)) {
			currentWeapon = weapons.get(7);
		}
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		pullItems(o,delta);
	}
	
	public void display(Graphics g) {
		super.display(g);
		v = new Vector2f(0,0);
		v.sub(p);
		v.add(getMouse());
		r.setLocation(this.getX(), this.getY());
		//g.draw(r.transform(Transform.createRotateTransform((float)v.getTheta() * 0.01745329252f - 1.57079632679f, this.getX(), this.getY())));
		character.getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2-16,this.getY()-height/2);
	}
	
	public void collectItem(Item i) {
		if (i.getType() == Collect.HP) {
			hp += i.getAmount();
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
	
	public Shape getLineOfFire() {
		return r.transform(Transform.createRotateTransform((float)v.getTheta() * 0.01745329252f - 1.57079632679f, this.getX(), this.getY()));
	}
}
