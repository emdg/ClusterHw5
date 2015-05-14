package api;

import java.io.Serializable;

import util.Tuple;

public abstract class Shared<T, E> implements Serializable {
	
	protected Tuple<T, E> bounds;
	
	public boolean isOlderThan(Shared<T, E> other){
		return false;
	};
	public Tuple<T, E> getBounds(){
		return null;
	};
}
