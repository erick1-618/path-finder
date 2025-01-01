package br.com.erick.pf.vision;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import br.com.erick.pf.model.Field;
import br.com.erick.pf.model.Field.ObjType;
import br.com.erick.pf.simulation.Controller;

public class Button extends JButton implements MouseListener {

	private static final long serialVersionUID = 1L;

	private Field f;
	private Controller controller;

	private static boolean sourcePut = false;
	private static boolean targetPut = false;
	private static boolean rightButtonPressed = false;

	public static void resetObjs() {
		sourcePut = false;
		targetPut = false;
	}

	public Button(Field f, Controller c) {
		addMouseListener(this);
		this.controller = c;
		setBackground(Color.white);
		setBorderPainted(false);
		this.f = f;
		f.setButton(this);
	}

	public Field getF() {
		return f;
	}

	public void setF(Field f) {
		this.f = f;
	}


	public void refreshColors() {
		if (f.getObj() == ObjType.OBSTACLE) {
			setBackground(Color.black);
		} else if (f.getObj() == ObjType.SOURCE) {
			setBackground(Color.blue);
		} else if (f.getObj() == ObjType.TARGET) {
			setBackground(Color.red);
		} else {
			setBackground(Color.white);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (f.getObj() == null) {
				if (!sourcePut) {
					sourcePut = true;
					f.setObj(ObjType.SOURCE);
					f.setLowestEstimative(0);
					controller.setSource(f);
				} else if (!targetPut) {
					targetPut = true;
					f.setObj(ObjType.TARGET);
					controller.setTarget(f);
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightButtonPressed = true;
			if (f.getObj() == null) {
				f.setObj(ObjType.OBSTACLE);
			} else if (f.getObj() == ObjType.OBSTACLE) {
				f.setObj(null);
			}
			controller.getGrid().recalculeNeighbors();
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			rightButtonPressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (rightButtonPressed) {
			rightButtonPressed = true;
			if (f.getObj() == null) {
				f.setObj(ObjType.OBSTACLE);
			} else if (f.getObj() == ObjType.OBSTACLE) {
				f.setObj(null);
			}
			controller.getGrid().recalculeNeighbors();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
}
