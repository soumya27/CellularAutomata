package edu.neu.csye6200.ca;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Simulation implements Runnable{

    private String rule;
    private int generation;
    private Thread mySimThread = null;
    private Runnable mySimulationRunnable ;
    private boolean done = false;
    private boolean isRunning = false;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private CACell [][]  nextGeneration;
    private CACell[][] startState;

    /*
     * Constructor for Simulation class
     * Initialising the Logger and runnable
     */
    public Simulation(){
        try{
            FileHandler fileHandler = new FileHandler("Simulation.log", true);
            LogManager.getLogManager().reset();
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        }catch (IOException e){
            System.out.println("Error while creating FileHandler : "+ e.getMessage());
        }
        this.mySimulationRunnable = this;
    }

    // start the simulation thread
    public void startSimulation(){
        if(mySimThread == null)
            mySimThread = new Thread(mySimulationRunnable);
        if(!mySimThread.isAlive())
            mySimThread.start();
        setRunning(true);
    }

    // quit the simulation thread
    public void stopSimulation(){
        done = true;
        setRunning(false);
    }

    private void delayThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    // setter for local variables
    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void setRunning(boolean running) {
        isRunning = running;
    }

    public CACell[][] getStartState() {
        return startState;
    }

    // Method to create an empty CACrystal with seed set at center
    private void setStartState() {
      CACrystal crystal = new CACrystal();
      startState = crystal.getCrystal();
    }

    public CACell[][] getNextGeneration() {
        return nextGeneration;
    }

    private void growth() {

         LOGGER.info("Rule : "+ rule);
         LOGGER.info("Generation : "+ generation);

         CACrystal caCrystal = new CACrystal();
         CACrystalSet caCrystalSet = CACrystalSet.getInstance();
         CARule caRule;

         caCrystal.setConsiderationCells(CACell.findNeighbors(caCrystal.getSeed(),caCrystal.getCrystal()));
         caCrystalSet.storeCACrystal(caCrystal);
         setStartState();

         switch (rule){
             case "Rule 2":{
                 // for rule2 ,we have two seed at the starting stage
                 caCrystal.getCrystal()[CACrystal.SIZE/2][CACrystal.SIZE/2 - 1].setState(1);
                 caRule = new CARule2();
                 break;
             }
             case "Rule 3":{
                 caRule = new CARule3();
                 break;
             }
             default:
             case "Rule 1":{
                 caRule = new CARule1();
                 break;
             }
         }

         for(int i = 0; i < generation ;){
             if(done)
                 break;
             LOGGER.info((i+1)+"th gen");
             HashSet<CACell> considerationList  = new HashSet<>();
             for(CACell cell : caCrystal.getConsiderationCells()){
                 considerationList.addAll(CACell.findNeighbors(cell, caCrystal.getCrystal()));
             }
             nextGeneration = caCrystal.nextGeneration(considerationList, caRule,caCrystalSet.getPreviousCACrystal());
             delayThread(1000L);
             considerationList.clear();
             i++;
         }
     }

    public void run(){
        LOGGER.info("Simulation thread started");
        while( !done ){
            growth();
        }
        mySimThread = null;
        done =false;
        LOGGER.info("Simulation thread stopped");
     }
}
