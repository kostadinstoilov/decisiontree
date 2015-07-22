package com.ontotext.kstoilov.interview.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class BinaryTree {

	public SplitNode head = null;
	
	private static final int MAXSPLIT = 10;
	
	private static final int MAXREGION = 10;

	private List<ArrayList<Double>> X;
	
	private List<Double> Y;

	public Set<HashSet<Integer>> regions;
	
	public BinaryTree (List<ArrayList<Double>> X, List<Double> Y) {
		
		this.X = X;
		this.Y = Y;
		build();
	}
	
	public void build() {

		regions = new HashSet<HashSet<Integer>>();
		
		Set<Integer> R = new HashSet<Integer>(Y.size());
		for (int i = 0; i < Y.size(); i++) {
			R.add(i);
		}
		
		this.head = this.buildRecurse(R);
	}
	
	private SplitNode buildRecurse(Set<Integer> R) {
		
		SplitNode node = this.bestSplit(R);
		
		if (node.R1.size() > MAXREGION) {
			node.left = this.buildRecurse(node.R1);
		} 
		else {
			regions.add(node.R1);
		}
		
		if (node.R2.size() > MAXREGION) {
			node.right = this.buildRecurse(node.R2);
		}
		else {
			regions.add(node.R2);
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
				Double rss = this.getRSS(R1, Y) + this.getRSS(R2, Y);
				if (bestRss == null || rss < bestRss) {
					bestRss = rss;
					node.column = column;
					node.s = s;
					node.R1 = R1;
					node.R2 = R2;
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
	
	public double getRSS(Set<Integer> R, List<Double> Y) {
		
		double yMean = this.getMean(R, Y);
		
		double rss = 0;
		for (int row : R) {
			rss += Math.pow((Y.get(row) - yMean), 2); 
		}
		
		return rss;
	}
	
	public double getOutput(List<Double> x) {
		return this.traverse(this.head, x);
	}
	
	private double traverse(SplitNode node, List<Double> x) {
		
		if (x.get(node.column) < node.s) {
			if (node.right == null) {
				return this.getMean(node.R1, Y);
			}
			return this.traverse(node.right, x);	
		}
		else {
			if (node.left == null) {
				return this.getMean(node.R2, Y);
			}
			return this.traverse(node.left, x);
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
