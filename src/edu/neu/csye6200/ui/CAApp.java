package edu.neu.csye6200.ui;

import edu.neu.csye6200.ca.CACell;
import edu.neu.csye6200.ca.CACrystal;
import edu.neu.csye6200.ca.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**********************************

 ***********************************/

public class CAApp
{
	//constants and global variables
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private BlockingQueue<CACell[][]> queue ;

	//Hexagon dimension variables for CAApp
	private final int BSIZE = CACrystal.SIZE;
	private final int HEXSIZE = 20 ;	//hex size in pixels
	private final int BORDERS = 15;

	//Color variables for CAApp
	private final static Color COLOURBACK =  Color.WHITE;
	final static Color COLOURCELL =  Color.BLACK;
	final static Color COLOURGRID =  Color.WHITE;
	final static Color COLOURONE = new Color(32,178,170,200);

	private Simulation simulation;

	private  JButton startBtn = null;
	private JTextField sizeText = null;
	private JComboBox<String> comboBox = null;
	private DrawingPanel panel;

	private CACrystal caCrystal = new CACrystal();
	private int[][] board = new int[BSIZE][BSIZE];

	private CACell[][] cells = caCrystal.getCrystal();


	private CAApp(BlockingQueue<CACell[][]> queue) {
		try{
			FileHandler fileHandler = new FileHandler("CAApp.log", true);
			LogManager.getLogManager().reset();
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			LOGGER.addHandler(fileHandler);
		}catch (IOException e){
			System.out.println("Error while creating FileHandler : "+ e.getMessage());
		}
		this.queue = queue;
		initGame();
		createAndShowGUI();
	}

	public static void main(String[] args)
	{
		Thread UImain = new Thread();
		BlockingQueue<CACell[][]> queue = new ArrayBlockingQueue<>(20) ;

		SwingUtilities.invokeLater(new Runnable() {
				public void run() {
				new CAApp(queue);
				}
		});
		UImain.setName("uiprocess");
		LOGGER.info(UImain.getName());
	}

	private void initGame(){

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

	private void createAndShowGUI()
	{
		simulation = new Simulation(queue);
		panel = new DrawingPanel();
		OptionsPanel options = new OptionsPanel();
		JFrame frame = new JFrame(" Cellular Automata Crystal Growth ");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setLayout(new BorderLayout());
		JScrollPane jScrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Container content = frame.getContentPane();
		content.add(options.getNorthPanel(),BorderLayout.NORTH);
		content.add(jScrollPane);

		//screen size (vertical dimension).
		int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3;
		frame.setSize( (int)(SCRSIZE /1.23), SCRSIZE);
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}


	public CACell[][] getCells() {
		return cells;
	}

	private void setCells(CACell[][] cells) {
		this.cells = cells;
	}


	class OptionsPanel extends JPanel {

		Timer timer ;

		OptionsPanel(){
			setBackground(COLOURBACK);
		}

		JPanel getNorthPanel() {
			timer = new Timer(10, e -> {
				try {
					while (!queue.isEmpty()){
						setCells(queue.take());
						Thread.sleep(100);
						panel.repaint();
					}
				} catch (InterruptedException ex) {
					LOGGER.severe(ex.getMessage());
				}
			});
			JPanel northPanel = new JPanel();
			northPanel.setLayout(new FlowLayout());

			startBtn = new JButton("Start");
			startBtn.addActionListener(new StartListener());

			northPanel.add(startBtn);

			JButton stopBtn = new JButton("Stop");
			stopBtn.addActionListener(new StopListener());
			northPanel.add(stopBtn);

			northPanel.add(new JLabel("Max Duration: "));
			sizeText = new JTextField(5);
			sizeText.setText("0");
			sizeText.addActionListener(e -> {
				if(simulation.isAlive()) {
					try {
						simulation.join();
					} catch (InterruptedException ex) {
						LOGGER.severe(ex.getMessage());
					}
				}
			});
			northPanel.add(sizeText);

			comboBox = new JComboBox<>();
			comboBox.addItem("Rule 1");
			comboBox.addItem("Rule 2");
			comboBox.addItem("Rule 3");
			comboBox.addActionListener(e -> {
				if(simulation.isAlive()) {
					try {
						System.out.println("alive");
						simulation.join();
					} catch (InterruptedException ex) {
						LOGGER.severe(ex.getMessage());
					}
				}
			});
			northPanel.add(new JLabel("Options: "));
			northPanel.add(comboBox);
			return northPanel;
		}

		class StartListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Max duration : "+sizeText.getText());
				simulation.setRule(comboBox.getSelectedItem().toString());
				LOGGER.info("Rules: "+ comboBox.getSelectedItem());
				simulation.setGeneration(Integer.parseInt(sizeText.getText()));
				startBtn.setEnabled(false);
				sizeText.setEnabled(false);
				comboBox.setEnabled(false);
				LOGGER.info("start clicked");
				if(!simulation.isAlive())
					simulation.start();
				timer.start();
			}
		}

		class StopListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("stop button clicked");
				simulation.interrupt();
				timer.stop();
				startBtn.setEnabled(true);
				sizeText.setEnabled(true);
				comboBox.setEnabled(true);
			}
		}
	}

	class DrawingPanel extends JPanel
	{
		public DrawingPanel()
		{
			setBackground(COLOURBACK);
		}

		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			super.paintComponent(g2);
			for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {
					CAAppHelper.drawHex(i,j,g2);
				}
			}
			for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {
					CAAppHelper.fillHex(i,j,cells[i][j].getState(),g2);
				}
			}
		}
	}

}