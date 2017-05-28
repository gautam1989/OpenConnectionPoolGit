package com.open.conn.pool;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


class SemT implements Runnable{
	DB2ConnectionCreation conn;
	public SemT(DB2ConnectionCreation conn) {
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

			} catch (ResourceCreationException e) {
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
			} catch (ResourceCreationException e) {
				e.printStackTrace();
			}
			}
	}
	
}





public class SemTest {

	public static void main(String[] args) throws InterruptedException, ResourceCreationException {

			ConnectionPoolFactory cpf = new ConnectionPoolFactory();
			DBParamUtility DBp =new DBParamUtility();
			
			DB2ConnectionCreation conn = (DB2ConnectionCreation) cpf.getConnectionType("DB2",DBp.getDBParametersFromConfigFile().get(0));
			validate(conn);
			Thread t1 = new Thread(new SemT(conn));
			t1.start();
			
			
			
	}	
	
	
	
	
	private static void validate(ResourcePool conn){
		if(null == conn)
		{
			System.out.println("This type of connection is yet not supported!!");
			System.exit(1);
		}
	}

}
