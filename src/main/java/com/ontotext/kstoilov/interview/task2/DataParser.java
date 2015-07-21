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

	public List<Double> Y = null;
	
	public List<ArrayList<Double>> X = null;
	
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
		
		CSVParser parser = CSVParser.parse(file, Charset.forName("utf-8"), CSVFormat.DEFAULT);
    	
		Y = new ArrayList<Double>(parser.getRecords().size());
		
		Map<Integer, Set<Double>> categoryValues  = new HashMap<Integer, Set<Double>>();
		
    	for (CSVRecord record : parser) {    		
    		if (X == null) {
    			X = this.createColumns(parser.getRecords().size(), record.size());
    		}
    		
    		int column = 0;
    		for (String value : record) {
    			double numeric;
    			
    			try {
    				numeric = Double.parseDouble(value);
    			} catch (NumberFormatException e) {
    				if (! categoryValues.containsKey(column)) {
    					categoryValues.put(column, new HashSet<Double>());
    				}
    				numeric = (double) categoryValues.get(column).size();
    				categoryValues.get(column).add(numeric);
    			}
    		
    			if (column < record.size()) {
    				X.get(column).add(numeric);
    			}
    			else {
    				Y.add(numeric);
    			}
    			column++;
    		}
    	}
	}
	
}
