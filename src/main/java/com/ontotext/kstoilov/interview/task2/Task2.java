package com.ontotext.kstoilov.interview.task2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Task2 
{
    public static void main( String[] args )
    {
    	File file = new File(args[0]);
    	
    	DataParser data;
    	try {
			 data = new DataParser(file);
		} catch (IOException e) {
			System.err.println("Could not read input data");
			return;
		}
    	
    	int split = Math.round(data.getY().size()/2);
    	
    	List<Double> train = data.getY().subList(0, split);
    	
    	BinaryTree tree;
    	
    	if (args.length >= 2 && args[1].equals("classification")) {
        	tree = new BinaryClassificationTree(data.getX(), train);   		
    	}
    	else {
        	tree = new BinaryRegressionTree(data.getX(), train);
    	}

    	tree.build(); 
    	
    	double error = 0;
    	
    	double avg = 0;
    	
    	for (int row = split; row < data.getY().size(); row++) {
    		
    		List<Double> sample = new ArrayList<Double>();
 
    		for (List<Double> x : data.getX()) {
    			sample.add(x.get(row));
    		}
    		double output = tree.getOutput(sample);
    		error += Math.pow(output - data.getY().get(row), 2);
    		avg += Math.abs(output - data.getY().get(row));
     	}
    	
    	System.out.println("Total MSE over test set " + error);
    	System.out.println("Average error per test set sample: " + avg/split);
    	
    }
}
