package edu.neu.csye6200.ui;

import edu.neu.csye6200.ca.CACell;
import edu.neu.csye6200.ca.CACrystal;
import edu.neu.csye6200.ca.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**********************************

 ***********************************/

public class CAApp
{
	BlockingQueue<CACell[][]> queue ;
	//constants and global variables
	public final static Color COLOURBACK =  Color.WHITE;
	private final static int EMPTY = 0;
	public final static int BSIZE = CACrystal.SIZE;
	private final static int HEXSIZE = 20 ;	//hex size in pixels
	private final static int BORDERS = 15;
	private final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS*3; //screen size (vertical dimension).
	final static Color COLOURCELL =  Color.BLACK;
	final static Color COLOURGRID =  Color.WHITE;
	final static Color COLOURONE = new Color(32,178,170,200);

	private  JPanel northPanel = null;
	private  JButton startBtn = null;
	private  JButton stopBtn = null;
	private JTextField sizeText = null;
	private JComboBox<String> comboBox = null;
	DrawingPanel panel;


//	Simulation simulation = new Simulation();
	CACrystal caCrystal = new CACrystal();
	int[][] board = new int[BSIZE][BSIZE];

	CACell[][] cells = caCrystal.getCrystal();


	private CAApp(BlockingQueue<CACell[][]> queue) {
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
		System.out.println(UImain.getName());
	}

	private void initGame(){

		CAAppHelper.setXYasVertex(false);

		CAAppHelper.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
		CAAppHelper.setBorders(BORDERS);

		for (int i=0;i<BSIZE;i++) {
			for (int j=0;j<BSIZE;j++) {
				board[i][j]=EMPTY;
			}
		}
	}

	private void createAndShowGUI()
	{
		panel = new DrawingPanel();
		OptionsPanel options = new OptionsPanel();
		JFrame frame = new JFrame(" Cellular Automata Crystal Growth ");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setLayout(new BorderLayout());
		JScrollPane jScrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Container content = frame.getContentPane();
		content.add(options.getNorthPanel(),BorderLayout.NORTH);
		content.add(jScrollPane);

		frame.setSize( (int)(SCRSIZE/1.23), SCRSIZE);
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}

	class OptionsPanel extends JPanel {

		Timer timer ;
		public OptionsPanel(){
			setBackground(COLOURBACK);
		}

		public JPanel getNorthPanel() {
			timer = new Timer(10, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						System.out.println("print from start button");
						setCells(queue.take());
						panel.repaint();
					} catch (InterruptedException ex) {
						System.out.println(ex.getMessage());
					}
				}
		});
			northPanel = new JPanel();
			northPanel.setLayout(new FlowLayout());

			startBtn = new JButton("Start");
			startBtn.addActionListener(new StartListener());

			northPanel.add(startBtn);

			stopBtn = new JButton("Stop");
			stopBtn.addActionListener(new StopListener());
			northPanel.add(stopBtn);

			northPanel.add(new JLabel("Max Duration: "));
			sizeText = new JTextField(5);
			northPanel.add(sizeText);

			comboBox = new JComboBox<>();
			comboBox.addItem("Rule1");
			comboBox.addItem("Rule2");
			comboBox.addItem("Rule3");
			northPanel.add(new JLabel("Options: "));
			northPanel.add(comboBox);
			return northPanel;
		}

		class StartListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.out.println(sizeText.getText());
				System.out.println(comboBox.getSelectedItem());
				startBtn.setEnabled(false);
				sizeText.setEnabled(false);
				comboBox.setEnabled(false);
				System.out.println("start clicked");
				Simulation simulation = new Simulation(queue);
				simulation.start();
				timer.start();
			}
		}

		class StopListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.out.println("stopping");
				timer.stop();
				startBtn.setEnabled(true);
				sizeText.setEnabled(true);
				comboBox.setEnabled(true);
			}
		}
	}

	public CACell[][] getCells() {
		return cells;
	}

	public void setCells(CACell[][] cells) {
		this.cells = cells;
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