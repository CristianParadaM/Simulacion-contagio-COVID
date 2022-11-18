package model;

public class Person {
	private Coordinate[] positions;
	
	public Person (Coordinate positionStart, int k, int c) {
		this.positions = generatePositions(k, c, positionStart);
	}

	private Coordinate[] generatePositions(int k, int c, Coordinate positionStart) {
		double[] pseudorandomX = GenerateRi.getInstance().generateRiArray(k, c, 9);
		double[] pseudorandomY = GenerateRi.getInstance().generateRiArray(k+2, c+2, 9);
		Coordinate[] positions = new Coordinate[365];
		positions[0] = positionStart;
		int x = 0, y = 0;
		for (int i = 1; i < positions.length; i++) {	
			x = pseudorandomX[i] > 0.5?1:-1;
			y = pseudorandomY[i] > 0.5?1:-1;
			if (positions[i-1].getX()+x == 0 || positions[i-1].getX()+x == 99 || positions[i-1].getY()+y == 0 || positions[i-1].getY()+y == 99) {
				positions[i] = new Coordinate(positions[i-1].getX(), positions[i-1].getY(), positionStart.getState());
			}else {
				positions[i] = new Coordinate(positions[i-1].getX()+x, positions[i-1].getY()+y, positionStart.getState());
			}
			if ((positions[i].getState() == State.SICK_WITH_MASK || positions[i].getState() == State.SICK_WITHOUT_A_MASK) && i >= 28) {
				positions[i].setState(State.RECOVERED);
			}
		}
		return positions;
	}
	
	public boolean setState(State state, int instantOfTime) {
		if (instantOfTime < 365) {
			positions[instantOfTime].setState(state);
			return true;
		}
		return false;
	}
	
	public Coordinate getCoordinate(int instantOfTime) {
		return positions[instantOfTime];
	}
	
}
