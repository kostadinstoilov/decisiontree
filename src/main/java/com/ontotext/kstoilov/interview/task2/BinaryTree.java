package com.ontotext.kstoilov.interview.task2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BinaryTree {

	public SplitNode head = null;
	
	public List<HashSet<Integer>> split(List<ArrayList<Double>> X, int size) {

		Set<Integer> R = new HashSet<Integer>(size);
		for (int i = 0; i < size; i++) {
			R.add(i);
		}
		
		if (head == null) {
			return null;
		}

		return splitRecurse(head, R, X);
	}
	
	private List<HashSet<Integer>> splitRecurse(SplitNode node, Set<Integer> R, List<ArrayList<Double>> data) {
		
		HashSet<Integer> R1 = new HashSet<Integer>();
		HashSet<Integer> R2 = new HashSet<Integer>();
		
		for (int row : R) {
			if (data.get(node.column).get(row) < node.s) {
				R1.add(row);
			}
			else {
				R2.add(row);
			}
		}
		
		List<HashSet<Integer>> result = new ArrayList<HashSet<Integer>>();
		
		if (node.left != null) {
			result.addAll(this.splitRecurse(node.left, R1, data));
		}
		else {
			result.add(R1);
		}
		
		if (node.right != null) {
			result.addAll(this.splitRecurse(node.right, R2, data));
		} 
		else {
			result.add(R2);
		}
		
		return result;		
	}
}
