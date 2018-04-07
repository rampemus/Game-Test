import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;
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

	public Item(int x, int y) {
		super(x, y);
		Random r = new Random();
		v.set(((float)r.nextInt(200))/100-0.5f,((float)r.nextInt(200))/100-0.5f);
		elasticity = 0.5f;
		xMaxSpeed = 10f;
		hp = 1;
		type = Collect.randomItem();
		amount = 100;
	}
	
	/**
	 * display the item on the screen. Now it is mostly for debugging. Need to implement real graphics here.
	 */
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		g.draw(hitBox);
		
		//This will be changed into a real graphics
		String displayLetter = "";
		switch (type) {
			case HP : 
				displayLetter = "H";
				break;
			case PISTOL_AMMO :
				displayLetter = "P";
				break;
			case ASSAULT_AMMO :
				displayLetter = "A";
				break;
			case SNIPER_AMMO :
				displayLetter = "S";
				break;
			case ROCKET : 
				displayLetter = "R";
				break;
			case GRENADE :
				displayLetter = "G";
				break;
		}
		g.drawString(displayLetter,p.getX()-width/2+3,p.getY()-height/2-1);
	}
	
	/**
	 * normal update and then check if hp is too low
	 */
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		if ( hp < 0) {
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
	HP, PISTOL_AMMO, ASSAULT_AMMO, SNIPER_AMMO, ROCKET, GRENADE;
	
	//method for getting a random item
	private static Random r = new Random();
	private static final List<Collect> items = Collections.unmodifiableList(Arrays.asList(values()));
	public static Collect randomItem() {
		return items.get(r.nextInt(items.size()));
	}
}