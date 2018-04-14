package org.turkudragons.SpaceHunter;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

public class MenuButton extends BasicGameState {
	
	private int posX;
	private int posY;
	private int drawToX;
	private int drawToY;
	private Image image;
	private Input input;
	
	public MenuButton(){
		this.posX=posX;
		this.posY=posY;
		this.drawToX=drawToX;
		this.drawToY=drawToY;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//whole section copied from Menu for easier referencing; needs overhaul to make usable
		
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		
		//play button
		if((mouseX>100 && mouseX<300) && (mouseY>350 && mouseY<400)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(5);
			}
		}
		
		if((posX>100 && posX<300) && (posY>190 && posY<240)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(4);
			}
		}
		
		//EnemyGame button
		if((mouseX>100 && mouseX<300) && (mouseY>280 && mouseY<330)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(3);
			}
		}
		
		//MapGame button
		if((mouseX>100 && mouseX<300) && (mouseY>210 && mouseY<260)){
			if(Mouse.isButtonDown(0)){
				sbg.enterState(2);
			}
		}
		
		//exit button
		if((mouseX>100 && mouseX<300) && (mouseY>120 && mouseY<170)){
			if((Mouse.isButtonDown(0))){ 
				System.exit(0);
			}
		}
		//exit via esc
		if(input.isKeyPressed(input.KEY_ESCAPE)){
			System.exit(0);
		}
		
		for (int i = oList.size()-1; i > 0; i--) {
			Object o = oList.get(i);
			if ( o instanceof Active) {
				((Active)o).update(oList, m, delta);
			}
		}

		deltaNumber = "Delta: " + delta;
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}