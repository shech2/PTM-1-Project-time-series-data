package test;


import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.stream.Collectors;

public class TimeSeries {
	HashMap<String,Vector<Float>> map = new HashMap<String ,Vector<Float>>();
	String[] columnNames;
	String[] values;
	
	public TimeSeries(String csvFileName) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFileName));

			String line = reader.readLine(); // Read First line..

			columnNames = line.split(",");  // save content of first line -ABCD-

			for (int i = 0; i < columnNames.length; i++) map.put(columnNames[i], new Vector<Float>());

			while ((line = reader.readLine()) != null) {
				values = line.split(",");

				for (int i = 0; i < columnNames.length; i++) { // put the values in order.
					map.get(columnNames[i]).add(Float.parseFloat(values[i]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	// : returns the length of the column.
	public int columnLen() {return columnNames.length;}

	// : returns the number of rows.
	public int rowsAmount() { return (map.get(columnNames[0])).size(); }


	public Vector<Float> colByLetter(String let) {return map.get(let);}


	// transfer the array from the map to primitive float array (float[]).
	public float[] toPrimitiveArray(String colName){
		float[] primitiveArray = new float[map.get(colName).size()];
		int i = 0;
		for(Float f : map.get(colName)){
			primitiveArray[i] = f;
			i++;
		}
		return primitiveArray;
	}

	// returns an element from a specific col.
	public Float floatFromCol(String colName,int index) {return map.get(colName).elementAt(index); }

	// transfer 2 cols to points array.
	public Point[] pointsArray(String colName1,String colName2){
		Point[] points = new Point[rowsAmount()];
		for (int i = 0; i < rowsAmount(); i++) {
			points[i] = new Point(floatFromCol(colName1,i),floatFromCol(colName2,i));
		}
		return points;
	}




}
