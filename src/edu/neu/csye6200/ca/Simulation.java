package edu.neu.csye6200.ca;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Simulation extends Thread{

    BlockingQueue<CACell[][]> queue;
    public Simulation(BlockingQueue<CACell[][]> queue){
        this.queue = queue;
     }

     private void growth() {

         CACrystal caCrystal = new CACrystal();
         CACrystalSet caCrystalSet = new CACrystalSet();
         caCrystalSet.storeCACrystal(caCrystal);
         CACell [][]  nextGeneration = caCrystal.getCrystal();

         System.out.println("First gen"); // one cell at center
         caCrystal.setConsiderationCells(CACell.findNeighbors(caCrystal.getSeed(),caCrystal.getCrystal()));

         for(int i =0; i < 8 ; i++){
             System.out.println((i+2)+"th gen");
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

     public void run(){
        growth();
     }

}
