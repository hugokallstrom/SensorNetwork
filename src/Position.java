/**
 * Created by hugo on 5/11/15.
 */
/**
 * @author Viktor Lindbad
 *	Position objects in 
 *	the network
 */

public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

    public boolean isNeighbour(Position position) {
        if((position.getX() - 1 == this.x || position.getX() + 1 == this.x) && position.getY() == this.y) {
            return true;
        } else if ((position.getY() - 1 == this.y || position.getY() + 1 == this.y) && position.getX() == this.x) {
            return true;
        } else if (position.getX() - 1 == this.x && position.getY() - 1 == this.y) {
            return true;
        } else if (position.getX() - 1 == this.x && position.getY() + 1 == this.y) {
            return true;
        } else if (position.getX() + 1 == this.x && position.getY() - 1 == this.y) {
            return true;
        } else if (position.getX() + 1 == this.x && position.getY() + 1 == this.y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Position: " + String.valueOf(x) + " " + String.valueOf(y)+"\n";
    }
}
