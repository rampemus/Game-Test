import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class Blockade_Barrel extends Character implements Visible, Active{
//anything at all
	public Blockade_Barrel() {
		super(100,100);
		hitBox = new Rectangle(100, 100, 31, 54);
	}
	public void attack(int delta) {
			walkRight(delta);
	}
}

