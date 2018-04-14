package org.turkudragons.SpaceHunter;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
public class Mecha_Dragon extends Character implements Visible, Active {
	
	boolean alive = true;
	Image blank;
	Image ds1;
	Image dr1;
	Image dr2;
	Image dr3;
	Image drm1;
	Image drm2;
	Image drm3;
	Image[] Dragon = new Image[60];
	Animation Dragon_Boss;
	
	public Mecha_Dragon (int defx, int defy) { //(1500,3740) default lokaatio
		super(defx, defy);
		width = 120;
		height = 120;
		hitBox = new Rectangle(defx, defy, width, height);
		xMaxSpeed = 1.01f;
		friction = 0.01f;
		jumpStrength = 0.9f;
		elasticity = 0.2f;
		hp = 50000;
		airborne = true;
		try {
		dr1 = new Image("res/Mecha_Dragon_w.png");
		dr2 = new Image("res/Mecha_Dragon_w2.png");
		dr3 = new Image("res/Mecha_Dragon_w3.png");
		drm1 = new Image("res/Mecha_Dragon_wm.png");
		drm2 = new Image("res/Mecha_Dragon_wm2.png");
		drm3 = new Image("res/Mecha_Dragon_wm3.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dragon[0] = dr1;
		Dragon[1] = dr2;
		Dragon[2] = dr3;
		Dragon[3] = dr1;
		Dragon[4] = dr2;
		Dragon[5] = dr3;
		Dragon[6] = dr1;
		Dragon[7] = dr2;
		Dragon[8] = dr3;
		Dragon[9] = dr1;
		Dragon[10] = dr2;
		Dragon[11] = dr3;
		Dragon[12] = dr1;
		Dragon[13] = dr2;
		Dragon[14] = dr3;
		Dragon[15] = drm1;
		Dragon[16] = drm2;
		Dragon[17] = drm3;
		Dragon[18] = drm1;
		Dragon[19] = drm2;
		Dragon[20] = drm3;
		Dragon[21] = dr1;
		Dragon[22] = dr2;
		Dragon[23] = dr3;
		Dragon[24] = dr1;
		Dragon[25] = dr2;
		Dragon[26] = dr3;
		Dragon[27] = dr1;
		Dragon[28] = dr2;
		Dragon[29] = dr3;
		Dragon[30] = dr1;
		Dragon[31] = dr2;
		Dragon[32] = dr3;
		Dragon[33] = dr1;
		Dragon[34] = dr2;
		Dragon[35] = dr3;
		Dragon[36] = dr1;
		Dragon[37] = dr2;
		Dragon[38] = dr3;
		Dragon[39] = dr1;
		Dragon[40] = dr2;
		Dragon[41] = dr3;
		Dragon[42] = drm1;
		Dragon[43] = drm2;
		Dragon[44] = drm3;
		Dragon[45] = drm1;
		Dragon[46] = drm2;
		Dragon[47] = drm3;
		Dragon[48] = drm1;
		Dragon[49] = drm2;
		Dragon[50] = drm3;
		Dragon[51] = drm1;
		Dragon[52] = drm2;
		Dragon[53] = drm3;
		Dragon[54] = drm1;
		Dragon[55] = drm2;
		Dragon[56] = drm3;
		Dragon[57] = drm1;
		Dragon[58] = drm2;
		Dragon[59] = drm3;
		
		Dragon_Boss = new Animation(Dragon,250,true);
		//lacks animations stuff
		
		for(Weapon w : weapons) {
			w.setEnemy(true);
		}
	}
	public void update(ArrayList<Object> o, Map m, int delta) {
		super.update(o, m, delta);
		
		Dragon_Boss.update(delta);
	}
	public void display(Graphics g) {
		super.display(g);
		Dragon_Boss.getCurrentFrame().getFlippedCopy(!lookingRight, false).draw(this.getX()-width/2,this.getY()-height/2 );
	}
}
