package com.ontotext.kstoilov.interview.task2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataParser {

	private List<Double> Y = null;
	
	private List<ArrayList<Double>> X = null;
	
	public DataParser(File file) throws IOException {
		parseFile(file);
	}
	
	private List<ArrayList<Double>> createColumns(int rowsize, int colsize) {

    	List<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>(colsize);
    	for (int i = 0; i < colsize; i++) {
    		list.add(new ArrayList<Double>(rowsize));
    	}
    	
    	return list;
	}
	
	private void parseFile(File file) throws IOException {
		
		Map<Integer, HashMap<String, Integer>> categoryValues = new HashMap<Integer, HashMap<String, Integer>>();
		
		CSVParser parser = CSVParser.parse(file, Charset.forName("utf-8"), CSVFormat.DEFAULT);
		
		int rows = 1000;
		
		Y = new ArrayList<Double>(rows);
		
    	for (CSVRecord record : parser) {
    		
    		if (X == null) {
    			X = this.createColumns(rows, record.size()-1);
    		}
    		
    		int column = 0;
    		for (String value : record) {
    			
    			double numeric;
    			
    			try {
    				numeric = Double.parseDouble(value);
    			} catch (NumberFormatException e) {
    				HashMap<String, Integer> columnValues = (categoryValues.containsKey(column))? categoryValues.get(column) : new HashMap<String, Integer>();
    				if (! columnValues.containsKey(value)) {
    					columnValues.put(value, columnValues.keySet().size());
    				}
    				numeric = (double) columnValues.get(value);
    				categoryValues.put(column, columnValues);
    			}
    		
    			if (column < record.size()-1) {
    				X.get(column).add(numeric);
    			}
    			else {
    				Y.add(numeric);
    			}
    			column++;
    		}
    	}
    	
    	for (Integer column : categoryValues.keySet()) {
    		int setSize = categoryValues.get(column).keySet().size();
    		List<Double> columnVector = X.get(column);
    		for (int row = 0; row < columnVector.size(); row++) {
    			columnVector.set(row, (double) Math.round(columnVector.get(row)/setSize*1000)/1000);
    		}
    	}
    }
	
	public List<ArrayList<Double>> getX() {
		return X;
	}
	
	public List<Double> getY() {
		return Y;
	}
}
