package model;

import java.util.ArrayList;

import view.JFrameMain;

public class Manager {
	private Person[] persons;
	private int amountPersons;
	private double ri[];
	private int count;
	private ArrayList<Double> timesSickPersons;
	private ArrayList<Double> timesNormalPersons;
	private ArrayList<Double> timesRecoveryPersons;
	private int days;
	private int range;

	public Manager(int numberOfSickWithMask, int numberOfSickWithoutMask, int numberOfNormalWithMask,
			int numberOfNormalWithoutMask, int k, int c) {
		this.range = 0;
		this.days = 365;
		this.amountPersons = calculateAmountPerson(numberOfNormalWithMask, numberOfNormalWithoutMask,
				numberOfSickWithMask, numberOfSickWithoutMask);
		this.persons = generatePersons(numberOfNormalWithMask, numberOfNormalWithoutMask, numberOfSickWithMask,
				numberOfSickWithoutMask, k, c);
		this.ri = generateRi(k, c);
		count = ri.length - 1;
		this.timesSickPersons = new ArrayList<Double>();
		this.timesNormalPersons = new ArrayList<Double>();
		this.timesRecoveryPersons = new ArrayList<Double>();
		startSimulation();
	}

	private double[] generateRi(int k, int c) {
		return GenerateRi.getInstance().generateRiArray(k, c, 15);
	}

	private void startSimulation() {
		JFrameMain.createProgress(0, this.days, "Generando simulacion");
		for (int i = 0; i < this.days; i++) {
			JFrameMain.setProgressBar(i);
			for (int j = 0; j < persons.length; j++) {
				comparePosition(persons[j], i);
			}
		}
		JFrameMain.disposeDialog();
	}

	public void addMorePositions() {
		int k = persons[0].getCoordinate(364).getX();
		int c = persons[0].getCoordinate(364).getY();
		this.ri = generateRi(k > c ? (k % 2 == 0 ? k : (k + 1)) : (c % 2 == 0 ? c : (c + 1)),
				k < c ? (k % 2 != 0 ? k : (k + 1)) : (c % 2 != 0 ? c : (c + 1)));
		count = ri.length - 1;
		JFrameMain.createProgress(0, this.days, "Generando más posiciones");
		for (int i = 0; i < persons.length; i++) {
			JFrameMain.setProgressBar(i);
			this.persons[i].addMorePositions();
		}
		JFrameMain.disposeDialog();
		JFrameMain.createProgress(0, this.days, "Generando más simulación");
		for (int i = 0; i < this.days; i++) {
			JFrameMain.setProgressBar(i);
			for (int j = 0; j < persons.length; j++) {
				comparePosition(persons[j], i);
			}
		}
		JFrameMain.disposeDialog();
	}

	private void comparePosition(Person person, int instantOfTime) {
		for (int i = 0; i < persons.length; i++) {
			if (persons[i] != person
					&& persons[i].getCoordinate(instantOfTime).isEquals(person.getCoordinate(instantOfTime))) {
				analyzePossibleContagion(person, persons[i], instantOfTime);
				analyzePossibleContagion(persons[i], person, instantOfTime);
			}
		}
	}

	public int getDays() {
		return days;
	}

	private void analyzePossibleContagion(Person person, Person person2, int instantOfTime) {
		if (person.getCoordinate(instantOfTime).getState() == State.SICK_WITH_MASK) {
			if (person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITH_MASK) {
				if (ri[count--] < 0.015) {
					estimateRecoveryTime(person2, instantOfTime, State.SICK_WITH_MASK);
				}
			} else if (person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITHOUT_A_MASK) {
				if (ri[count--] < 0.05) {
					estimateRecoveryTime(person2, instantOfTime, State.SICK_WITHOUT_A_MASK);
				}
			}
		} else if (person.getCoordinate(instantOfTime).getState() == State.SICK_WITHOUT_A_MASK) {
			if (person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITH_MASK) {
				if (ri[count--] < 0.7) {
					estimateRecoveryTime(person2, instantOfTime, State.SICK_WITH_MASK);
				}
			} else if (person2.getCoordinate(instantOfTime).getState() == State.NORMAL_WITHOUT_A_MASK) {
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
			} else {
				person2.getCoordinate(i).setState(State.RECOVERED);
			}
		}
	}

