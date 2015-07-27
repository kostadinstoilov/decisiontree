package com.ontotext.kstoilov.interview.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

abstract public class BinaryTree {

	public SplitNode head = null;
	
	private static final int MAXSPLIT = 10;
	
	private int MAXREGION = 10;

	private List<ArrayList<Double>> X;
	
	private List<Double> Y;

	public Set<HashSet<Integer>> regions;
	
	abstract public double getError(Set<Integer> R, List<Double> Y);
	
	abstract public double getLeafValue(Set<Integer> R, List<Double> Y);
	
	public BinaryTree (List<ArrayList<Double>> X, List<Double> Y) {
		
		this.X = X;
		this.Y = Y;
		regions = new HashSet<HashSet<Integer>>();
		MAXREGION = Y.size()/MAXSPLIT;
	}
	
	public void build() {
		
		Set<Integer> R = new HashSet<Integer>(Y.size());
		for (int i = 0; i < Y.size(); i++) {
			R.add(i);
		}
		this.head = this.buildRecurse(R);
	}
	
	private SplitNode buildRecurse(Set<Integer> R) {
		
		SplitNode node = this.bestSplit(R);
		
		if (node.regionLeft.size() > MAXREGION) {
			node.left = this.buildRecurse(node.regionLeft);
		} 
		else {
			regions.add(node.regionLeft);
		}
		
		if (node.regionRight.size() > MAXREGION) {
			node.right = this.buildRecurse(node.regionRight);
		}
		else {
			regions.add(node.regionRight);
		}
		
		return node;
	}
	
	public SplitNode bestSplit(Set<Integer> R) {
		
		SplitNode node = new SplitNode();
		
		Double bestRss = null;
		int column = 0;
		
		for (ArrayList<Double> x : X) {

			Set<Double> splitValues = this.getSplitValues(x, R);
			
			for (double s : splitValues) {
				HashSet<Integer> R1 = new HashSet<Integer>();
				HashSet<Integer> R2 = new HashSet<Integer>();
				for (int row : R) {
					if (x.get(row) < s) {
						R1.add(row);
					}
					else {
						R2.add(row);
					}
				}
				if (R1.size() > 0 && R2.size() > 0) {
					Double rss = this.getError(R1, Y) + this.getError(R2, Y);
					if (bestRss == null || rss < bestRss) {
						bestRss = rss;
						node.feature = column;
						node.s = s;
						node.regionLeft = R1;
						node.regionRight = R2;
					}					
				}

			}
			
			column++;
		}
		
		return node;
	}
	
	public Set<Double> getSplitValues(List<Double> x, Set<Integer> R) {
		
		Set<Double> distinct = new HashSet<Double>();
		
		for (int row = 0; row < x.size(); row++) {
			if (R.contains(row)) {
				distinct.add(x.get(row));
			}
 		}
		
		if (distinct.size() < MAXSPLIT) {
			return distinct;
		}
		
		Double min = Collections.min(distinct);
		Double max = Collections.max(distinct);
		
		Double step = (max - min) / (MAXSPLIT + 2);
		
		Set<Double> split = new HashSet<Double>();		
		for (Double s = min+step; s < max; s+=step) {
			split.add(s);
		}
		return split;
	}
	
	public double getMean(Set<Integer>R, List<Double> Y) {
		
		double sum = 0;
		for (int row : R) {
			sum += Y.get(row);
		}
		return  (sum / R.size());
	}
	
	public double getOutput(List<Double> x) {
		return this.traverse(this.head, x);
	}
	
	private double traverse(SplitNode node, List<Double> x) {
		
		if (x.get(node.feature) < node.s) {
			if (node.left == null) {
				return this.getLeafValue(node.regionLeft, Y);
			}
			return this.traverse(node.left, x);	
		}
		else {
			if (node.right == null) {
				return this.getLeafValue(node.regionRight, Y);
			}
			return this.traverse(node.right, x);
		}
	}
	
	private void traversePrunable(SplitNode node, Set<SplitNode> prunable) {
		
		if (node.left == null && node.right == null) {
			prunable.add(node);
			return;
		}
		
		if (node.left != null) {
			traversePrunable(node.left, prunable);
		}
		
		if (node.left != null) {
			traversePrunable(node.right, prunable);
		}
		
		return;
	}	
}
