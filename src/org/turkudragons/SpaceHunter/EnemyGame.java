package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EnemyGame extends BasicGameState {

	public static int id = 1;
	private Player player;
	private String deltaNumber = "0";
	private Input input;
	private ArrayList<Object> oList;
	private Map m;
	private boolean initialized;
	private Music story;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			oList = new ArrayList<Object>();
			m = new Map();
			player = new Player(350,300);
			oList.add(player);
			/*oList.add(new Blockade_Barrel(100,100));
			oList.add(new Flame_Tank(600,200));
			oList.add(new Dragonling_Drone(500,500));*/
			oList.add(new Alien(600,200));
			oList.add(new Mecha_Dragon(300,300));
			input = gc.getInput();
			m.add(1,1,0,9);
			m.add(1, 1, 5, 9);
			story = new Music("res/Story.ogg");
			initialized = true;
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame stg, Graphics g) throws SlickException {
		//player.display(g);
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
			story.loop(1,0.1f);
			initialized = false;
		}
		player.updateInput(gc, m, delta, oList);
		
		//player.update(oList,m,delta);
		for (int i = oList.size()-1; i >= 0; i--) {
			Object o = oList.get(i);
			if ( o instanceof Active) {
				((Active)o).update(oList, m, delta);
			}
		}

		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			story.stop();
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
		
		deltaNumber = "Delta: " + delta;
		// god-mode
		if (input.isKeyDown(Input.KEY_F)) {
			oList.add(new Item(100,400,Collect.randomItem()));
		}
		if (input.isKeyDown(Input.KEY_G) && oList.size() > 1) {
			oList.remove(1);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Emptygame.id;
	}
	
}
