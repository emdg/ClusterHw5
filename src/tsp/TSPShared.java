package tsp;

import util.Tuple;
import api.Shared;

public class TSPShared extends Shared<Double, Double> {

	
	public TSPShared(Tuple<Double, Double> tuple){
		this.bounds = tuple;
	}
	
	public Tuple<Double, Double> getBounds(){
		return this.bounds;
	};
	
	
	public static TSPShared getStartShared(){
		return new TSPShared(new Tuple<Double, Double>(Double.MAX_VALUE, Double.MAX_VALUE));
	}
	
	
}
