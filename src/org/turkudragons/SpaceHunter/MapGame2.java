package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MapGame2 extends BasicGameState {

	public static int id = 3;
	private Player player;
	// private String deltaNumber = "0";
	private Input input;
	private boolean isTile;
	private boolean initialized = false;
	private ArrayList<Object> oList;
	private Map m;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		oList = new ArrayList<Object>();
		m = new Map();
		// player and enemies
		try {
			player = ((Main) sbg).getOldPlayer();
			player.p = new Vector2f(350, 3700);
		} catch (NullPointerException e) {
			player = new Player(350, 3700);
		}
		oList.add(player);
		oList.add(new Blockade_Barrel(3456, 3712));

		oList.add(new Alien(2048, 3136));
		oList.add(new Alien(3100, 3136));
		oList.add(new Alien(2400, 3136));
		oList.add(new Alien(2800, 3136));

		oList.add(new Dragonling_Drone(2800, 3712));
		oList.add(new Dragonling_Drone(2200, 3712));
		oList.add(new Dragonling_Drone(2500, 3712));

		oList.add(new Alien(1220, 2200));
		oList.add(new Alien(1440, 2200));
		oList.add(new Alien(2000, 2200));
		oList.add(new Alien(1780, 2200));

		oList.add(new Alien(550, 1536));
		oList.add(new Alien(1320, 1536));
		oList.add(new Flame_Tank(600, 1536));
		oList.add(new Flame_Tank(1100, 1536));
		oList.add(new Flame_Tank(800, 1536));

		oList.add(new Blockade_Barrel(3100, 1500));
		oList.add(new Alien(2900, 1500));

		input = gc.getInput();

		// map for the state
		m.add(1, 4, 2, 58);

		m.add(4, 4, 2, 57);
		m.add(4, 4, 2, 53);
		m.add(4, 4, 2, 49);
		m.add(4, 4, 2, 45);
		m.add(4, 4, 2, 41);
		m.add(4, 4, 2, 37);
		m.add(4, 4, 2, 33);
		m.add(4, 4, 2, 29);
		m.add(4, 4, 2, 25);
		m.add(4, 4, 2, 21);

		m.add(2, 4, 10, 57);
		m.add(2, 4, 12, 56);
		m.add(2, 4, 14, 55);
		m.add(2, 4, 16, 54);

		m.add(1, 4, 19, 54);
		m.add(2, 5, 17, 51);
		m.add(2, 5, 23, 49);

		m.add(4, 4, 29, 58);
		m.add(1, 4, 30, 58);
		m.add(1, 4, 40, 58);
		m.add(2, 4, 47, 57);
		m.add(2, 4, 47, 56);

		m.add(1, 5, 30, 45);
		m.add(1, 5, 40, 45);
		m.add(1, 5, 30, 49);
		m.add(1, 5, 40, 49);

		m.add(2, 4, 50, 58);
		m.add(2, 4, 53, 58);
		m.add(2, 4, 54, 55);
		m.add(2, 4, 51, 52);
		m.add(4, 4, 56, 58);
		m.add(4, 4, 56, 53);

		m.add(4, 4, 56, 49);
		m.add(4, 4, 56, 44);
		m.add(4, 4, 56, 39);
		m.add(4, 4, 56, 34);
		m.add(4, 4, 56, 29);

		m.add(2, 4, 54, 48);

		m.add(2, 5, 34, 42);
		m.add(2, 5, 37, 39);
		m.add(2, 5, 40, 36);
		m.add(2, 5, 44, 34);
		m.add(2, 5, 47, 34);

		m.add(2, 5, 32, 34);
		m.add(2, 5, 24, 34);
		m.add(2, 5, 15, 34);
		m.add(1, 5, 15, 35);
		m.add(1, 5, 25, 35);

		m.add(2, 5, 21, 31);
		m.add(2, 5, 24, 28);
		m.add(1, 4, 14, 25);
		m.add(1, 4, 4, 25);
		m.add(5, 4, 19, 24);
		m.add(5, 4, 7, 24);
		
		initialized = true;

	}

	@Override
	public void render(GameContainer gc, StateBasedGame stg, Graphics g) throws SlickException {
		g.translate(-(player.getX() - 400), -(player.getY() - 300));

		m.display();
		//player.display(g);
		for (Object o : oList) {
			if (o instanceof Visible) {
				((Visible) o).display(g);
			}
		}
		g.drawString("isTile:" + isTile, 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (initialized) {
			initialized = false;
		}
		player.updateInput(gc, m,delta, oList);
		//player.update(oList,m,delta);
		for (int i = oList.size()-1; i >= 0; i--) {
			Object o = oList.get(i);
			if (o instanceof Active) {
				((Active) o).update(oList, m, delta);
			}
		}
		if (oList.size() < 2) {
			((MapGame) sbg.getState(MapGame.id)).backgroundMusic.stop();
			((Main) sbg).setOldPlayer(player);
			sbg.getState(BossMap.id).init(gc, sbg);
			sbg.enterState(BossMap.id);
		}
		if (player.getHP() <= 0) {
			((MapGame) sbg.getState(MapGame.id)).backgroundMusic.stop();
			((Main) sbg).setOldPlayer(null);
			sbg.getState(GameOver.id).init(gc, sbg);
			sbg.enterState(GameOver.id);
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			((MapGame) sbg.getState(MapGame.id)).backgroundMusic.stop();
			((Main) sbg).setOldPlayer(null);
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
		isTile = m.isTile(input.getMouseX(), input.getMouseY());

		// deltaNumber = "Delta: " + delta;
		// god-mode
		if (input.isKeyDown(Input.KEY_F)) {
			oList.add(new Item(100, 400, Collect.randomItem()));
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
