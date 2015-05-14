package tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

import api.Result;
import api.Shared;
import api.Task;
import api.TaskGroup;

public class TSPTaskGroup implements TaskGroup {

	UUID uuid;
	TSPTask startTask;
	Result<?> bestResult;
	HashMap<UUID, Task<?>> unfinishedTasks;
	TSPShared shared;
	boolean lowerBound;
	
	public TSPTaskGroup(double[][] CITIES, List<Integer> base, boolean lowerBound){
		this.uuid = UUID.randomUUID();
		TSPTask task = new TSPTask(uuid, CITIES, base, lowerBound);
		this.startTask = task;
		this.unfinishedTasks = new HashMap<UUID, Task<?>>();
		bestResult = null;
		shared = TSPShared.getStartShared();
	}
	
	
	
	
	
	
	public void registerResult(Result<?> result){
		if (!result.isPruned()){
			bestResult = result;
		}
		
		UUID idToRemove = result.getTaskID();
		unfinishedTasks.remove(idToRemove);
	}
	
	public void registerTasks(List<Task<?>> tasks){
		for (Task task: tasks){
			unfinishedTasks.remove(task.getUUID());
		}
	}
	
	
	
	
	public boolean isReady(PriorityBlockingQueue<Task<?>> queue) {
		return (bestResult != null && unfinishedTasks.isEmpty() && queue.isEmpty());
	}
	
	

	@Override
	public UUID getID() {
		return this.uuid;
	}

	@Override
	public Task<?> getStartTask() {
		return this.startTask;
	}






	@Override
	public Result<?> getBestResult() {
		return this.bestResult;
	}






	@Override
	public Shared getShared() {
		return this.shared;
	}

	
	
	
	
}
