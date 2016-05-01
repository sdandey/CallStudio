package com.montiefiore.ivr.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.montiefiore.ivr.vxml.DynamicFlow;
import com.sun.istack.internal.logging.Logger;

public class DynamicFlowService {

	Logger logger = Logger.getLogger(DynamicFlowService.class);
	
	public DynamicFlow getDynamicFlowForDnis(String dnisNumber){
		
		File dynamicMenujson = new File(this.getClass().getClassLoader().getResource("dynamicmenu.json").getFile());
		
		if(dynamicMenujson.canRead()){
			logger.info("dynamicmenu.json file found");
			logger.info("Passed DNIS Number:" + dnisNumber);

			Gson gson = new Gson();
			Type type = new TypeToken<List<DynamicFlow>>() {
			}.getType();
			try {
				List<DynamicFlow> dynamicFlows = gson.fromJson(new FileReader(dynamicMenujson), type);
				
				for (DynamicFlow dynamicFlow : dynamicFlows) {
					if(dnisNumber.equals(dynamicFlow.getDnisNumber())){
						logger.info("Found dynamic flow configuration for DNIS:" + dnisNumber);
						logger.info("Dynamic Flow Details:" + dynamicFlow.toString());
						return dynamicFlow;
					}
				}
				
				logger.severe("No Dynamic Flow Configuration found for DNIS:" + dnisNumber);
				return null;
			} catch (JsonIOException e) {
				e.printStackTrace();
				return null;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				logger.severe("Error in the json Syntax");
				return null;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		else{
			logger.severe("dynamicmenu.json file not found");
			return null;
		}
	}
	
	
}
