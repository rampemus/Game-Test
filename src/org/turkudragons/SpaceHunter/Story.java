package org.turkudragons.SpaceHunter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Story extends BasicGameState{

	public static final int id = 6;
	private boolean initialized;
	Input input;
	private Music storyM;
	String[] story = {	"Date: 20XX, March 6th",
						"Location: Orbiting TLX-278 (Sigmar System)",
						"Weather: 17,2% chance of meteor showers, 42,8% chance for UV-burst",
						"Entry: After 3 months of silence, I received my first job request in a while, quite",
						" a big one at that. Assignment of slaying several important targets across the ",
						"system, with cash payment after finishing! The price is high enough to warrant ",
						"an early retirement for anybody, and their gramma. It would be stupid ",
						"not to accept this one. Besides, my gramma really needs a new swimming pool.",
						"",
						"Date: 20XX, March 9th\n",
						"Location: Asteroid belt between Sigmar & Machina systems\n",
						"Weather: 100% meteors & occasional warships\n",
						"Entry: Moving on to the location “Classified” to begin mission. These targets ",
						"include monsters, political figures, criminals etc. No wonder they’re wanted dead.",
						" Lucky for them, they hired the right man for the job, my glorious reputation ",
						"exceeds me! ",
						"I only wish there’s something more than just monsters in these space rocks, ",
						"without a good restaurant how will I be able to keep up my perfect diet?",
						"",
						"Date: 20XX, March 12th\n", 
						"Location: Classified\n" ,
						"Weather: Lots of aliens\n",
						"Entry: Beginning mission, estimated time of completion minimal, failures are ",
						"unacceptable, as my name knows no defeat, no challenge that’s too great and no danger",
						"I cannot face! Ancis Bastion Rexas Diablo Frey XXI, moving out!"
						};
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if(!initialized) {
			storyM = new Music("res/Story.ogg");
			input = gc.getInput();
			initialized = true;
		}
		
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("(PRESS ESC TO RETURN)", 140, 10);
		for ( int i = 0; i < story.length; i++) {
			g.drawString(story[i], 10, 60 + 20*i);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(initialized) {
			storyM.loop(1,0.1f);
			initialized = false;
		}
		// TODO Auto-generated method stub
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			storyM.stop();
			sbg.getState(Menu.id).init(gc, sbg);
			sbg.enterState(Menu.id);
		}
	}

	@Override
	public int getID() {
		return Story.id;
	}

}
