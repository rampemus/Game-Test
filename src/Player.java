import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Player extends Character {
	public Player(int x, int y) {
		super(x,y);
	}
	
	public void updateInput(GameContainer gc, int delta) {
		
		Input input = gc.getInput();
		
		//horozontal movement
		if ( input.isKeyDown(Input.KEY_D)) {
			walkRight(delta);
		} else if ( input.isKeyDown(Input.KEY_A)) {
			walkLeft(delta);
		} 
		
		//jump mecanic
		if ( input.isKeyDown(Input.KEY_SPACE) ){
			jump();
		}
	}
}
