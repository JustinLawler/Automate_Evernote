package com.justin.evernote.dataSources;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 */
@Component
@ConfigurationProperties("TextLinesFromFileDataSource")
public class TextLinesFromFileDataSource implements IDataSource
{
	public static final String KEY = "TEXT_FILE";
	
	private String directoryName;
	@Value("#{'${TextLinesFromFileDataSource.textFileNames}'.split(',')}") 
	private List<String> textFileNames;
	private String fileNameSuffix;

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	
	public List<String> getTextFileNames() {
		return textFileNames;
	}

	public void setTextFileNames(List<String> textFileNames) {
		this.textFileNames = textFileNames;
	}

	public String getFileNameSuffix() {
		return fileNameSuffix;
	}

	public void setFileNameSuffix(String fileNameSuffix) {
		this.fileNameSuffix = fileNameSuffix;
	}
	
	/**
	 */
	@Override
	public String getDataSourceKey()
	{
		return KEY;
	}

	/**
	 */
	@Override
	public Map<String, Object> getDataMap() throws Exception
	{
		Map<String, Object> vals = new HashMap<String, Object>();
		for (String textFileName : textFileNames) {
			List<String> lines = valuesInFile(textFileName);
			vals.put(textFileName, lines);	
		}
		
		return vals;
	}
	
	/**
	 */
	private List<String> valuesInFile(String textFileName) throws Exception
	{
		File file = getTemplateURL(textFileName);
		List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
		return lines;
	}
	
	/**
	 */
	private File getTemplateURL(String textFileName)
	{
		String fileName = FilenameUtils.concat(directoryName, textFileName + "." + fileNameSuffix);
		URL resource = this.getClass().getClassLoader().getResource(fileName);
		if (resource == null) {
			throw new RuntimeException("Resource not found - " + fileName);
		}
		return new File(resource.getFile()); 
	}
}


