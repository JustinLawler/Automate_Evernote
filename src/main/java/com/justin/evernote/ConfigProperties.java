/**
 * 
 */
package com.justin.evernote;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.justin.evernote.dataSources.IDataSource;

/**
 */
@Component
@ConfigurationProperties("ConfigProperties")
public class ConfigProperties
{
	private String authToken;
	private String developerToken;		// for sandbox use only

	@Autowired
	private Set<IDataSource> dataSources;

	/**
	 */
	public String getAuthToken()
	{
		return authToken;
	}

	/**
	 */
	public void setAuthToken(String authToken)
	{
		this.authToken = authToken;
	}
	
	public String getDeveloperToken() {
		return developerToken;
	}

	public void setDeveloperToken(String developerToken) {
		this.developerToken = developerToken;
	}
	
	/**
	 */
	public Set<IDataSource> getDataSources()
	{
		return dataSources;
	}

	/**
	 */
	public void setDataSources(Set<IDataSource> dataSources)
	{
		this.dataSources = dataSources;
	}
	
	/**
	 */
	public Map<String, Object> getDataSourceKeyValues() throws Exception
	{
		Map<String, Object> dataSourceKeyValues = new HashMap<String, Object>();
		
		for (IDataSource dataSource : dataSources) {
			Map<String, Object> dsMap = dataSource.getDataMap();
			dataSourceKeyValues.put(dataSource.getDataSourceKey(), dsMap);
		}
		
		return dataSourceKeyValues;
	}
}