	private int calculateAmountPerson(int numberOfNormalWithMask, int numberOfNormalWithoutMask,
			int numberOfSickWithMask, int numberOfSickWithoutMask) {
		return numberOfNormalWithMask + numberOfNormalWithoutMask + numberOfSickWithMask + numberOfSickWithoutMask;
	}

	private Person[] generatePersons(int numberOfNormalWithMask, int numberOfNormalWithoutMask,
			int numberOfSickWithMask, int numberOfSickWithoutMask, int k, int c) {
		int g = 0, count = 0;
		for (g = 0; Math.pow(2, g) < amountPersons; g++)
			;
		g = g < 7 ? 7 : g + 1;
		double[] positionsStartX = new DistribucionUniforme(0, 100, GenerateRi.getInstance().generateRiArray(k, c, g))
				.generatePseudorandomNumbers();
		double[] positionsStartY = new DistribucionUniforme(0, 100,
				GenerateRi.getInstance().generateRiArray(k + 2, c + 2, g)).generatePseudorandomNumbers();
		Person[] persons = new Person[amountPersons];
		JFrameMain.createProgress(0, persons.length, "Generando Personas");
		for (int i = 0; i < persons.length; i++, count++) {
			JFrameMain.setProgressBar(i);
			Coordinate positionStart = new Coordinate((int) positionsStartX[count], (int) positionsStartY[count],
					getState(i, numberOfNormalWithMask, numberOfNormalWithoutMask, numberOfSickWithMask,
							numberOfSickWithoutMask));
			while (positionStart.getX() == 0 || positionStart.getX() == 99 || positionStart.getY() == 0
					|| positionStart.getY() == 99) {
				count++;
				positionStart.setX((int) positionsStartX[count]);
				positionStart.setY((int) positionsStartY[count]);
			}
			persons[i] = new Person(positionStart, (int) positionsStartX[count], (int) positionsStartY[count]);
		}
		JFrameMain.disposeDialog();
		return persons;
	}

	private State getState(int i, int numberOfNormalWithMask, int numberOfNormalWithoutMask, int numberOfSickWithMask,
			int numberOfSickWithoutMask) {
		if (i < numberOfNormalWithMask) {
			return State.NORMAL_WITH_MASK;
		} else if (i < numberOfNormalWithMask + numberOfNormalWithoutMask) {
			return State.NORMAL_WITHOUT_A_MASK;
		} else if (i < numberOfNormalWithMask + numberOfNormalWithoutMask + numberOfSickWithMask) {
			return State.SICK_WITH_MASK;
		} else {
			return State.SICK_WITHOUT_A_MASK;
		}
	}

	public int[] calculateNumberPeopleInState(int instantOfTime) {
		int[] aux = new int[3];
		for (int i = 0; i < persons.length; i++) {
			if (persons[i].getCoordinate(instantOfTime).getState() == State.NORMAL_WITH_MASK
					|| persons[i].getCoordinate(instantOfTime).getState() == State.NORMAL_WITHOUT_A_MASK) {
				aux[0] += 1;
			} else if (persons[i].getCoordinate(instantOfTime).getState() == State.SICK_WITH_MASK
					|| persons[i].getCoordinate(instantOfTime).getState() == State.SICK_WITHOUT_A_MASK) {
				aux[1] += 1;
			} else {
				aux[2] += 1;
			}
		}
		return aux;
	}

