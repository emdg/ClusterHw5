package tsp;

import java.util.ArrayList;
import java.util.List;

import util.KruskalAlgorithm;

import util.Tuple;

public class TSPHelpers {
	public static double distanceBetweenCities(double[][] cities, Integer city1, Integer city2){
		return Math.sqrt(Math.pow(cities[city2][1] - cities[city1][1], 2.0) + Math.pow(cities[city2][0] - cities[city1][0], 2.0));
	}
	
	
	public static double calculateRoundTrip(double[][] cities, List<Integer> seq){
		
		double score = calculatePath(cities, seq);
		Integer city2 = seq.get(0);
		Integer city1 = seq.get(seq.size() - 1);
		score += distanceBetweenCities(cities, city1, city2);
		return score;
	}
	
	
	public static double calculatePath(double[][] cities, List<Integer> seq){
		double score = 0;
		for (int i = 0; i < seq.size() - 1; i++){
			Integer city1 = seq.get(i);
			Integer city2 = seq.get(i + 1);
			score += distanceBetweenCities(cities, city1, city2);
		}
		return score;
	}
	
	
	
	public static int minIndexNotInSeq(double[] row, List<Integer> seq){
		int index = 0;
		double min = Integer.MAX_VALUE;
		for(int i = 0; i < row.length; i++){
			
			if (seq.contains(i)){
				continue;
			}
			
			
			if (row[i] != 0){
				if (row[i] < min){
					min = row[i];
					index = i;
				}
			}
		}
		return index;
	}
	
	

	
	public static Integer minIndex(double[][] distanceMatrix, int row, List<Integer> seq){
		
		int minIndex = -1;
		double minWeight = Double.MAX_VALUE;
		for (int i = 0; i < distanceMatrix[row].length; i++){
			if (seq.contains(i))
				continue;
			
			if (i == row)
				continue;
			else {
				if (minWeight > distanceMatrix[row][i]){
					minIndex = i;
					minWeight = distanceMatrix[row][i];
				}
			}
		}
		
		return minIndex;

	}
	
	
	//using nearest neighbour
	public static double calculateUpperBound(double[][] cities, double[][] distanceMatrix, List<Integer> seq){
		
		if (seq.size() == cities.length){
			return calculateRoundTrip(cities, seq);
		}
		
		ArrayList<Integer> upperBoundSeq = new ArrayList<Integer>(seq);
		Integer lastCity = upperBoundSeq.get(seq.size() - 1);
		while (upperBoundSeq.size() < cities.length){
			Integer newCity = minIndex(distanceMatrix, lastCity, upperBoundSeq);
			upperBoundSeq.add(newCity);
			lastCity = newCity;
		}
		
		return TSPHelpers.calculateRoundTrip(cities, upperBoundSeq);
	}
	
	


	
	public static double calculateLowerBound(double[][] cities, double[][] distanceMatrix, List<Integer> seq){
		return new TSPLowerBound(cities, new ArrayList<Integer>(seq), distanceMatrix).getLowerBound();
	}
	
	
	
	public static Tuple<Double, Double> getBounds(double[][] cities, double[][] distanceMatrix, List<Integer> seq){

		return new Tuple<Double, Double>(calculateLowerBound(cities, distanceMatrix, seq), calculateUpperBound(cities, distanceMatrix, seq));
	}
	
    public static double distance(double[] city1, double[] city2) {
        return Math.sqrt(Math.pow(city2[0] - city1[0], 2) +
                Math.pow(city2[1] - city1[1], 2));
    }
	
}





