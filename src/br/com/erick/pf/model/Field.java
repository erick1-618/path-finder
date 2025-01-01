package br.com.erick.pf.model;

import java.util.Set;

import br.com.erick.pf.vision.Button;

public class Field implements Comparable<Field>{

	public static enum ObjType {
		SOURCE, TARGET, OBSTACLE
	}
	
	private ObjType obj = null;
	
	private Field previousFastest;
	
	private Set<Field> neighbors;
	
	private int lowestEstimative = -1;
	
	private boolean isExplored = false;
	
	private Button button;
	
	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}


	public void setNeighbors(Set<Field> neighbors) {
		this.neighbors = neighbors;
	}
	
	public Set<Field> getNeighbors() {
		return neighbors;
	}

	public boolean isExplored() {
		return isExplored;
	}

	public void setExplored(boolean isExplored) {
		this.isExplored = isExplored;
	}

	public Field getPreviousFastest() {
		return previousFastest;
	}

	public void setPreviousFastest(Field previousFastest) {
		this.previousFastest = previousFastest;
	}

	public int getLowestEstimative() {
		return lowestEstimative;
	}

	public void setLowestEstimative(int lowestEstimative) {
		this.lowestEstimative = lowestEstimative;
	}

	public void setObj(ObjType obj) {
		this.obj = obj;
		button.refreshColors();
	}
	
	public ObjType getObj() {
		return this.obj;
	}
	
	public boolean isObstructed() {
		return this.obj == ObjType.OBSTACLE;
	}
	
	@Override
	public int compareTo(Field o) {
		return Integer.compare(lowestEstimative, o.lowestEstimative);
	}
}
