package com.open.conn.pool;

public class DBParamaters {

	
	String userName;
	String password;
	String jdbcURL;
	String numberOfConnectionsPerPartition;
	
	@Override
	public String toString() {
		return "DBParamaters [userName=" + userName + ", password=" + password + ", jdbcURL=" + jdbcURL
				+ ", numberOfConnectionsPerPartition=" + numberOfConnectionsPerPartition + "]";
	}
	
	
}