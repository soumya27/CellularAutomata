package edu.neu.csye6200.ca;

import java.util.ArrayList;
import java.util.HashSet;

/*
 * This class will holds multiple CACrystals
 * and can call the 'CA Rule' class repeatedly
 * to generate a new crystal state
 */
public class CACrystalSet {

    ArrayList<CACrystal> generationHistory = new ArrayList<CACrystal>();


    HashSet<CACell> previousChanged = new HashSet<>();
    /*
     * Method will add new state of the crystal growth to the array list
     * @param CACrystal 2D array
     */
    public void storeCACrystal( CACrystal generation){
        generationHistory.add(generation);
    }

    public void storeCACrystal( CACell [][]  caCellsArray){
        CACrystal caCrystal = new CACrystal();
        caCrystal.setCrystal(caCellsArray);
        generationHistory.add(caCrystal);
    }
    /*
    * Method will return the previous state of the crystal growth
    * @return CACrystal 2D array
     */
    public CACrystal getPreviousCACrystal(){
        return generationHistory.get(generationHistory.size()-1);
    }

    public HashSet<CACell> getPreviousChanged() {
        return previousChanged;
    }

    public void setPreviousChanged(HashSet<CACell> previousChanged) {
        this.previousChanged = previousChanged;
    }



}
