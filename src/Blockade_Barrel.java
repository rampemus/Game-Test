import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public class Blockade_Barrel extends Collider implements Visible{
//anything at all
	public Blockade_Barrel() {
		super(100,100);
		hitBox = new Rectangle(100, 100, 31, 54);
	}
	@Override
	public void update(ArrayList<Object> o, Map m, int delta) {
		o.add(new Blockade_Barrel());
	}
}
