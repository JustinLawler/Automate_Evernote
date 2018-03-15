package com.justin.evernote;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.evernote.edam.error.EDAMErrorCode;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.transport.TTransportException;
import com.justin.evernote.client.IEvernoteInterface;


/**
 */
@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner, ApplicationContextAware
{
	private static final Logger logger = LogManager.getLogger(SpringBootConsoleApplication.class);
	
	private ApplicationContext applicationContext;
	
	/**
	 */
    public static void main(String[] args) throws Exception
    {
    		SpringApplication.run(SpringBootConsoleApplication.class, args);
    }
    
    /**
	 */
	public @PostConstruct void init()
	{
		logger.debug("init()");
	}
	
	/**
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}

    @Override
    public void run(String... args) throws Exception
    {
    		logger.debug("run()");
    		
    		IEvernoteInterface bean = applicationContext.getBean(IEvernoteInterface.class);
    		try {
    			bean.createNoteWithHTML("Notes_From_API");
    		}
    		catch (EDAMUserException e) {
    			// These are the most common error types that you'll need to
    			// handle
    			// EDAMUserException is thrown when an API call fails because a
    			// parameter was invalid.
    			if (e.getErrorCode() == EDAMErrorCode.AUTH_EXPIRED) {
    				System.err.println("Your authentication token is expired!");
    			} else if (e.getErrorCode() == EDAMErrorCode.INVALID_AUTH) {
    				System.err.println("Your authentication token is invalid!");
    			} else if (e.getErrorCode() == EDAMErrorCode.QUOTA_REACHED) {
    				System.err.println("Your authentication token is invalid!");
    			} else {
    				System.err.println("Error: " + e.getErrorCode().toString()
    						+ " parameter: " + e.getParameter());
    			}
    		}
    		catch (EDAMSystemException e) {
    			System.err.println("System error: " + e.getErrorCode().toString());
    		}
    		catch (TTransportException t) {
    			System.err.println("Networking error: " + t.getMessage());
    		}
    }
}
