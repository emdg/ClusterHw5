package tsp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import task.AbstractTask;
import util.Tuple;
import api.Shared;
import api.Space;
import api.Task;

public class TSPTask extends AbstractTask<Integer> implements Comparable<Task<Integer>> {
	
	private double[][] cities;
	private List<Integer> start;
	private List<Integer> perm;
	private double[][] distanceMatrix;
	private Tuple<Double, Double> bounds;
	
	private boolean lowerbound;
	
	public TSPTask(double[][] cities, List<Integer> start, boolean lowerbound){
		super();
		this.cities = cities;
		this.start = start;
		this.distanceMatrix = new double[cities.length][cities.length];
		for (int i = 0; i < cities.length; i++){
			for (int j = 0; j < cities.length; j++){
				this.distanceMatrix[i][j] = TSPHelpers.distanceBetweenCities(cities, i, j);				
			}
		}
		
		this.perm = IntStream.range(0, cities.length).boxed().collect(Collectors.toList());
		this.perm.removeAll(this.start);
		this.bounds =  TSPHelpers.getBounds(cities, distanceMatrix, this.start);	
		this.lowerbound = lowerbound;
	}
	
	
	public TSPTask(UUID owner, double[][] cities, double[][] distanceMatrix, List<Integer> start, boolean lowerbound){
		super();
		this.cities = cities;
		this.start = start;
		this.distanceMatrix = distanceMatrix;
		this.owner = owner;
		this.perm = IntStream.range(0, cities.length).boxed().collect(Collectors.toList());
		this.perm.removeAll(this.start);
		this.bounds =  TSPHelpers.getBounds(cities, distanceMatrix, this.start);
		this.lowerbound = lowerbound;
	}
	
	public TSPTask(UUID owner, double[][] cities, List<Integer> start, boolean lowerbound){
		this(cities, start, lowerbound);
		this.cities = cities;
		this.start = start;
		this.owner = owner;
		this.perm = IntStream.range(0, cities.length).boxed().collect(Collectors.toList());
		this.perm.removeAll(this.start);
	}


	
	
	
	@Override
	public synchronized void execute(Space space) {
		ArrayList<Task<?>> tasks = new ArrayList<Task<?>>();
		Shared shared = null;
		try {
			shared = space.getShared();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Tuple<Double, Double> sharedLowAndUpper = shared.getBounds();;
		
		if (lowerbound){
			if (bounds._1 > sharedLowAndUpper._1 ){

				try {
					space.registerResult(new TSPResult(this.uuid, this.owner, this.start, 0, true));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		
		
		if (this.start.size() == cities.length){
			try {
				space.setShared(new TSPShared(bounds));
				System.out.println("Result found: ");
				System.out.println(this.start);
				space.registerResult(new TSPResult(this.uuid, this.owner, this.start, 0, false));
				return;
			}
			catch (RemoteException e){
				e.printStackTrace();
			}
		}

		

		
		
		
		for (Integer i : this.perm){
			ArrayList<Integer> ints = new ArrayList<Integer>(this.start);
			ints.add(i);		
			TSPTask task = new TSPTask(this.owner,this.cities, distanceMatrix, ints, lowerbound);
			tasks.add(task);
		}
		
		try {
			space.addTasks(tasks);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	public void setBounds(Tuple<Double, Double> bounds){
		this.bounds = bounds;
	}

	@Override
	public int compareTo(Task<Integer> o) {
		
		
		int res = (this.getHigherBound() > o.getHigherBound() ? 1 : -1);
		
		if (this.getHigherBound() == o.getHigherBound()){
			res = (this.getLowerBound() > o.getLowerBound() ? 1 : -1);
		}
		
		return res;
	}


	@Override
	public double getHigherBound() {
		// TODO Auto-generated method stub
		return bounds._2;
	}


	@Override
	public double getLowerBound() {
		// TODO Auto-generated method stub
		return bounds._1;
	}	
}
