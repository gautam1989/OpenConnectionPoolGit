package com.open.conn.pool;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DBParamUtility {

	
	public  String getJsonDBConfigString(){

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("dbconfig.json").getFile());
		StringBuilder result = new StringBuilder("");
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	
	}
	
	
	public List<DBParamaters> getDBParametersFromConfigFile(){

		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<DBParamaters>>(){}.getType();
		List<DBParamaters> dbConfigList = gson.fromJson(getJsonDBConfigString(), listType);
		return dbConfigList;
	
	}
	
	
	public static void main(String[] args) {
		
		DBParamUtility DBp =new DBParamUtility();
		DBp.getDBParametersFromConfigFile();
		
	}
	
}
