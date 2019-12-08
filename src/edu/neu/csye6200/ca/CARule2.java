package edu.neu.csye6200.ca;

import java.util.HashSet;

/*
 * Rule : state set to frozen when one or two neighbor are frozen
 */
public class CARule2 implements CARule {

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
        else if ( sum == 2 ||sum == 3 || sum == 6){
            current.setState(0);
        }
        return current;
    }
}
