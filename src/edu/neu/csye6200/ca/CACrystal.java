package edu.neu.csye6200.ca;

import java.util.HashSet;

/*
 * This class will holds a 2D array of CACells
 */
public class CACrystal {


    public static final int SIZE = 40;


    private CACell [][] crystal = new CACell[SIZE][SIZE];
    private CACell seed;

    HashSet<CACell> considerationCells;

    /*
     * Default constructor
     */
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
     * Method to print the Crystal 2D array
     */
    public void printCrystalStates(CACell [][] crystal){
        for (int i =0; i<SIZE; i++){
            for(int j =0;j<SIZE;j++){
                System.out.print(crystal[i][j].getState() + " ");
            }
            System.out.println();
        }
    }

    // Getter
    public CACell[][] getCrystal() {
        return crystal;
    }

    // Setter
    public void setCrystal(CACell[][] crystal) {
        this.crystal = crystal;
    }

    public CACell getSeed() {
        return seed;
    }

    public void setSeed(CACell seed) {
        this.seed = seed;
    }

    public HashSet<CACell> getConsiderationCells() {
        return considerationCells;
    }

    public void setConsiderationCells(HashSet<CACell> considerationCells) {
        this.considerationCells = considerationCells;
    }

    public CACell [][] nextGeneration(HashSet<CACell> considerationCells, CARule rule, CACrystal caCrystal){
        HashSet<CACell> newGeneratedCells = new HashSet<>();
        rule = new CARule1();
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

}
