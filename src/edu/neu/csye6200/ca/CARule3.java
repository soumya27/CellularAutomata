package edu.neu.csye6200.ca;

import java.util.HashSet;

/*
 * Rule : state set to frozen when 1 or 0 neighbor are frozen
 */
public class CARule3 implements CARule {

    @Override
    public CACell applyRule(CACell current, CACell[][] previousGen) {
        HashSet<CACell> neighbors = CACell.findNeighbors(current,previousGen);
        int sum =0;
        for (CACell cell : neighbors){
            sum += cell.getState();
        }
        if(sum == 1 || sum == 3){
            current.setState(1);
        }
        return current;
    }
}
