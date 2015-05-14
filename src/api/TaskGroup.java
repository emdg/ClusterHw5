package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

public interface TaskGroup extends Serializable {
	public UUID getID();
	
	public Task<?> getStartTask();
	public boolean isReady(PriorityBlockingQueue<Task<?>> tasks);
	
	public void registerTasks(List<Task<?>> tasks);
	
	public void registerResult(Result<?> result);
	
	
	public Result<?> getBestResult();
	
	public Shared getShared();
	
}
