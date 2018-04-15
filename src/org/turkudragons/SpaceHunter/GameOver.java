package org.turkudragons.SpaceHunter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState{

	public static final int id = 5;
	public int timeout;
	private boolean initialized;
	private Music gameOver;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			gameOver = new Music("res/GameOver.ogg");
			timeout = 10000;
			initialized = true;
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Ya lost the game, you silly goose!", 140, 100);
		g.drawString("Restart after: " + timeout/1000 + " seconds...", 140, 120);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(initialized && Sounds.soundIsPlaying()) {
			gameOver.play(1,0.2f);
			initialized = false;
		}
		timeout -= delta;
		if (timeout < 0) {
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
	}

	@Override
	public int getID() {
		return GameOver.id;
	}

}
