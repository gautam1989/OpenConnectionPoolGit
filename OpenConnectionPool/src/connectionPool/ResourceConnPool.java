package connectionPool;


/*
 * interface which can be used for concrete
 * implementation for different Databases.
 */
public interface ResourceConnPool<T extends Object> {

	
	public T getConnection() throws InterruptedException, ResourceCreationException;
	public void returnResource(T conn);
	
	
}
