package task;
import java.util.UUID;

import api.Result;
import api.Task;

import java.util.concurrent.LinkedBlockingQueue;


public abstract class AbstractTask<T> implements Task<T>{
	
	
	
	
	protected UUID uuid;
	
	protected UUID owner;
	
	protected LinkedBlockingQueue<Result<T>> results;

		
	public AbstractTask(UUID uuid){
		this.owner = uuid;
		this.uuid = UUID.randomUUID();
		this.results = new LinkedBlockingQueue<Result<T>>();

	}
	

	
	
	
	public AbstractTask(){
		this.uuid = UUID.randomUUID();
		this.owner = UUID.randomUUID();
		this.results = new LinkedBlockingQueue<Result<T>>();
	}
	
	public UUID getUUID(){
		return this.uuid;
	}
	
	public UUID getParentUUID(){
		return null;
	}
	
	public UUID getOwner(){
		return this.owner;
	}

}
