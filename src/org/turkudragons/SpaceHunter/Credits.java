package org.turkudragons.SpaceHunter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Credits extends BasicGameState {

	private int y;
	String[] credits = {	
			"Turku dragons esittää",
			"SpaceHunter",
			"",
			"",
			"Pasi Toivanen",
			"",
			"Santeri Loitomaa",
			"",
			"Tommi Heikkinen", 
			"",
			"Janina Kuosmanen",
			"",
			"Olli Parviainen",
			""
			};
	String maks = "unacceptable, as my name knows no defeat, no challenge that’s too great and no danger";
	Input input;
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		y = 0;
		input = gc.getInput();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for ( int i = 0; i < credits.length; i++) {
			
			//tulosta ylemmät rivit kun ne ovat valmistuneet
				g.drawString(credits[i], 10, 600 - y + 20*i);
			
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		y += delta;
		if (input.isKeyDown(Input.KEY_ESCAPE)||input.isKeyDown(Input.KEY_ENTER)|input.isKeyDown(Input.KEY_SPACE)) {
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
		if ( y > 40000) {
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