	/**
	 * Metodo para obtener las posiciones de las personas y sus estados en un
	 * instante de tiempo
	 * 
	 * @param instantOfTime instante de tiempo
	 * @return posiciones en una matriz de double
	 */
	private double[][] getPositions(int instantOfTime) {
		double[][] positions = new double[persons.length][3];
		for (int i = 0; i < persons.length; i++) {
			Coordinate coordinate = persons[i].getCoordinate(instantOfTime);
			positions[i][0] = coordinate.getX();
			positions[i][1] = coordinate.getY();
			positions[i][2] = coordinate.getState().ordinal();
		}
		return positions;
	}

	/**
	 * Metodo que separa los arreglos segun el estado de la persona
	 * 
	 * @param instantOfTime instante de tiempo
	 * @return objeto con las listas separadas
	 */
	public Object[] separateArrays(int instantOfTime) {
		double[][] positions = getPositions(instantOfTime);
		ArrayList<Double> petax = new ArrayList<Double>(), pentax = new ArrayList<Double>(),
				pstax = new ArrayList<Double>(), psntax = new ArrayList<Double>(), precax = new ArrayList<Double>();
		ArrayList<Double> petay = new ArrayList<Double>(), pentay = new ArrayList<Double>(),
				pstay = new ArrayList<Double>(), psntay = new ArrayList<Double>(), precay = new ArrayList<Double>();

		for (int i = 0; i < positions.length; i++) {
			switch ((int) positions[i][2]) {
			case 0:
				petax.add(positions[i][0]);
				petay.add(positions[i][1]);
				break;
			case 1:
				pentax.add(positions[i][0]);
				pentay.add(positions[i][1]);
				break;
			case 2:
				pstax.add(positions[i][0]);
				pstay.add(positions[i][1]);
				break;
			case 3:
				psntax.add(positions[i][0]);
				psntay.add(positions[i][1]);
				break;
			case 4:
				precax.add(positions[i][0]);
				precay.add(positions[i][1]);
				break;

			}
		}
		return new Object[] { convertToArray(petax, petay),

				convertToArray(pentax, pentay), convertToArray(pstax, pstay), convertToArray(psntax, psntay),
				convertToArray(precax, precay) };
	}

	private double[][] convertToArray(ArrayList<Double> auxArrayListX, ArrayList<Double> auxArrayListy) {
		double[][] aux = new double[2][auxArrayListX.size()];
		for (int i = 0; i < auxArrayListX.size(); i++) {
			aux[0][i] = auxArrayListX.get(i);
			aux[1][i] = auxArrayListy.get(i);
		}
		return aux;
	}

	public Object[] separateAnalitics(int i) {
		int[] analitics = calculateNumberPeopleInState(i-range);
		this.timesNormalPersons.add(Double.valueOf(analitics[0]));
		this.timesSickPersons.add(Double.valueOf(analitics[1]));
		this.timesRecoveryPersons.add(Double.valueOf(analitics[2]));
		double[] indexes = getArrayIndex(i);
		return new Object[] { new double[][] { indexes, convertToArray(timesNormalPersons) },
				new double[][] { indexes, convertToArray(timesSickPersons) },
				new double[][] { indexes, convertToArray(timesRecoveryPersons) } };
	}
	
	private double[] convertToArray(ArrayList<Double> times) {
		double[] aux = new double[times.size()];
		for (int i = 0; i < aux.length; i++) {
			aux[i] = times.get(i);
		}
		return aux;
	}

	public double[] getPercentages(int time) {
		int[] aux = calculateNumberPeopleInState(time);
		double a = aux[0] + aux[1] + aux[2];
		double percent1 = (aux[0] * 100) / a;
		double percent2 = (aux[1] * 100) / a;
		double percent3 = (aux[2] * 100) / a;
		return new double[] { percent1, percent2, percent3 };
	}
	
	public void setRange(int range) {
		this.range = range;
	}
	
	public int getRange() {
		return range;
	}

	private double[] getArrayIndex(int index) {
		double[] aux = new double[index + 1];
		for (int i = 0; i <= index; i++) {
			aux[i] = i;
		}
		return aux;
	}
}
