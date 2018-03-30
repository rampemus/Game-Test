import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MapGame2 extends BasicGameState {
	
	public static int id = 2;
	private Player player;
	private String deltaNumber = "0";
	private Input input;
	private boolean isTile;
	
	private ArrayList<Object> oList = new ArrayList<Object>();
	private Map m = new Map();

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player = new Player(350,300);
		oList.add(player);
		input = gc.getInput();
		
		m.add(1,1,0,15);
		m.add(1,1,0,20);
		m.add(4,1,0,19);
		m.add(2,1,10,21);
		m.add(2,1,12,22);
		m.add(2,1,14,23);
		m.add(2,1,16,24);
		m.add(2,1,18,25);
		m.add(1,1,20,26);
		m.add(1,1,30,26);
		m.add(4,1,37,25);
		m.add(3,1,15,13);
		m.add(3,1,21,10);
		m.add(3,1,28,14);
		m.add(3,1,34,8);
		
	
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
		
		deltaNumber = "Delta: " + delta;
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
