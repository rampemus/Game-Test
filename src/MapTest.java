

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
		addState(new MapGame());
		addState(new MapGame2());
	}

}
