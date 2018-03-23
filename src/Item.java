import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;

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
		type = Collect.HP;
		amount = 10;
	}
	
	public void display(Graphics g) {
		hitBox.setCenterX(p.getX());
		hitBox.setCenterY(p.getY());
		g.draw(hitBox);
		g.drawString("H",p.getX()-width/2+3,p.getY()-height/2-1);
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		if ( hp < 0) {
			o.remove(this);
		}
	}
	
	public int getAmount() { return amount; }
	
	public Collect getType() { return type; }
}

enum Collect {
	HP, PISTOL_AMMO, ASSAULT_AMMO, SNIPER_AMMO, ROCKET, GRENADE;
	
	private static Random r = new Random();
	private static final List<Collect> items = Collections.unmodifiableList(Arrays.asList(values()));
	public static Collect randomItem() {
		return items.get(r.nextInt());
	}
}