package com.ontotext.kstoilov.interview.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BinaryRegressionTree extends BinaryTree {

	public BinaryRegressionTree(List<ArrayList<Double>> X, List<Double> Y) {
		super(X, Y);
	}
	
	public double getError(Set<Integer> R, List<Double> Y) {
		
		double yMean = this.getMean(R, Y);
		
		double rss = 0;
		for (int row : R) {
			rss += Math.pow((Y.get(row) - yMean), 2); 
		}
		
		return rss;
	}

	public double getLeafValue(Set<Integer> R, List<Double> Y) {
		return this.getMean(R, Y);
	}

}
