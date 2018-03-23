import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Character {
	private int maxItemSwallowDistance;
	
	public Player(int x, int y) {
		super(x,y);
		maxItemSwallowDistance = height;
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
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		pullItems(o,delta);
	}
	
	public void collectItem(Item i) {
		//tähän instance of, muutetaan ammusten määrää playerillä mitä lie juttuja TÄHÄN
	}
	
	public void pullItems(ArrayList<Object> items, int delta) {
		for (int i = items.size()-1; i >= 0; i--) {
			if ( items.get(i) instanceof Item) {
				Item item = (Item)items.get(i);
				Vector2f a = new Vector2f(p);
				a.sub(item.getP());
				float distance = a.length();
				if (distance < 10) {
					collectItem(item);
					items.remove(item);
					continue;
				}
				if ( distance < maxItemSwallowDistance) {
					a.normalise();
					a.scale(delta/distance/2);
					item.vAdd(a, delta);
				}
			}
		}
	}
}
