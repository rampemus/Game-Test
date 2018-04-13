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
	
	public static int id = 0;
	private String deltaNumber = "0";
	private Input input;
	Image play;
	Image story;
	Image EnemyTest;
	Image MapTest;
	Image exit;
	
	private ArrayList<Object> oList = new ArrayList<Object>();

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		play = new Image("res/play.png");
		story = new Image("res/story.png");
		EnemyTest = new Image("res/EnemyTest.png");
		MapTest = new Image("res/MapTest.png");
		exit = new Image("res/exit.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame stg, Graphics g) throws SlickException {

		play.draw(100, 140, 180, 60);
		story.draw(100, 210, 180, 60);
		EnemyTest.draw(100, 280, 180, 60);
		MapTest.draw(100, 350, 180, 60);
		exit.draw(100, 420, 180, 60);
		
		
		for (Object o : oList) {
			if ( o instanceof Visible) {
				((Visible)o).display(g);
			}
		}
		g.drawString(deltaNumber,100,100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		
		/*play button
		if((mouseX>100 && mouseX<280) && (mouseY>400 && mouseY<460)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(5);
			}
		}
		*/
		
		/*story button - STATE NOT CREATED, crashes on launch
		if((posX>100 && posX<280) && (posY>330 && posY<390)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(4);
			}
		}
		*/
		
		//EnemyGame button
		if((mouseX>100 && mouseX<280) && (mouseY>260 && mouseY<320)){
			if(Mouse.isButtonDown(0)){
				//sbg.enterState(EnemyGame.id);
				sbg.enterState(MapGame.id);
			}
		}
		
		//MapGame button
		if((mouseX>100 && mouseX<280) && (mouseY>290 && mouseY<320)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(MapGame.id);
			}
		}
		
		/*exit button
		if((mouseX>100 && mouseX<280) && (mouseY>120 && mouseY<180)){
			if((Mouse.isButtonDown(0))){
				System.exit(0);
			}
		}
		*/
		
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