package api;

import java.io.Serializable;
import java.util.UUID;

public interface Task<T> extends Serializable, Comparable<Task<T>>{
	
	
	public double getHigherBound();
	public double getLowerBound();
	public UUID getOwner();
	public UUID getUUID();
	abstract public void execute(Space space);	
}
