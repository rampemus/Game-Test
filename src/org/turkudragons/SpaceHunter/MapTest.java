package org.turkudragons.SpaceHunter;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MapTest extends StateBasedGame {

	public MapTest(String name) {
		super(name);
	}

	public static void main(String[] args) {

		AppGameContainer appgc;
		Weapon.createWeapons();
		try {
			Sounds.initiateSounds();
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		
		try {
			appgc = new AppGameContainer(new MapTest("Testataan miten mäppi toimii"));
			appgc.setDisplayMode(800, 600, false);
			appgc.setAlwaysRender(true);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		addState(new MapGame2());
		addState(new MapGame());
		addState(new BossMap());
		//MapGame.id = 1;
		//MapGame2.id = 2;
	}

}
