package com.ontotext.kstoilov.interview.task2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Hello world!
 *
 */
public class Task2 
{
    public static void main( String[] args ) throws IOException
    {
    	List<Double> Y = new ArrayList<Double>(1000);
    	
    	List<ArrayList<Double>> X = new ArrayList<ArrayList<Double>>(8);
    	for (int i = 0; i < 8; i++) {
    		X.add(new ArrayList<Double>(1000));
    	}
    	
    	File file = new File("abalone.csv");
    	CSVParser parser = CSVParser.parse(file, Charset.forName("utf-8"), CSVFormat.DEFAULT);
    	
    	for (CSVRecord record : parser) {
    		Iterator<String> it = record.iterator();
    		int column = 0;
    		while (it.hasNext()) {
    			if (column == 0) {
    				String value = it.next();
    				if (value.equals("M")) {
    					X.get(column).add(1.0);
    				}
    				else if (value.equals("F")) {
    					X.get(column).add(2.0);
    				}
    				else {
    					X.get(column).add(0.0);
    				}
    			}
    			else if (column < 8) {
    				X.get(column).add(Double.parseDouble(it.next()));
    			}
    			else if (column == 8) {
    				Y.add(Double.parseDouble(it.next()));
    			}
    			column++;
    		}
    	}
    	
    	for (int row = 0; row < X.get(0).size(); row++) {
    		for (List<Double> column : X) {
    			System.out.print(column.get(row) + ",");
    		}
    		System.out.println(Y.get(row));
    	}
    	
    }
}
