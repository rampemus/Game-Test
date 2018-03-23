import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Enemy_Test extends StateBasedGame {
	
	public Enemy_Test(String name) {
		super(name);
	}

	public static void main(String[] args) {

		AppGameContainer appgc;
		Weapon.createAmmo();
		
		try {
			appgc = new AppGameContainer(new Enemy_Test("Testataan vihollisten toimintaa"));
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
		addState(new EnemyGame());
	}

}

