package test;

import java.io.FileReader;
import java.util.*;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
	List<CorrelatedFeatures> cfList;
	public float threshold;

	// CTOR:
	public SimpleAnomalyDetector(){
		this.cfList = new LinkedList<CorrelatedFeatures>();
		this.threshold = (float)0.9;
	}

	// Getter:
	public float getThreshold() {return threshold;}
	public List<CorrelatedFeatures> getCfList() {return cfList;}

	//Setter:
	public void setCflist(List<CorrelatedFeatures> cfList) {this.cfList = cfList;}
	public void setThreshold(float thr) {this.threshold = thr;}

	@Override
	public void learnNormal(TimeSeries ts) {
		Point[] points;
		for (int i = 0; i < ts.columnLen(); i++){
			float m = 0,maxDev = 0;
			int c = -1;
			for(int j = i+1;j<ts.columnLen();j++){
				float p = Math.abs(StatLib.pearson(ts.toPrimitiveArray(ts.columnNames[i]), ts.toPrimitiveArray(ts.columnNames[j])));
				if(p > m && p > threshold){
					m = p;
					c = j;
				}
			}
			if(c != -1){
				points = ts.pointsArray(ts.columnNames[i],ts.columnNames[c]);
				Line line = StatLib.linear_reg(points);
				for(Point p :points){
					if(StatLib.dev(p,line) > maxDev) maxDev = StatLib.dev(p,line);
				}
				maxDev = maxDev*(1.1f);
				cfList.add(new CorrelatedFeatures(ts.columnNames[i],ts.columnNames[c],m,line,maxDev));
			}
		}
	}

	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		List<AnomalyReport> List = new LinkedList<AnomalyReport>();
		Point[] points;

		for(CorrelatedFeatures c : cfList){
			points = ts.pointsArray(c.feature1,c.feature2);

			for (int i = 0; i < points.length; i++) {
				if(StatLib.dev(points[i],c.lin_reg) > c.threshold) List.add(new AnomalyReport(c.feature1+"-"+c.feature2, i+1));
			}
		}
		return List;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){return this.cfList;}
}
