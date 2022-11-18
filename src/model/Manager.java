package model;

import view.JFrameMain;

public class Manager {
	private Person[] persons;
	private int amountPersons;
	private double ri[];
	private int count;

	public Manager(int numberOfSickWithMask, int numberOfSickWithoutMask, int numberOfNormalWithMask, int numberOfNormalWithoutMask, int k, int c) {
		this.amountPersons = calculateAmountPerson(numberOfNormalWithMask, numberOfNormalWithoutMask, numberOfSickWithMask, numberOfSickWithoutMask);
		this.persons = generatePersons(numberOfNormalWithMask, numberOfNormalWithoutMask, numberOfSickWithMask, numberOfSickWithoutMask, k, c);
		this.ri = generateRi(k, c);
		count = ri.length - 1;
		startSimulation();
	}

	private double[] generateRi(int k, int c) {
		return GenerateRi.getInstance().generateRiArray(k, c, 15);
	}

	private void startSimulation() {
		JFrameMain.createProgress(0, 365, "Generando simulacion");
		for (int i = 0; i < 365; i++) {
			JFrameMain.setProgressBar(i);
			for (int j = 0; j < persons.length; j++) {
				comparePosition(persons[j], i);
			}
		}
		JFrameMain.disposeDialog();
	}

	private void comparePosition(Person person, int instantOfTime) {
		for (int i = 0; i < persons.length; i++) {
			if (persons[i] != person && persons[i].getCoordinate(instantOfTime).isEquals(person.getCoordinate(instantOfTime))) {
				analyzePossibleContagion(person, persons[i], instantOfTime);
				analyzePossibleContagion(persons[i], person, instantOfTime);
			}
		}
	}

	private void analyzePossibleContagion(Person person, Person person2, int instantOfTime) {
		if (person.getCoordinate(instantOfTime).getState() == State.SICK_WITH_MASK) {
			if (person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITH_MASK) {
				if (ri[count--] < 0.015) {
					estimateRecoveryTime(person2, instantOfTime, State.SICK_WITH_MASK);
				}
			}else if(person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITHOUT_A_MASK) {
				if (ri[count--] < 0.05) {
					estimateRecoveryTime(person2, instantOfTime, State.SICK_WITHOUT_A_MASK);
				}
			}
		}else if(person.getCoordinate(instantOfTime).getState() == State.SICK_WITHOUT_A_MASK) {
			if (person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITH_MASK) {
				if (ri[count--] < 0.7) {
					estimateRecoveryTime(person2, instantOfTime, State.SICK_WITH_MASK);
				}
			}else if(person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITHOUT_A_MASK) {
				if (ri[count--] < 0.9) {
					estimateRecoveryTime(person2, instantOfTime, State.SICK_WITHOUT_A_MASK);
				}
			}
		}
	}

	private void estimateRecoveryTime(Person person2, int instantOfTime, State state) {
		int count = 1;
		for (int i = instantOfTime; i < 365; i++, count++) {
			if (i > 364) {
				break;
			}
			if (count < 29) {
				person2.getCoordinate(i).setState(state);
			}else {
				person2.getCoordinate(i).setState(State.RECOVERED);
			}
		}
	}

	private int calculateAmountPerson(int numberOfNormalWithMask, int numberOfNormalWithoutMask,
			int numberOfSickWithMask, int numberOfSickWithoutMask) {
		return numberOfNormalWithMask+numberOfNormalWithoutMask+numberOfSickWithMask+numberOfSickWithoutMask;
	}

	private Person[] generatePersons(int numberOfNormalWithMask, int numberOfNormalWithoutMask,
			int numberOfSickWithMask, int numberOfSickWithoutMask, int k, int c) {
		int g = 0, count = 0;
		for (g = 0; Math.pow(2, g) < amountPersons; g++);
		g = g < 7?7:g+1;
		double[] positionsStartX = new DistribucionUniforme(0, 100, GenerateRi.getInstance().generateRiArray(k, c, g)).generatePseudorandomNumbers();
		double[] positionsStartY = new DistribucionUniforme(0, 100, GenerateRi.getInstance().generateRiArray(k+2, c+2, g)).generatePseudorandomNumbers();
		Person[] persons = new Person[amountPersons];
		JFrameMain.createProgress(0, persons.length, "Generando Personas");
		for (int i = 0; i < persons.length; i++, count++) {
			JFrameMain.setProgressBar(i);
			Coordinate positionStart = new Coordinate((int)positionsStartX[count], (int)positionsStartY[count], getState(i, numberOfNormalWithMask, numberOfNormalWithoutMask, numberOfSickWithMask, numberOfSickWithoutMask));
			while (positionStart.getX() == 0 || positionStart.getX() == 99 || positionStart.getY() == 0 || positionStart.getY() == 99) {
				count++;
				positionStart.setX((int)positionsStartX[count]);
				positionStart.setY((int)positionsStartY[count]);
			}
			persons[i] = new Person(positionStart, (int)positionsStartX[count], (int)positionsStartY[count]);
		}
		JFrameMain.disposeDialog();
		return persons;
	}

	private State getState(int i, int numberOfNormalWithMask, int numberOfNormalWithoutMask, int numberOfSickWithMask,
			int numberOfSickWithoutMask) {
		if (i < numberOfNormalWithMask) {
			return State.NORMAL_WITH_MASK;
		}else if(i < numberOfNormalWithMask + numberOfNormalWithoutMask) {
			return State.NORMAL_WITHOUT_A_MASK;
		}else if(i < numberOfNormalWithMask+numberOfNormalWithoutMask+numberOfSickWithMask) {
			return State.SICK_WITH_MASK;
		}else {
			return State.SICK_WITHOUT_A_MASK;
		}
	}
	
	public int[] calculateNumberPeopleInState(int instantOfTime) {
		int[] aux = new int[3];
		for (int i = 0; i < persons.length; i++) {
			if (persons[i].getCoordinate(instantOfTime).getState() == State.NORMAL_WITH_MASK || persons[i].getCoordinate(instantOfTime).getState() == State.NORMAL_WITHOUT_A_MASK) {
				aux[0] += 1;
			}else if (persons[i].getCoordinate(instantOfTime).getState() == State.SICK_WITH_MASK || persons[i].getCoordinate(instantOfTime).getState() == State.SICK_WITHOUT_A_MASK) {
				aux[1] += 1;
			}else {
				aux[2] += 1;
			}
		}
		return aux;
	}
	
	/**
	 * Metodo para obtener las posiciones de las personas y sus estados en un instante de tiempo
	 * @param instantOfTime instante de tiempo
	 * @return posiciones en una matriz de double
	 */
	public double[][] getPositions(int instantOfTime){
		double [][] positions = new double [persons.length][3];
		for (int i = 0; i < positions.length; i++) {
			Coordinate coordinate = persons[i].getCoordinate(i);
			positions[i][0] = coordinate.getX();
			positions[i][1] = coordinate.getY();
			positions[i][0] = coordinate.getState().ordinal();
		}
		return positions;
	}
}
