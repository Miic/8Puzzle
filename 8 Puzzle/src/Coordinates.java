
public class Coordinates {
	private int x, y;
	private String actionIdentifier;
	
	public Coordinates(int x, int y) {
		this.setX(x);
		this.y = y;
	}

	public Coordinates(int x, int y, String actionIdentified) {
		this.setX(x);
		this.y = y;
		actionIdentifier = actionIdentified;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public String getActionIdentifier() {
		return actionIdentifier;
	}

	public void setActionIdentifier(String actionIdentifier) {
		this.actionIdentifier = actionIdentifier;
	}
}
