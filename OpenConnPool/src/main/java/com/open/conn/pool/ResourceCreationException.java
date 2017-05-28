package com.open.conn.pool;


import java.sql.SQLException;

/*
 * Custom exception for dealing with Reaource Creation
 */
public class ResourceCreationException extends Exception {
    
	public ResourceCreationException(Exception _excep,String message) {
		
		super(message);
		
		if(_excep instanceof InterruptedException)
		{
			System.out.println("Interrupted Exception");
			
		}else if(_excep instanceof SQLException){
			System.out.println("SQl Exception");

		}
		
		
	}
}
