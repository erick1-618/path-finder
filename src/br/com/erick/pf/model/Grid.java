package br.com.erick.pf.model;

import java.util.HashSet;
import java.util.Set;

public class Grid {

	private Field[][] matrix;

	public Grid(int size) {
		matrix = new Field[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = new Field();
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				addPossibleNeighbors(i, j);
			}
		}
	}

	private void addPossibleNeighbors(int i, int j) {
		Field actual = matrix[i][j];
		Field possible;

		Set<Field> neightbors = new HashSet<Field>();

		try {
			possible = matrix[i - 1][j];
			if (!possible.isObstructed())
				neightbors.add(possible);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			possible = matrix[i][j - 1];
			if (!possible.isObstructed())
				neightbors.add(possible);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			possible = matrix[i][j + 1];
			if (!possible.isObstructed())
				neightbors.add(possible);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			possible = matrix[i + 1][j];
			if (!possible.isObstructed())
				neightbors.add(possible);
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		actual.setNeighbors(neightbors);
	}

	public Field[][] getMatrix() {
		return matrix;
	}

	public void recalculeNeighbors() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				addPossibleNeighbors(i, j);
			}
		}
	}
}
