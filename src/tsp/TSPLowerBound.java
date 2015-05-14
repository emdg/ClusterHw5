package tsp;

import java.util.ArrayList;

public class TSPLowerBound {
	    private double[][] coordinates;
	    private ArrayList<Integer> path;
	    private double[][] Odistance;
		
		public TSPLowerBound(double[][] coordinates, ArrayList<Integer> path, double[][] distance){
	        this.coordinates = coordinates;
	        this.path = (ArrayList<Integer>) path.clone();
	        this.Odistance = distance;
	    }
		
	    public static double totalDistance(double[][] coordinates, ArrayList<Integer> path)  {
	        ArrayList<Integer> temp = (ArrayList<Integer>) path.clone();
	        int start = temp.get(0);
	        int prev = start;
	        temp.remove(0);
	        double totalDistance = 0;
	        for (int city : temp) {
	            totalDistance += TSPHelpers.distanceBetweenCities(coordinates, prev, city);
	            prev = city;
	        }
	        return totalDistance;
	    }

		public Double getLowerBound() {
			
	        double cost = 0;
	        ArrayList<Integer> clone = (ArrayList<Integer>) path.clone();
	        Integer pre = clone.remove(0);


	        double min = 0;
	        for (double[] coordinate : coordinates) {
	            double c = TSPHelpers.distance(coordinates[pre], coordinate);
	            if (c < min) {
	                min = c;
	            }
	        }
	        cost += min;

	        for (Integer cur : clone) {
	            cost += TSPHelpers.distance(coordinates[pre], coordinates[cur]);
	            pre = cur;
	        }

	        min = 0;
	        for (double[] coordinate : coordinates) {
	            double c = TSPHelpers.distance(coordinates[pre], coordinate);
	            if (c < min) {
	                min = c;
	            }
	        }
	        cost += min;


	        double nCost = 0;
	        for (Integer i = 0; i < coordinates.length; i++) {
	            if (path.contains(i))  continue;

	            double m1 = Double.MAX_VALUE;
	            double m2 = Double.MAX_VALUE;
	            for (int j = 0; j < coordinates.length; j++) {
	                if (i == j) continue;
	                double c = TSPHelpers.distance(coordinates[i], coordinates[j]);
	                if (c < m1) {
	                    m2 = m1;
	                    m1 = c;
	                }
	                else if (c < m2) {
	                    m2 = c;
	                }
	            }
	            nCost += m1 + m2;
	        }

	        return cost + nCost/2;
	    }
}
