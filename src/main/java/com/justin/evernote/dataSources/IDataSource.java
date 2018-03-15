package com.justin.evernote.dataSources;

import java.util.Map;

/**
 */
public interface IDataSource 
{
	/**
	 */
	String getDataSourceKey();
	
	/**
	 */
	Map<String, Object> getDataMap() throws Exception;
}
