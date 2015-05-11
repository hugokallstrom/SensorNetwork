/**
 * Created by hugo on 5/11/15.
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

    public boolean isNeighbour(Position myPosition) {
        if(myPosition.getX() - 1 == this.x || myPosition.getX() + 1 == this.x) {
            return true;
        } else if (myPosition.getY() - 1 == this.y || myPosition.getY() + 1 == this.y) {
            return true;
        } else {
            return false;
        }
    }
}
