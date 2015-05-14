package system;


import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import api.Task;
import api.RemoteComputer;

public class ComputerProxy implements Runnable{
	private RemoteComputer computer;
	private PriorityBlockingQueue<Task<?>> taskList;
	
	public ComputerProxy(RemoteComputer computer, PriorityBlockingQueue<Task<?>> taskList){
		this.computer =  computer;
		this.taskList = taskList;
	}

	public void run(){
		while(true){
			if (!taskList.isEmpty()){
				Task<?> task = null;	
					try {
						task = taskList.poll();
						if (task != null){
							computer.handleTask(task);
						}
						System.out.println(taskList.size());

					}
					catch(RemoteException e){
						taskList.add(task);
						System.out.println(e);
						return;
					}
			}
		}
	}
}