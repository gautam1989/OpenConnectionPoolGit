package com.open.conn.pool;

public class ConnectionPoolFactory {

	
	 public ResourcePool getConnectionType(String resourceTYPE, DBParamaters dbParamaters){
		 if("DB2".equalsIgnoreCase(resourceTYPE)){
			 return DB2ConnectionCreation.getResourcePool(dbParamaters);
		 }
		 		return null;
	 }
	 
	 
	 
	
}
