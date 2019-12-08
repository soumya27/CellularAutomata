package edu.neu.csye6200.ui;

import edu.neu.csye6200.ca.CACell;
import edu.neu.csye6200.ca.CACrystal;
import edu.neu.csye6200.ca.Simulation;

import javax.swing.*;
import java.awt.*;

//Abstract class for UI
abstract class CellularAutomataUIA {


    //Hexagon dimension variables for CAApp
    final static int BSIZE = CACrystal.SIZE;
    final int HEXSIZE = 20 ;	//hex size in pixels
    final int BORDERS = 15;

    //Color variables for CAApp
    final static Color COLOURBACK =  Color.WHITE;
    final static Color COLOURCELL =  Color.BLACK;
    final static Color COLOURGRID =  Color.WHITE;
    final static Color COLOURONE = new Color(32,178,170,200);

    Simulation simulation =null;
    JFrame frame = null;
    JButton startBtn = null;
    JButton stopBtn = null;
    JTextField sizeText = null;
    JComboBox<String> comboBox = null;
    HexagonalCanvas panel = null;

    private static CACrystal caCrystal = new CACrystal();
    int[][] board = new int[BSIZE][BSIZE];
    static CACell[][] cells = caCrystal.getCrystal();

    CellularAutomataUIA(){
        initGUI();
    }

    // Setting the JFrame
    private void initGUI() {
        frame = new JFrame();
        frame.setTitle("Cellular Automata Crystal Growth ");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(false);
    }

    public abstract JPanel getNorthPanel() ;
}
