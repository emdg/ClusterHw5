package result;

import java.util.UUID;

import api.Result;

public class AbstractResult<T> implements Result<T> {
	
	private UUID owner;
	private UUID taskID;
	protected T res;
	private boolean pruned;

	public AbstractResult(UUID taskID, UUID owner, T res, boolean pruned){
		this.owner = owner;
		this.res = res;
		this.pruned = pruned;
		this.taskID = taskID;
	}
	
	
	public T value(){
		return this.res;
	}

	public UUID getOwner(){
		return this.owner;
	}
	
	public boolean isPruned(){
		return this.pruned;
	}
	
	public UUID getTaskID(){
		return this.taskID;
	}
}


