package com.ontotext.kstoilov.interview.task2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BinaryClassificationTree extends BinaryTree {

	Set<Double> K;
	
	public BinaryClassificationTree(List<ArrayList<Double>> X, List<Double> Y) {
		super(X, Y);
		this.K = new HashSet<Double>();
		
		for (double y : Y) {
			this.K.add(y);
		}
	}

	@Override
	public double getError(Set<Integer> R, List<Double> Y) {

		double giniIndex = 0;

		for (double k : K) {
			int currentTotal = 0;
			for (int row : R) {
				if (Y.get(row) == k) {
					currentTotal++;
				}
			}
			double p = currentTotal/R.size();
			giniIndex += (p * (1 - p));
		}
		
		return giniIndex;
	}

	@Override
	public double getLeafValue(Set<Integer> R, List<Double> Y) {
		
		double maxK = 0;
		double maxSamples = 0;
		
		for (double k : K) {
			int totalK = 0;
			for (int row : R) {
				if (Y.get(row) == k) {
					totalK++;
				}
			}
			if (totalK > maxSamples) {
				maxK = k;
				maxSamples = totalK;
			}
		}
		
		return maxK;
	}

}
