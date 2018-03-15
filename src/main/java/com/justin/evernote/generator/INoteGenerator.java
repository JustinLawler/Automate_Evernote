package com.justin.evernote.generator;

import java.util.List;

/**
 * 
 */
public interface INoteGenerator
{

	/**
	 */
	public List<String> generateTags() throws Exception;
	
	/**
	 */
	public String generateTitle() throws Exception;

	/**
	 */
	public String generateContents() throws Exception;
}
