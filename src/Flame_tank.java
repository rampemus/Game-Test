import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class Flame_tank extends Character implements Visible, Active {
	Image blank;
	Image ft1;
	Image ft2;
	Image ftb1;
	Image ftb2;
	Image ftb3;
	Image ftb4;
	Image[] ftank = new Image [7];
	Animation Flame_Tank;
	boolean alive = true;

	public Flame_Tank(int defx, int defy) {
		super(defx,defy);
	}

}
