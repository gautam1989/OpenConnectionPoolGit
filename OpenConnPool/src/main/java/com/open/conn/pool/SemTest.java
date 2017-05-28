package com.open.conn.pool;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


class SemT implements Runnable{
	ResourceConnPool<Connection> conn;
	public SemT(ResourceConnPool<Connection> conn) {
		this.conn = conn;
	}
	@Override
	public void run() {
		List<Connection> clist = new ArrayList<>();
		for(int i=0;i<2;i++){
			System.out.println("Pool Size: "+conn.getValidPoolSize());
			try {
				Connection con = conn.getConnection();
				clist.add(con);

			} catch (InterruptedException | ResourceCreationException e) {
				e.printStackTrace();
			}
			}
		
		System.out.println("Releasing Them Now");
		
		for(Connection c:clist)
			conn.returnResource(c);
		
		
		for(int i=0;i<3;i++){
			try {
				System.out.println("Pool Size: "+conn.getValidPoolSize());
				Connection con = conn.getConnection();
				clist.add(con);
			} catch (InterruptedException | ResourceCreationException e) {
				e.printStackTrace();
			}
			}
	}
	
}





public class SemTest {

	public static void main(String[] args) throws InterruptedException, ResourceCreationException {

			ResourceConnPool<Connection> conn = ResourcePool.getResourcePool();
			
			Thread t1 = new Thread(new SemT(conn));
			t1.start();
			
			
			
	}	

}
