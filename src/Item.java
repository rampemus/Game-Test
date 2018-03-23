import java.util.Random;

public class Item extends Collider implements Active,Visible{

	public Item(int x, int y) {
		super(x, y);
		Random r = new Random();
		v.set(((float)r.nextInt(200))/100-0.5f,((float)r.nextInt(200))/100-0.5f);
		elasticity = 0.5f;
		xMaxSpeed = 10f;
	}

}
