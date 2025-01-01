package br.com.erick.pf.simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import br.com.erick.pf.exceptions.PathNotFoundException;
import br.com.erick.pf.model.Field;
import br.com.erick.pf.model.Field.ObjType;
import br.com.erick.pf.model.Grid;
import br.com.erick.pf.vision.Button;

public class Controller {
	
	private Grid grid;
	private Field source;
	private Field target;
	private boolean targetFound = false;
	
	public void setSource(Field source) {
		this.source = source;
	}

	public void setTarget(Field target) {
		this.target = target;
	}

	public Controller(int size) {
		grid = new Grid(size);
	}
	
	private void findShortestPath() {
		
		if(source == null || target == null) return;
		
	    PriorityQueue<Field> queue = new PriorityQueue<>();
	    queue.add(source);

	    while (!queue.isEmpty()) {
	        Field current = queue.poll();
	        
	        if (current.isExplored()) continue;
	        current.setExplored(true);
			if (current.getObj() == null)
	        current.getButton().setBackground(Color.lightGray);
	        
	        try {
				Thread.sleep(2);
			} catch (Exception e) {}
	        
			if (current == target) {
	        	targetFound = true;
	        	break;	        	
	        }

	        for (Field neighbor : current.getNeighbors()) {
	            if (!neighbor.isExplored()) {
	                int newEstimative = current.getLowestEstimative() + 1;
	                if (neighbor.getLowestEstimative() == -1 || newEstimative < neighbor.getLowestEstimative()) {
	                    neighbor.setLowestEstimative(newEstimative);
	                    neighbor.setPreviousFastest(current);
	                    queue.add(neighbor);
	                }
	            }
	        }
	    }
	    
	    if(!targetFound) throw new PathNotFoundException();
	}
	
	public Grid getGrid() {
		return grid;
	}

	public List<Field> getShortestPath() {
		findShortestPath();
		List<Field> list = new ArrayList<Field>();
		Field current = target;
		while(current != null) {
			list.add(current);
			current = current.getPreviousFastest();
		}
		return list.reversed();
	}
	
	public void logicalReset() {
		for (int i = 0; i < grid.getMatrix().length; i++) {
	        for (int j = 0; j < grid.getMatrix().length; j++) {
	        	targetFound = false;
	            Field field = grid.getMatrix()[i][j];
	            field.setPreviousFastest(null);
	            field.setExplored(false);
	            if(field.getObj() == ObjType.SOURCE) field.setLowestEstimative(0);
	            else field.setLowestEstimative(-1);
	        }
	    }
	}
	
	public void resetGrid() {
	    for (int i = 0; i < grid.getMatrix().length; i++) {
	        for (int j = 0; j < grid.getMatrix().length; j++) {
	        	targetFound = false;
	        	target = null;
	        	source = null;
	            Field field = grid.getMatrix()[i][j];
	            field.setObj(null);
	            field.setPreviousFastest(null);
	            field.setLowestEstimative(-1);
	            field.setExplored(false);
	            Button.resetObjs();
	        }
	    }
	    grid.recalculeNeighbors();
	}
}
