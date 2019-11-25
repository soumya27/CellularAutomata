package edu.neu.csye6200.ca;

import java.util.HashSet;

/*
 * Rule : state set to frozen when one or two neighbor are frozen
 */
public class CARule2 implements CARule {
    @Override
    public CACell applyRule(CACell current, CACell[][] previousGen) {
        HashSet<CACell> neighbors = current.findNeighbors(current,previousGen);
        int sum =0;
        for (CACell cell : neighbors){
            sum += cell.getState();
        }
        if(sum == 1 || sum == 2){
            current.setState(1);
        }
        return current;
    }
}
