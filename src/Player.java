import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Character {
	public Player(int x, int y) {
		super(x,y);
	}
	
	public void updateInput(GameContainer gc, int delta) {
		
		Input input = gc.getInput();
		
		//horozontal movement
		if ( input.isKeyDown(Input.KEY_D)) {
			walkRight(delta);
		} else if ( input.isKeyDown(Input.KEY_A)) {
			walkLeft(delta);
		} 
		
		//jump mecanic
		if ( input.isKeyDown(Input.KEY_SPACE) ){
			jump();
		}
	}
	
	public void pullItems(ArrayList<Item> items, int delta) {
		for (Item item : items) {
			Vector2f a = new Vector2f(p);
			a.sub(item.getP());
			float distance = a.length();
			a.normalise();
			a.scale(delta/distance);
			item.vAdd(a, delta);
		}
	}
}
