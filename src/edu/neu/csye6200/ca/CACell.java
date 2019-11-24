package edu.neu.csye6200.ca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
 * This class defines the state of a cell
 */
public class CACell {


    private int x; // x co-ordinated on the Crystal grid
    private int y; // y co-ordinated on the Crystal grid
    private int state; // State can be frozen (denoted by 1) or not (Denoted by 0)

    /*
     * Default Constructor
     */
    public CACell()
    {
        x = 0;
        y = 0;
        state = 0;
    }

    public CACell(CACell cell){
        this.x = cell.getX();
        this.y = cell.getY();
        this.state = cell.getState();
    }

    /*
     * Parameterised Constructor
     */
    public CACell(int x, int y, int state)
    {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    /*
     * Method to find the neighboring cells for a given cell
     */
    public static HashSet<CACell> findNeighbors(CACell current, CACell[][] crystal){
//        System.out.println("Cell [ "+current.getX()+","+current.getY()+" ]");
        HashSet<CACell> neighbors = new HashSet<>();
        if(current.getX()%2 ==0) { //even
            neighbors.add(new CACell(crystal[current.getX()][current.getY() + 1]));
            neighbors.add(new CACell(crystal[current.getX()][current.getY() - 1]));
            neighbors.add(new CACell(crystal[current.getX() - 1][current.getY() - 1]));
            neighbors.add(new CACell(crystal[current.getX() - 1][current.getY()]));
            neighbors.add(new CACell(crystal[current.getX() + 1][current.getY() - 1]));
            neighbors.add(new CACell(crystal[current.getX() + 1][current.getY()]));
        } else { //odd
            neighbors.add(new CACell(crystal[current.getX()][current.getY() + 1]));
            neighbors.add(new CACell(crystal[current.getX()][current.getY() - 1]));
            neighbors.add(new CACell(crystal[current.getX() - 1][current.getY() +1]));
            neighbors.add(new CACell(crystal[current.getX() - 1][current.getY()]));
            neighbors.add(new CACell(crystal[current.getX() + 1][current.getY() +1]));
            neighbors.add(new CACell(crystal[current.getX() + 1][current.getY()]));
        }
//        System.out.println(Arrays.toString(neighbors.toArray()));
        return neighbors;
    }

    /*
     * Getters and Setters for the class variables
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "( " + x +
                "," + y +
                ", " + state +
                ')';
    }
}
