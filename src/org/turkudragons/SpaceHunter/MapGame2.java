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
			//tunnel enemy placement
			oList.add(new Flame_Tank(2048,3136));
			oList.add(new Flame_Tank(3100,3136));
			oList.add(new Flame_Tank(2400,3136));
			oList.add(new Flame_Tank(2800,3136));
			//another path 
			oList.add(new Dragonling_Drone(2800,3712));
			oList.add(new Dragonling_Drone(2200,3712));
			oList.add(new Dragonling_Drone(2500,3712));
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
		}
		
		isTile = m.isTile(input.getMouseX(), input.getMouseY());
		
		//deltaNumber = "Delta: " + delta;
		// god-mode
		if (input.isKeyDown(Input.KEY_F)) {
			oList.add(new Item(100,400));
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
