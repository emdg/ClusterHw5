package util;

import java.io.Serializable;

public class Tuple<T, E> implements Serializable {
	
	
	public T _1;
	public E _2;
	
	
	public Tuple(T t, E e){
		this._1 = t;
		this._2 = e;
	}
	
	public String toString(){
		return "(" + _1 + ", " + _2 + ")";
	}
}
