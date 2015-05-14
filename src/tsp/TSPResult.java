package tsp;

import java.util.List;
import java.util.UUID;

import result.AbstractResult;

public class TSPResult extends AbstractResult<List<Integer>> {
	private double score = -1;
	
	public TSPResult(UUID taskID, UUID owner, List<Integer> res, double score, boolean pruned) {
		super(taskID, owner, res, pruned);
		this.score = score;
	}
	
	
	public double getScore(){
		return this.score;
	}
	
	
	public List<Integer> value(){
		return this.res;
	}

}
