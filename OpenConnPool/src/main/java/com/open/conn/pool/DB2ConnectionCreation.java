package com.open.conn.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

public class DB2ConnectionCreation extends ResourcePool {

	private static DB2ConnectionCreation db2ConnectionCreation;
	private static DBParamaters dbParameters;
	private DB2ConnectionCreation(){}

	public static DB2ConnectionCreation getResourcePool(DBParamaters dbParameter){
		if(db2ConnectionCreation == null){
			db2ConnectionCreation = new DB2ConnectionCreation();
			db2ConnectionCreation.sem = new Semaphore(Integer.parseInt(dbParameter.numberOfConnectionsPerExecutor));
			db2ConnectionCreation.dynamicAllocateCount=Integer.parseInt(dbParameter.dynamicAllocateCount);
			db2ConnectionCreation.numberOfConnectionsPerExecutor=Integer.parseInt(dbParameter.numberOfConnectionsPerExecutor);
			dbParameters = dbParameter;
		}
		return db2ConnectionCreation;
	}



	@Override
	public Connection createResource() throws SQLException {
		System.out.println("Creating a new Connection" + Thread.currentThread().getName());
		Connection conn = DriverManager.getConnection(dbParameters.jdbcURL, dbParameters.userName, dbParameters.password);
		return conn;
	}
	
	

	

}
