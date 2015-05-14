package api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface Space extends Remote {
	public static int PORT = 8001;
	public static String SERVICE_NAME = "Space";

	
	public void addTasks(List<Task<?>> tasks) throws RemoteException;
	
	public void putTask(Task<?> task) throws RemoteException;
	public void register(RemoteComputer computer) throws RemoteException;

	public Shared getShared() throws RemoteException;
	
	
	
	public void registerTaskGroup(TaskGroup taskGroup) throws RemoteException;
	
	
	public void registerResult(Result<?> result) throws RemoteException;
	
	public void setShared(Shared shared) throws RemoteException;
	
	
	
	public boolean taskGroupFinished(UUID uuid) throws RemoteException;
	
	public Result<?> getResult(UUID uuid) throws RemoteException;

}
