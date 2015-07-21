package com.ontotext.kstoilov.interview.task2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Hello world!
 *
 */
public class Task2 
{
    public static void main( String[] args )
    {
    	File file = new File("abalone.csv");
    	
    	DataParser d;
    	try {
			 d = new DataParser(file);
		} catch (IOException e) {
			System.err.println("Could not read input data");
			return;
		}
    	
		for (int row = 0; row < d.Y.size(); row++) {
    		for (int col = 0; col < d.X.size(); col++) {
    			System.out.print (d.X.get(col).get(row) + " ");
    		}
    		System.out.println(d.Y.get(row));
    	}
    }
}
