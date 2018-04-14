package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;

public class Menu extends BasicGameState {
	
	public static int id = 0;
	private String deltaNumber = "0";
	Image play;
	Image story;
	Image EnemyTest;
	Image MapTest;
	Image exit;
	Music titleScreen;
	private boolean initialized;
	
	private ArrayList<Object> oList = new ArrayList<Object>();

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			play = new Image("res/play.png");
			story = new Image("res/story.png");
			EnemyTest = new Image("res/EnemyTest.png");
			MapTest = new Image("res/MapTest.png");
			exit = new Image("res/exit.png");
			titleScreen = new Music("res/TitleScreen.ogg");
			initialized = true;
		}
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
		if(initialized) {
			titleScreen.loop(1.0f,0.1f);
			initialized = false;
		}
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		
		/*play button
		if((mouseX>100 && mouseX<280) && (mouseY>400 && mouseY<460)){
			if(Mouse.isButtonDown(0)){
				titleScreen.stop();
				sbg.enterState(5);
			}
		}
		*/
		
		//Story button
		if((mouseX>100 && mouseX<280) && (mouseY>330 && mouseY<390)){
			if(Mouse.isButtonDown(0)){
<<<<<<< HEAD
				sbg.enterState(6);
=======
				titleScreen.stop();
				sbg.enterState(4);
>>>>>>> 7345791e9224b22a3ffd320a7933eb0aa595a64d
			}
		}
		
		//EnemyGame button
		if((mouseX>100 && mouseX<280) && (mouseY>260 && mouseY<320)){
			if(Mouse.isButtonDown(0)){
				//sbg.enterState(EnemyGame.id);
				titleScreen.stop();
				sbg.enterState(EnemyGame.id);
			}
		}
		
		//MapGame button
		if((mouseX>100 && mouseX<280) && (mouseY>190 && mouseY<250)){
			if(Mouse.isButtonDown(0)){
				titleScreen.stop();
				sbg.enterState(MapGame.id);
			}
		}
		
		//exit button
		if((mouseX>100 && mouseX<280) && (mouseY>120 && mouseY<180)){
			if((Mouse.isButtonDown(0))){
				titleScreen.stop();
				System.exit(0);
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