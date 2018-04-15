package org.turkudragons.SpaceHunter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Credits extends BasicGameState {
	public static final int id = 7;
	private boolean initialized;
	private int y;
	private int step;
	private Music storyM;
	String[] credits = {	
			"Turku dragons esittää - Space Hunter",
			"",
			"Graphics - Tommi Heikkinen",
			"Game Engine - Pasi Toivanen and Santeri Loitomaa",
			"Weapon design - Santeri Loitomaa",
			"Physics - Pasi Toivanen",
			"Story - Tommi Heikkinen",
			"Map architecture - Janina Kuosmanen and Olli Parviainen",
			"Map design - Janina Kuosmanen",
			"Menu System - Olli Parviainen",
			"Enemy Design - Tommi Heikkinen",
			"",
			"Music:" , 
			"Extreme Action - bensound.com (Menu music)" , 
			"For The Win 16 - Håkan Erikson (Level music)" , 
			"Drone Hunting - Niklas Johansson (Boss level music)" , 
			"Sci Fi - bensound.com (Story and Credits music)" , 
			"",
			"Sound effects - soundbible.com" , 
			"",
			"Special thanks to:" , 
			"Hanne" , 
			"Jonne Pohjankukka" , 
			""};
	Input input;
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		y = 0;
		input = gc.getInput();
		storyM = new Music("res/Story.ogg");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for ( int i = 0; i < credits.length; i++) {
			
			//tulosta ylemmät rivit kun ne ovat valmistuneet
				g.drawString(credits[i], 400-credits[i].length()*10/2, 600 - (int)y + 20*i);
			
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(initialized) {
			storyM.loop(1,0.1f);
			initialized = false;
		}
		step += delta;
		if ( step > 30 ) {
			y++;
			step = 0;
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)||input.isKeyDown(Input.KEY_ENTER)|input.isKeyDown(Input.KEY_SPACE)) {
			storyM.stop();
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
		if ( y > 40000) {
			storyM.stop();
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
