package edu.neu.csye6200.ca;

import java.util.HashSet;

/*
 * This class will holds a 2D array of CACells
 */
public class CACrystal {

    public static final int SIZE = 40;
    private CACell seed;
    private CACell [][] crystal = new CACell[SIZE][SIZE];
    private HashSet<CACell> considerationCells;

    // Default constructor
    public CACrystal(){
        //set seed location at (SIZE/2,SIZE/2)
        considerationCells = new HashSet<>();
        for (int i =0; i<SIZE; i++){
            for(int j =0;j<SIZE;j++) {
                crystal[i][j]= new CACell(i,j,0);
            }
        }
        crystal[SIZE/2][SIZE/2].setState(1);
        seed = crystal[SIZE/2][SIZE/2];
    }

    /*
     * Method will consider each CACell from the considerationCell HashSe and
     * the previous generation CACrystal caCrystal,
     * apply the CARule rule on it to form the new generation
     * which is returned as crystal
     */
    CACell [][] nextGeneration(HashSet<CACell> considerationCells, CARule rule, CACrystal caCrystal){
        HashSet<CACell> newGeneratedCells = new HashSet<>();
        for(CACell cell : considerationCells){
            cell = rule.applyRule(cell, caCrystal.getCrystal());
            if(cell.getState()==1){
                newGeneratedCells.add(cell);
            }
        }
        caCrystal.getConsiderationCells().clear();
        caCrystal.setConsiderationCells(newGeneratedCells);
        for(CACell cell : newGeneratedCells){
            caCrystal.getCrystal()[cell.getX()][cell.getY()]=cell;
        }
        return crystal;
    }

    /*
     * Getter and setter for crystal variable
     */
    public CACell[][] getCrystal() {
        return crystal;
    }

    /*
     * Method will return the center cell ( which will be the seed for the crystal growth )
     */
    CACell getSeed() {
        return seed;
    }

    /*
     * Getter and setter for considerationCells variable
     */
    HashSet<CACell> getConsiderationCells() {
        return considerationCells;
    }

    void setConsiderationCells(HashSet<CACell> considerationCells) {
        this.considerationCells = considerationCells;
    }

}
