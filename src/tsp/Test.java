package tsp;

import java.util.ArrayList;
import java.util.Arrays;

import util.Permutations;

public class Test {

	static double[][] cities = 
		{
		{ 1, 1 },
		{ 8, 1 },
		{ 8, 8 },
		{ 1, 8 },
		{ 2, 2 },
		{ 7, 2 },
		{ 7, 7 },
		{ 2, 7 },
		{ 3, 3 },
		{ 6, 3 },
		{ 6, 6 },
		{ 3, 6 },
		{ 4, 4 },
		{ 5, 4 },
		{ 5, 5 },
		{ 4, 5 }
	};
	public static void main(String[] args){
		double[][] distanceMatrix = new double[cities.length][cities.length];

		for (int i = 0; i < cities.length; i++){
			for (int j = 0; j < cities.length; j++){
				distanceMatrix[i][j] = TSPHelpers.distanceBetweenCities(cities, i, j);
			}
		}
		
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
		
		TSPLowerBound tlb = new TSPLowerBound(cities, list, distanceMatrix);
		
		
		System.out.println(tlb.getLowerBound());
		
		
		 list = new ArrayList<Integer>(Arrays.asList(0, 4, 8, 12, 13, 9, 5, 1, 2));
		
		 tlb = new TSPLowerBound(cities, list, distanceMatrix);
			System.out.println(tlb.getLowerBound());

		
		
		

		System.out.println(TSPHelpers.getBounds(cities, distanceMatrix, list));

		
	
	}
	
	
}
