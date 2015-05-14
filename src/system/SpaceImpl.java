package system;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import tsp.TSPShared;
import api.RemoteComputer;
import api.Result;
import api.Shared;
import api.Space;
import api.Task;
import api.TaskGroup;


public class SpaceImpl extends UnicastRemoteObject implements Space {

	private PriorityBlockingQueue<Task<?>> tasks;
	private ConcurrentHashMap<UUID, TaskGroup> taskGroupMap;
	
	
	private Shared shared;
	

	protected SpaceImpl() throws RemoteException {
		super();
		tasks = new PriorityBlockingQueue<Task<?>>(100000);
		
		taskGroupMap = new ConcurrentHashMap<UUID, TaskGroup>();
		shared = TSPShared.getStartShared();		
	}
	
	
	
	@Override
	public void register(RemoteComputer computer) {
		System.out.println("got computer");
		new Thread(new ComputerProxy(computer, tasks)).run();
	}



	@Override
	public synchronized Shared getShared() throws RemoteException {
		return this.shared;
	}


	@Override
	public synchronized void addTasks(List<Task<?>> tasks) throws RemoteException {
		if (tasks.size() > 0){ 
			UUID owner = tasks.get(0).getOwner();
			
			
			taskGroupMap.get(owner).registerTasks(tasks);
			
			this.tasks.addAll(tasks);
		}
	}
	
	@Override
	public synchronized void putTask(Task<?> task) throws RemoteException {
		this.tasks.add(task);
	}



	@Override
	public synchronized void setShared(Shared shared) {
		this.shared = shared;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		System.setSecurityManager(new SecurityManager());
		LocateRegistry.createRegistry(Space.PORT).rebind(Space.SERVICE_NAME, new SpaceImpl());
	}



	@Override
	public void registerTaskGroup(TaskGroup taskGroup) throws RemoteException {
		taskGroupMap.put(taskGroup.getID(), taskGroup);
		this.setShared(taskGroup.getShared());
		this.putTask((Task)taskGroup.getStartTask());
	}



	@Override
	public void registerResult(Result<?> result) throws RemoteException {
		UUID owner = result.getOwner();
		taskGroupMap.get(owner).registerResult(result);
	}



	@Override
	public boolean taskGroupFinished(UUID uuid) throws RemoteException {
		return taskGroupMap.get(uuid).isReady(this.tasks);
	}



	@Override
	public Result<?> getResult(UUID uuid) throws RemoteException {
		return taskGroupMap.get(uuid).getBestResult();
	}

	
}
