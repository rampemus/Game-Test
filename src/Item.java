import java.util.Random;

public class Item extends Collider{

	public Item(int x, int y) {
		super(x, y);
		Random r = new Random();
		v.set(((float)r.nextInt(200))/200-0.5f,((float)r.nextInt(200))/200-0.5f);
	}

}
