package model;

public class Coordinate {
	private int x;
	private int y;
	private State state;
	
	public Coordinate(int x, int y, State state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public boolean isEquals(Coordinate coordinate) {
		return coordinate.getX() == this.x && coordinate.getY() == y;
	}
}
