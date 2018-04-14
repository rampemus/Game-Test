package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class BossMap extends BasicGameState {
	
	public static int id = 4;
	private Player player;
	//private String deltaNumber = "0";
	private Input input;
	private boolean isTile;
	private boolean initialized = false;
	private Music bossTheme;
	private ArrayList<Object> oList = new ArrayList<Object>();
	private Map m = new Map();

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			player = new Player(700,3700);
			oList.add(player);
			oList.add(new Mecha_Dragon(1500,3740));
			
			input = gc.getInput();
			
			m.add(1,1,10,59);
			m.add(1,1,20,59);
			m.add(1,1,10,50);
			m.add(1,1,20,50);
			
			m.add(4,1,9,59);
			m.add(4,1,9,54);
		    m.add(4,1,30,59);
			m.add(4,1,30,54);
			

			m.add(5,1,13,57);
			m.add(5,1,25,57);
			m.add(2,1,9,54);
			m.add(2,1,28,54);
			
			try {
				//Drone Hunting by Niklas Johansson, downloaded from player.epidemicsound.com
				bossTheme = new Music("res/BossTheme.ogg");
			}catch(SlickException e) {
				
			}
			
			initialized = true;
		}
	}


	@Override
	public void render(GameContainer gc, StateBasedGame stg, Graphics g) throws SlickException {
		g.translate(-(player.getX()-400),-(player.getY()-300));
		
		m.display();
		player.display(g);
		for (Object o : oList) {
			if ( o instanceof Visible) {
				((Visible)o).display(g);
			}
		}
		g.drawString("isTile:" + isTile,100,100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(initialized) {
			bossTheme.loop(1.0f,0.1f);
			initialized = false;
		}
		player.updateInput(gc, m,delta, oList);
		player.update(oList,m,delta);
		for (int i = oList.size()-1; i >= 0; i--) {
			Object o = oList.get(i);
			if ( o instanceof Active) {
				((Active)o).update(oList, m, delta);
			}
		}
		if(oList.size()<2) {
			bossTheme.stop();
		}
		if (player.getHP() <= 0) {
			bossTheme.stop();
			sbg.enterState(GameOver.id);
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			bossTheme.stop();
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
		
		isTile = m.isTile(input.getMouseX(), input.getMouseY());
		
		//deltaNumber = "Delta: " + delta;
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
		return BossMap.id;
	}
	
}
