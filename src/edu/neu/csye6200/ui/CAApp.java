package edu.neu.csye6200.ui;

import edu.neu.csye6200.ca.CACell;
import edu.neu.csye6200.ca.Simulation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CAApp extends CellularAutomataUIA {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private CAApp() {
		try{
			FileHandler fileHandler = new FileHandler("CAApp.log", true);
			LogManager.getLogManager().reset();
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			LOGGER.addHandler(fileHandler);
		}catch (IOException e){
			System.out.println("Error while creating FileHandler : "+ e.getMessage());
		}
		simulation = new Simulation();
		init();
		createAndShowGUI();
	}

	private void init(){
		CAAppHelper.setXYasVertex(false);
		CAAppHelper.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
		CAAppHelper.setBorders(BORDERS);
		for (int i=0;i<BSIZE;i++) {
			for (int j=0;j<BSIZE;j++) {
				int EMPTY = 0;
				board[i][j]= EMPTY;
			}
		}
	}

	private void createAndShowGUI() {
		//screen size (vertical dimension).
		int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3;
		frame.setSize( (int)(SCRSIZE /1.23), SCRSIZE);
		panel = new HexagonalCanvas();
		frame.getContentPane().add(getNorthPanel(),BorderLayout.NORTH);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public JPanel getNorthPanel() {

		Timer timer = new Timer(50,e -> setDisplay());
		JPanel northPanel = new JPanel();
		northPanel.setBackground(COLOURBACK);
		northPanel.setLayout(new FlowLayout());

		startBtn = new JButton("Start");
		startBtn.addActionListener(e ->{
			LOGGER.info("Max duration : "+sizeText.getText());
			simulation.setRule(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
			LOGGER.info("Rules: "+ comboBox.getSelectedItem());
			simulation.setGeneration(Integer.parseInt(sizeText.getText()));
			simulation.startSimulation();
			setButtonState();
			timer.start();
			LOGGER.info("start button clicked");
		});

		northPanel.add(startBtn);

		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(e->{
			LOGGER.info("stop button clicked");
			setDisplay(simulation.getStartState());
			simulation.stopSimulation();
			setButtonState();
			timer.stop();
		});

		northPanel.add(stopBtn);
		setButtonState();
		northPanel.add(new JLabel("Max Duration: "));
		sizeText = new JTextField(5);
		sizeText.setText("1");
		northPanel.add(sizeText);

		comboBox = new JComboBox<>();
		comboBox.addItem("Rule 1");
		comboBox.addItem("Rule 2");
		comboBox.addItem("Rule 3");
		northPanel.add(new JLabel("Options: "));
		northPanel.add(comboBox);
		return northPanel;
	}

	/*
	 * Method to enable or disable the buttons depending
	 * on the state of simulation thread
	 */
    private void setButtonState() {
		boolean running = simulation.isRunning();
		startBtn.setEnabled(!running);
		stopBtn.setEnabled(running);
	}

	/*
	 * Method to set the display with each generation of crystal
	 * and repaint the panel
	 */
    private void setDisplay() {
		setCells(simulation.getNextGeneration());
		panel.repaint();
    }

	/*
	 * Method to set the display with empty crystal
	 * and repaint the panel
	 */
	private void setDisplay(CACell[][] empty) {
		setCells(empty);
		panel.repaint();
	}

	// setter method
	private void setCells(CACell[][] cells) {
		CellularAutomataUIA.cells = cells;
	}

	public static void main(String[] args) {
		new CAApp();
	}

}