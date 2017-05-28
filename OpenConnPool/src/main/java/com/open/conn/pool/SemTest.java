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
		for(int i=0;i<1;i++){
		
			try {
				Thread.sleep(3000);
				Connection con = conn.getConnection();
				clist.add(con);

			} catch (ResourceCreationException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		
		
		try {
			Thread.sleep(10000);
			System.out.println("Releasing Them Now");
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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


class SemT2 implements Runnable{
	DB2ConnectionCreation conn;
	public SemT2(DB2ConnectionCreation conn) {
		this.conn = conn;
	}
	@Override
	public void run() {
		List<Connection> clist = new ArrayList<>();
		for(int i=0;i<6;i++){
		
			try {
				Thread.sleep(1000);
				Connection con = conn.getConnection();
				clist.add(con);

			} catch (ResourceCreationException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
			Thread t1 = new Thread(new SemT(conn),"T1");
			t1.start();
			
			Thread t2 = new Thread(new SemT2(conn),"T2");
			t2.start();
			
			
			
	}	
	
	
	
	
	private static void validate(ResourcePool conn){
		if(null == conn)
		{
			System.out.println("This type of connection is yet not supported!!");
			System.exit(1);
		}
	}

}
