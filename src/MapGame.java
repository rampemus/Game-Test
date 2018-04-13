import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MapGame extends BasicGameState {
	
	public static int id = 2;
	private Player player;
	private String deltaNumber = "0";
	private Input input;
	private boolean isTile;
	private Music backgroundMusic;
	private boolean initialized = false;
	private ArrayList<Object> oList = new ArrayList<Object>();
	private Map m = new Map();

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			player = new Player(350,300);
			oList.add(player);
			oList.add(new Blockade_Barrel(100,100));
			oList.add(new Dragonling_Drone(500,50));
			input = gc.getInput();
			m.add(2,4,4,6);
			m.add(2,4,0,8);
			m.add(2,4,7,5);
			m.add(2,4,10,4);
			m.add(3,1,12,11);
			m.add(1,1,0,9);
			m.add(1,1,3,13);
			m.add(4,1,3,13);
			m.add(1,1,10,13);
			m.add(2,1,7,20);
			m.add(4,1,20,13);
			try {
				backgroundMusic = new Music("/res/Music.ogg");
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
		player.updateInput(gc, m,delta, oList);
		player.update(oList,m,delta);
		for (int i = oList.size()-1; i >= 0; i--) {
			Object o = oList.get(i);
			if ( o instanceof Active) {
				((Active)o).update(oList, m, delta);
			}
		}
		if(oList.size()<2) {
			sbg.enterState(MapGame2.id);
		}
		
		isTile = m.isTile(input.getMouseX(), input.getMouseY());
		
		deltaNumber = "Delta: " + delta;
		// god-mode
		if (input.isKeyDown(Input.KEY_F)) {
			oList.add(new Item(100,400));
		}
		if(input.isKeyPressed(Input.KEY_M)) {
			backgroundMusic.loop(1.0f,0.1f);
		}
		if (input.isKeyDown(Input.KEY_G) && oList.size() > 1) {
			oList.remove(1);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return MapGame.id;
	}
	
}
