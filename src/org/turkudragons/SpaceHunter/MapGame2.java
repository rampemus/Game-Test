package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MapGame2 extends BasicGameState {
	
	public static int id = 3;
	private Player player;
	//private String deltaNumber = "0";
	private Input input;
	private boolean isTile;
	private boolean initialized = false;
	private ArrayList<Object> oList = new ArrayList<Object>();
	private Map m = new Map();

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			player = new Player(350,3700);
			oList.add(player);
			oList.add(new Blockade_Barrel(3456,3712));
			
			oList.add(new Alien(2048,3136));
			oList.add(new Alien(3100,3136));
			oList.add(new Alien(2400,3136));
			oList.add(new Alien(2800,3136));
			
			oList.add(new Dragonling_Drone(2800,3712));
			oList.add(new Dragonling_Drone(2200,3712));
			oList.add(new Dragonling_Drone(2500,3712));
			
			oList.add(new Alien(1220,2200));
			oList.add(new Alien(1440,2200));
			oList.add(new Alien(2000,2200));
			oList.add(new Alien(1780,2200));
			
			oList.add(new Alien(550,1536));
			oList.add(new Alien(1320,1536));
			oList.add(new Flame_Tank(600,1536));
			oList.add(new Flame_Tank(1100,1536));
			oList.add(new Flame_Tank(800,1536));
			
			oList.add(new Blockade_Barrel(3100,1500));
			oList.add(new Alien(2900,1500));
			
			
			input = gc.getInput();
			
			m.add(1,4,2,59);
			m.add(2,4,10,58);
			m.add(2,4,12,57);
			m.add(2,4,14,56);
			m.add(2,4,16,55);
			
			m.add(1,4,19,55);
			m.add(2,5,17,52);
			m.add(2,5,23,50);
			
			m.add(4,4,29,59);
			m.add(1,4,30,59);
			m.add(1,4,40,59);
			m.add(2,4,47,58);
			m.add(2,4,47,57);
			
			m.add(1,5,30,46);
			m.add(1,5,40,46);
			m.add(1,5,30,50);
			m.add(1,5,40,50);
			
			m.add(2,4,50,59);
			m.add(2,4,53,59);
			m.add(2,4,54,56);
			m.add(2,4,51,53);
			m.add(4,4,56,59);
			m.add(4,4,56,54);
			m.add(2,4,54,49);
			
			m.add(2,5,34,43);
			m.add(2,5,37,40);
			m.add(2,5,40,37);
			m.add(2,5,44,35);
			m.add(2,5,47,35);
			
			
			m.add(2,5,32,35);
			m.add(2,5,24,35);
			m.add(2,5,15,35);
			m.add(1,5,15,36);
			m.add(1,5,25,36);
			
			m.add(2,5,21,32);
			m.add(2,5,24,29);
			m.add(1,4,14,26);
			m.add(1,4,4,26);
			m.add(5,4,19,25);
			m.add(5,4,7,25);
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
		player.updateInput(gc, m,delta, oList);
		player.update(oList,m,delta);
		for (int i = oList.size()-1; i >= 0; i--) {
			Object o = oList.get(i);
			if ( o instanceof Active) {
				((Active)o).update(oList, m, delta);
			}
		}
		if(oList.size()<2) {
			sbg.enterState(BossMap.id);
		}
		if (player.getHP() <= 0) {
			sbg.enterState(GameOver.id);
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
		return MapGame2.id;
	}
	
}
