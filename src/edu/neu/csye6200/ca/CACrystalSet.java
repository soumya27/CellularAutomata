package edu.neu.csye6200.ca;

import java.util.ArrayList;

/*
 * This class will holds multiple CACrystals
 * and can call the 'CA Rule' class repeatedly
 * to generate a new crystal state
 */
class CACrystalSet {

    //Making CACrystalSet a Singleton class
    private static CACrystalSet instance = null;

    //Making the constructor private
    private CACrystalSet(){

    }

    //This will create a new instance or return the existing instance
    static CACrystalSet getInstance(){
        if(instance==null) instance = new CACrystalSet();
        return instance;
    }

    private ArrayList<CACrystal> generationHistory = new ArrayList<>();

    /*
     * Method will add new state of the crystal growth to the array list
     * @param CACrystal 2D array
     */
    void storeCACrystal( CACrystal generation){
        generationHistory.add(generation);
    }

    /*
    * Method will return the previous state of the crystal growth
    * @return CACrystal 2D array
     */
    CACrystal getPreviousCACrystal(){
        return generationHistory.get(generationHistory.size()-1);
    }

}
