/**
 * 
 */
package com.justin.evernote.client;

import com.evernote.edam.type.Notebook;

/**
 */
public interface IEvernoteInterface
{
	/**
	 */
	Notebook getNotebookWithName(String name) throws Exception;
	
	/**
	 */
	void createNoteWithHTML(String notebookName) throws Exception;
}
