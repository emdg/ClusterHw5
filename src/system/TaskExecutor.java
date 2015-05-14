package system;
import java.rmi.RemoteException;
import java.util.concurrent.PriorityBlockingQueue;

import api.Shared;
import api.Space;
import api.Task;

public class TaskExecutor extends Thread implements Runnable {

	Space space;
	private PriorityBlockingQueue<Task<?>> tasks;
	private Task<?> currentTask;
	public TaskExecutor(Space space, PriorityBlockingQueue<Task<?>> tasks){
		this.space = space;
		this.tasks = tasks;
	}
	
	public synchronized <T> void execute(Task<T> task) throws RemoteException {
		// TODO Auto-generated method stub
				
		System.out.println(task);
		task.execute(space);
	}
	
	
	public Task<?> getCurrentTask(){
		return this.currentTask;
	}
	
	
	
	@Override
	public void run() {
		while(true){
			if (!tasks.isEmpty()){
				try {
					currentTask = tasks.poll();
					if (currentTask != null)
						this.execute(currentTask);
				}
				
				catch(Exception e){
					currentTask = null;
					e.printStackTrace();
				}
			}
		}
	}

}
