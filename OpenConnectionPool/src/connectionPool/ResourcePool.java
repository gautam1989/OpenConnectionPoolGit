package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/*
 * Resource Pool (Connection Pool) which is responsible to create and maintain the pool of Connections and 
 * also the access to the pool.
 * 
 */
public class ResourcePool implements ResourceConnPool<Connection> {

	private Semaphore sem = new Semaphore(2);
	private final Queue<Connection> resources = new ConcurrentLinkedQueue<Connection>();
	private static ResourcePool resourcePool;

	private ResourcePool(){}

	public static ResourcePool getResourcePool(){
		if(resourcePool == null){
			resourcePool = new ResourcePool();
		}
		return resourcePool;
	}




	public Connection getResource(long maxWaitMillis)
			throws  ResourceCreationException {

		// First, acquire the permission to take or create a resource
		try{
			sem.acquire();

			// Then, actually take one if available...
			Connection res = resources.poll();
			if (res != null)
			{
				/*
				 * testConnectionsTobeOK
				 * if OK then return the connection else
				 * dispose the connection from the pool and create one new
				 */
				if(!res.isClosed() && res.isValid(5000)){
					System.out.println("Acquiring from pool");
					return res;
				}


			}
			
			//if connection not in pool then create one
			return createResource();


		}catch(Exception _excep){
			sem.release();
			throw new ResourceCreationException(_excep,_excep.getMessage());
		}
	}

	public void returnResource(Connection res) {

		resources.add(res);
		sem.release();
		System.out.println("Available Connection:"+sem.availablePermits());
	}

	public Connection createResource() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poolingtestdb?useSSL=false", "root", "root");
		System.out.println("Creating Connection:"+sem.availablePermits());
		return conn;
	}




	@Override
	public Connection getConnection() throws  ResourceCreationException {
		return getResource(1000);
	}
}