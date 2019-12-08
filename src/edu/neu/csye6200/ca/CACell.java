package edu.neu.csye6200.ca;

import java.util.HashSet;
import java.util.Objects;

/*
 * This class defines the state of a cell
 */
public class CACell {


    private int x; // x co-ordinated on the Crystal grid
    private int y; // y co-ordinated on the Crystal grid
    private int state; // State can be frozen (denoted by 1) or not (Denoted by 0)

    private CACell(CACell cell){
        this.x = cell.getX();
        this.y = cell.getY();
        this.state = cell.getState();
    }

    /*
     * Parameterised Constructor
     */
    CACell(int x, int y, int state)
    {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    /*
     * Method to find the neighboring cells for a given cell
     */
    static HashSet<CACell> findNeighbors(CACell current, CACell[][] crystal){
        HashSet<CACell> neighbors = new HashSet<>();
        int x = current.getX();
        int y = current.getY();
        if( x < CACrystal.SIZE -1 && y < CACrystal.SIZE-1 && x > 1 && y > 1){ // exit conditions
            neighbors.add(new CACell(crystal[x][y + 1]));
            neighbors.add(new CACell(crystal[x][y - 1]));
            neighbors.add(new CACell(crystal[x - 1][y]));
            neighbors.add(new CACell(crystal[x + 1][y]));
            if(x%2 ==0) { //even
                neighbors.add(new CACell(crystal[x - 1][y - 1]));
                neighbors.add(new CACell(crystal[x + 1][y - 1]));
            } else { //odd
                neighbors.add(new CACell(crystal[x - 1][y + 1]));
                neighbors.add(new CACell(crystal[x + 1][y +1 ]));
            }
        }
        return neighbors;
    }

    /*
     * Getters and Setters for the class variables
     */
    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public int getState() {
        return state;
    }

    void setState(int state) {
        this.state = state;
    }

    /*
     * Overriding the equals() and hashCode() to help comparing two CACells
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CACell)) return false;
        CACell caCell = (CACell) o;
        return getX() == caCell.getX() &&
                getY() == caCell.getY() &&
                getState() == caCell.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getState());
    }

    /*
     * Overriding the toString() to print CACells in the form (x,y,state)
     */
    @Override
    public String toString() {
        return "( " + x +
                "," + y +
                ", " + state +
                ')';
    }
}
