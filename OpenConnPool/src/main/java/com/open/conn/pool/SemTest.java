package com.open.conn.pool;


import java.sql.Connection;
import java.sql.SQLException;
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
		for(int i=0;i<7;i++){
		
			try {
				Connection con = conn.getConnection();
				clist.add(con);

			} catch (ResourceCreationException e) {
				e.printStackTrace();
			}
			}
		
		System.out.println("Releasing Them Now");
		
		for(Connection c:clist){
			try {
				conn.returnResource(c);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
//		
//		for(int i=0;i<3;i++){
//			try {
//				System.out.println("Pool Size: "+conn.getValidPoolSize());
//				Connection con = conn.getConnection();
//				clist.add(con);
//			} catch (ResourceCreationException e) {
//				e.printStackTrace();
//			}
//			}
		
		for(Connection c:clist){
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
