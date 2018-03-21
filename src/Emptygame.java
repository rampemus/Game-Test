import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Emptygame extends BasicGameState {
	
	public static int id = 1;
	private Player player;
	private String deltaNumber = "0";
	private ArrayList<Item> iList = new ArrayList<Item>();
	private ArrayList<Bullet> bList = new ArrayList<Bullet>();
	private Input input;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player = new Player(350,300);
		input = gc.getInput();
		Ammo.createBullets();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame stg, Graphics g) throws SlickException {
		player.display(g);
		for (Collider c : iList) {
			c.display(g);
		}
		for (Bullet b : bList) {
			b.display(g);
		}
		g.drawString(deltaNumber,100,100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		player.update(delta);
		player.updateInput(gc,delta);
		for (Collider c : iList) {
			c.update(delta);
		}
		deltaNumber = "Delta: " + delta;
		if (input.isKeyDown(Input.KEY_F)) {
			iList.add(new Item(100,400));
		}
		if (input.isKeyDown(Input.KEY_G) && iList.size() > 0) {
			iList.remove(0);
		}
		if(input.isMouseButtonDown(0)) {
			bList.add(new Bullet((int)player.getX(), (int)player.getY(), input.getMouseX(), input.getMouseY(), Ammo.getBullets().get(0)));
		}
		for (Bullet b : bList) {
			b.update(delta);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Emptygame.id;
	}
	
	public void throwCollider() {
		
	}
	
}
