package edu.neu.csye6200.ca;

/*
* This class can assign a new cell based on a neighboring cell
*/
public interface CARule {

    CACell applyRule(CACell current , CACell[][] previousGen);

}
