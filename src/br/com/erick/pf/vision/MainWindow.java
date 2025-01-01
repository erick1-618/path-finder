package br.com.erick.pf.vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.erick.pf.exceptions.PathNotFoundException;
import br.com.erick.pf.model.Field;
import br.com.erick.pf.simulation.Controller;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private Controller controller;

	//Buttons
	private JButton resetButton;
	private JButton startButton;
	
	//Path execution thread
	private Thread simThread;

	public MainWindow() {

		controller = new Controller(50);

		setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(e -> {
			if (simThread == null || !simThread.isAlive()) {
				controller.resetGrid();
				revalidate();
				repaint();
			}
		});

		startButton = new JButton("Start");
		startButton.addActionListener(e -> {
			if (simThread == null || !simThread.isAlive()) {
				refreshColors();
				simThread = getExecutionThread();
				simThread.start();
			}
		});

		buttonPanel.add(startButton);
		buttonPanel.add(resetButton);

		add(buttonPanel, BorderLayout.NORTH);

		JPanel gridPanel = new JPanel(new GridLayout(50, 50));
		for (int i = 0; i < controller.getGrid().getMatrix().length; i++) {
			for (int j = 0; j < controller.getGrid().getMatrix().length; j++) {
				gridPanel.add(new Button(controller.getGrid().getMatrix()[i][j], controller));
			}
		}
		add(gridPanel, BorderLayout.CENTER);

		setResizable(false);
		setSize(new Dimension(650, 650));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Path Finder - Dijkstra's Algorithm Simulator");
		setVisible(true);
	}

	private void refreshColors() {
		Field[][] matrix = controller.getGrid().getMatrix();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j].getButton().refreshColors();
			}
		}
	}

	public static void main(String[] args) {
		new MainWindow();
	}
	
	private Thread getExecutionThread() {
		return new Thread(() -> {
			try {
				controller.logicalReset();
				List<Field> list = controller.getShortestPath();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getObj() == null) {
						list.get(i).getButton().setBackground(Color.green);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			} catch (PathNotFoundException ex) {
				JOptionPane.showMessageDialog(this, "A path was not found!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
