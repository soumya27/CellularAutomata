package edu.neu.csye6200.ca;

import java.util.HashSet;

/*
 * Rule : state set to frozen when exactly one neighbor is frozen
 */
public class CARule1 implements CARule {

    @Override
    public CACell applyRule(CACell current, CACell[][] previousGen) {
        HashSet<CACell> neighbors = CACell.findNeighbors(current,previousGen);
        int sum =0;
        for (CACell cell : neighbors){
            sum += cell.getState();
        }
        if(sum == 1 ){
            current.setState(1);
        }
        return current;
    }
}
