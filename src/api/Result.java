package api;

import java.io.Serializable;
import java.util.UUID;

public interface Result<T> extends Serializable {

	
	public UUID getOwner();
	public boolean isPruned();
	public UUID getTaskID();
	
	public T value();
}
