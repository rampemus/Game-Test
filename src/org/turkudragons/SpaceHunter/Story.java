package org.turkudragons.SpaceHunter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Story extends BasicGameState{

	public static final int id = 6;
	Input input;
	String[] story = {"Date: 20XX, March 6th",
						"Location: Orbiting TLX-278 (Sigmar System)",
						"Weather: 17,2% chance of meteor showers, 42,8% chance for UV-burst",
						"Entry: After 3 months of silence, I received my first job request in a while, quite",
						" a big one at that. Assignment of slaying several important targets across the ",
						"system, with cash payment after finishing! The price is high enough to warrant ",
						"an early retirement for anybody, and their gramma. It would be stupid ",
						"not to accept this one. Besides, my gramma really needs a new swimming pool."};
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input = gc.getInput();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("(PRESS ESC TO RETURN)", 140, 10);
		for ( int i = 0; i < story.length; i++) {
			g.drawString(story[i], 10, 120 + 20*i);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			sbg.enterState(Menu.id);
		}
	}

	@Override
	public int getID() {
		return Story.id;
	}

}
