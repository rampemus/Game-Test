import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class Menu extends BasicGameState {
	
	public static int id = 1;
	private String deltaNumber = "0";
	private Input input;
	Image play;
	Image story;
	Image EnemyTest;
	Image MapTest;
	Image exit;
	
	private ArrayList<Object> oList = new ArrayList<Object>();
	private Map m;

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		play = new Image("res/play.png");
		story = new Image("res/story.png");
		EnemyTest = new Image("res/EnemyTest.png");
		MapTest = new Image("res/MapTest.png");
		exit = new Image("res/exit.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame stg, Graphics g) throws SlickException {

		play.draw(100, 300, 70, 120);
		story.draw(100, 300, 140, 190);
		EnemyTest.draw(100, 300, 210, 260);
		MapTest.draw(100, 300, 280, 330);
		exit.draw(100, 300, 350, 400);
		
		m.display();
		for (Object o : oList) {
			if ( o instanceof Visible) {
				((Visible)o).display(g);
			}
		}
		g.drawString(deltaNumber,100,100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		//play button
		if((posX>100 && posX<300) && (posY>350 && posY<400)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(4);
			}
		}
		
		/*story button - STATE NOT CREATED
		if((posX>100 && posX<300) && (posY>190 && posY<240)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState();
			}
		}
		*/
		
		//EnemyGame button
		if((posX>100 && posX<300) && (posY>280 && posY<330)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(3);
			}
		}
		
		//MapGame button
		if((posX>100 && posX<300) && (posY>210 && posY<260)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(2);
			}
		}
		
		//exit button
		if((posX>100 && posX<300) && (posY>120 && posY<170)){
			if((Mouse.isButtonDown(0)) || (input.isKeyPressed(input.KEY_ESCAPE))){ 
				System.exit(0);
			}
		}
		
		for (int i = oList.size()-1; i > 0; i--) {
			Object o = oList.get(i);
			if ( o instanceof Active) {
				((Active)o).update(oList, m, delta);
			}
		}

		deltaNumber = "Delta: " + delta;
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Menu.id;
	}
	
}