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

public class Blockade_Barrel extends Character implements Visible, Active{
//anything at all
	Image b;
	Image barrel;
	Image barrel1;
	Image barrel2;
	Image barrel3;
	Image barrel4;
	Image barrel5;
	Image[] barrels = new Image [7];
	Animation barrel_phases;
	
	public Blockade_Barrel(int defx, int defy) {
		super(defx,defy);
		hitBox = new Rectangle(defx, defy, 31, 54);
		xMaxSpeed = 0.3f;
		friction = 0.01f;
		jumpStrength = 0.5f;
		elasticity = 0.2f;
		hp = 100;
		
		try {
			b = new Image ("C:\\Users\\tommi\\Desktop\\T\\Pictures\\Sprite art\\blank.png");
			barrel = new Image ("C:\\Users\\tommi\\Desktop\\T\\Pictures\\Sprite art\\barrel.png");
			barrel1 = new Image ("C:\\Users\\tommi\\Desktop\\T\\Pictures\\Sprite art\\barrel_b1.png");
			barrel2 = new Image ("C:\\Users\\tommi\\Desktop\\T\\Pictures\\Sprite art\\barrel_b2.png");
			barrel3 = new Image ("C:\\Users\\tommi\\Desktop\\T\\Pictures\\Sprite art\\barrel_b3.png");
			barrel4 = new Image ("C:\\Users\\tommi\\Desktop\\T\\Pictures\\Sprite art\\barrel_b4.png");
			barrel5 = new Image ("C:\\Users\\tommi\\Desktop\\T\\Pictures\\Sprite art\\barrel_b5.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		barrels[0] = barrel;
		barrels[1] = barrel1;
		barrels[2] = barrel2;
		barrels[3] = barrel3;
		barrels[4] = barrel4;
		barrels[5] = barrel5;
		barrels[6] = b;
		barrel_phases = new Animation(barrels,100,false);
	}
	
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		if (((Character) o.get(0)).getX() > this.getX() +10) {
			if (((Character) o.get(0)).getX() < this.getX() +100){
				walkRight(delta);
			}
		}
		if (((Character) o.get(0)).getX() < this.getX() -10) {
			if (((Character) o.get(0)).getX() > this.getX() -100){
			walkLeft(delta);
			}
		}
		if((((Character) o.get(0)).getX() < (this.getX() +10))&& (((Character) o.get(0)).getX() > (this.getX() -10)))  {
			barrel_phases.update(100);
		}
		if(barrel_phases.getFrame() == 3) {
			barrel_phases.setAutoUpdate(true);
		}
		if (barrel_phases.getFrame() == 6) {
			barrel_phases.stop();
			o.remove(this);
		}
	}

	public void display(Graphics g) {
		super.display(g);
		barrel_phases.draw(this.getX()-width/2-16,this.getY()-height/2-5 );
	}
	
}

