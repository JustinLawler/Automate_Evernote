package com.justin.evernote.client;
/*
  Evernote API sample code, structured as a simple command line
  application that demonstrates several API calls.

  To run:
    java -classpath ../../target/evernote-api-*.jar GenerateNote

  Full documentation of the Evernote API can be found at 
  http://dev.evernote.com/documentation/cloud/
 */

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.TException;
import com.justin.evernote.ConfigProperties;
import com.justin.evernote.SpringBootConsoleApplication;
import com.justin.evernote.generator.GroovyTemplateNoteGenerator;

/**
 */
@Component
@ConfigurationProperties("GenerateEvernoteBean")
public class GenerateEvernoteBean implements IEvernoteInterface
{
	private static final Logger logger = LogManager.getLogger(GenerateEvernoteBean.class);
	
	@Autowired
    private ConfigProperties props;

//	private INoteGenerator noteGenerator = new MarkdownTemplateNoteGenerator();

	// TODO - make this configurable to something extensible!!
	@Autowired
	private GroovyTemplateNoteGenerator noteGenerator = new GroovyTemplateNoteGenerator();
	
	private UserStoreClient userStore;
	private NoteStoreClient noteStore;
	private String newNoteGuid;

	/**
	 * Intialize UserStore and NoteStore clients. During this step, we
	 * authenticate with the Evernote web service. All of this code is boilerplate
	 * - you can copy it straight into your application.
	 */
	public GenerateEvernoteBean() throws Exception
	{
		logger.debug("new GenerateEvernoteBean()");
	}
	
    /**
	 */
	public @PostConstruct void init()
	{
		logger.info("init()");
		
		// Set up the UserStore client and check that we can speak to the server
		String authToken = props.getAuthToken();
		
		// TODO: Make configurable
		EvernoteAuth evernoteAuth = new EvernoteAuth(EvernoteService.SANDBOX, authToken);
		
		try {
			ClientFactory factory = new ClientFactory(evernoteAuth);
			userStore = factory.createUserStoreClient();
	
			boolean versionOk = userStore.checkVersion("Evernote EDAMDemo (Java)",
					com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
					com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
			if (!versionOk) {
				System.err.println("Incompatible Evernote client protocol version");
				System.exit(1);
			}
	
			// Set up the NoteStore client
			noteStore = factory.createNoteStoreClient();
		}
		catch (TException e) {
			throw new RuntimeException("Problems initialising connection to evernote", e);
		}
		catch (EDAMUserException e) {
			throw new RuntimeException("Problems initialising connection to evernote", e);
		}
		catch (EDAMSystemException e) {
			throw new RuntimeException("Problems initialising connection to evernote", e);
		}
	}
	
	/**
	 */
	public Notebook getNotebookWithName(String name) throws Exception
	{
		logger.info("getNotebookWithName(" + name + ")");
		
		// First, get a list of all notebooks
		List<Notebook> notebooks = noteStore.listNotebooks();
		
		for (Notebook notebook : notebooks) {
			logger.debug("Notebook: " + notebook.getName());
			
			if (!StringUtils.isBlank(notebook.getName())) {
				if (notebook.getName().equals(name)) {
					return notebook;
				}
			}
			else {
				throw new Exception("Empty note name!!");
			}
		}
		
		return null;
	}
	
	/**
	 * Create a new note containing HTML
	 */
	public void createNoteWithHTML(String notebookName) throws Exception
	{
		logger.debug("createNoteWithHTML(" + notebookName + ")");
		
		Notebook notebook = getNotebookWithName(notebookName);
		if (notebook == null) {
			throw new Exception("Can't find notebook " + notebookName);
		}
		
		// To create a new note, simply create a new Note object and fill in
		// attributes such as the note's title.
		Note note = new Note();
		note.setTitle(noteGenerator.generateTitle());
		note.setNotebookGuid(notebook.getGuid());
		
		List<String> tags = noteGenerator.generateTags();
		for (String tag : tags) {
			note.addToTagNames(tag);
		}

		// The content of an Evernote note is represented using Evernote Markup
		// Language (ENML). The full ENML specification can be found in the Evernote API
		// Overview at http://dev.evernote.com/documentation/cloud/chapters/ENML.php
		note.setContent(noteGenerator.generateContents());

		// Finally, send the new note to Evernote using the createNote method
		// The new Note object that is returned will contain server-generated
		// attributes such as the new note's unique GUID.
		Note createdNote = noteStore.createNote(note);
		newNoteGuid = createdNote.getGuid();

		logger.debug("Successfully created a new note with GUID: " + newNoteGuid);
	}
}
