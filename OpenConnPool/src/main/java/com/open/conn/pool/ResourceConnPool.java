package com.open.conn.pool;



/*
 * interface which can be used for concrete
 * implementation for different Databases.
 */
public interface ResourceConnPool<T extends Object> {

	/*
	 * Returns a connection from the database and also 
	 * deals with pooling of it
	 */
	public T getConnection() throws InterruptedException, ResourceCreationException;
	
	/*
	 * Returns the resource back to the pool
	 */
	public void returnResource(T conn);
	
	/*
	 * Return the valid pool Size
	 */
	public int getValidPoolSize();
	
	
}
