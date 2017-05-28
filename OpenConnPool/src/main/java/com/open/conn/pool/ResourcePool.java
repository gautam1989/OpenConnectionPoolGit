package com.open.conn.pool;


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
abstract class ResourcePool implements ResourceConnPool<Connection> {

	protected Semaphore sem ;
	private final Queue<Connection> resources = new ConcurrentLinkedQueue<Connection>();
	private  ResourcePool resourcePool;


	
	
	public ResourcePool() {
		// TODO Auto-generated constructor stub
	}


	public int getValidPoolSize(){
		if( null != resources && resources.size() >=0)
			return resources.size();
		else
			return 0;
	}

	public Connection getResource()
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
				}else{
					//creating a new resource as the one just pooled is not valid now 
					return createResource();
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


	
	public  abstract Connection createResource() throws SQLException;



	@Override
	public Connection getConnection() throws  ResourceCreationException {
		return getResource();
	}
}