package edu.neu.csye6200.ca;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Simulation extends Thread{

    private BlockingQueue<CACell[][]> queue;
    private String rule;
    private int generation;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /*
     * Constructor for Simulation class
     * Initialising the Logger and queue
     */
    public Simulation(BlockingQueue<CACell[][]> queue){
        try{
            FileHandler fileHandler = new FileHandler("Simulation.log", true);
            LogManager.getLogManager().reset();
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        }catch (IOException e){
            System.out.println("Error while creating FileHandler : "+ e.getMessage());
        }
        this.queue = queue;
    }

     private void growth() {
         LOGGER.info("Rule : "+ rule);
         LOGGER.info("Generation : "+ generation);
         CACrystal caCrystal = new CACrystal();
         CACrystalSet caCrystalSet = new CACrystalSet();
         caCrystalSet.storeCACrystal(caCrystal);
         CACell [][]  nextGeneration = caCrystal.getCrystal();

         LOGGER.info("First gen"); // one cell at center
         caCrystal.setConsiderationCells(CACell.findNeighbors(caCrystal.getSeed(),caCrystal.getCrystal()));

         for(int i =0; i < generation ; i++){
             LOGGER.info((i+2)+"th gen");
             HashSet<CACell> considerationList  = new HashSet<>();
             for(CACell cell : caCrystal.getConsiderationCells()){
                 considerationList.addAll(CACell.findNeighbors(cell, caCrystal.getCrystal()));
             }
             nextGeneration = caCrystal.nextGeneration(considerationList,new CARule1(),caCrystalSet.getPreviousCACrystal());
             try {
                 Thread.sleep(1000);
                 queue.put(nextGeneration);
             }catch (InterruptedException e){
                 System.out.println(e.getMessage());
             }
             considerationList.clear();
         }
     }

     // setter for local variables
    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }


    public void run(){
        LOGGER.info("Simulation thread started");
        while(!Thread.interrupted())
            growth();
        LOGGER.info("Simulation thread stopped");
     }
}
