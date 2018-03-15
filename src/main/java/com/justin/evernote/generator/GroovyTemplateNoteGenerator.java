package com.justin.evernote.generator;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.justin.evernote.ConfigProperties;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;
import groovy.text.markup.MarkupTemplateEngine;

/**
 * Uses the groovy template engine to output values. 
 */
@Component
@ConfigurationProperties("GroovyTemplateNoteGenerator")
public class GroovyTemplateNoteGenerator implements INoteGenerator
{
	private static final Logger logger = LogManager.getLogger(GroovyTemplateNoteGenerator.class);
	
	private String templateTagsFileName;
	private String templateTitleFileName;
	private String templateFileName;
	
	@Autowired
    private ConfigProperties props;
	
	/**
	 */
	public GroovyTemplateNoteGenerator()
	{
	}

	/**
	 */
	public GroovyTemplateNoteGenerator(ConfigProperties props)
	{
		this.props = props;
	}
	
	public String getTemplateTagsFileName() {
		return templateTagsFileName;
	}

	public void setTemplateTagsFileName(String templateTagsFileName) {
		this.templateTagsFileName = templateTagsFileName;
	}

	public String getTemplateTitleFileName() {
		return templateTitleFileName;
	}

	public void setTemplateTitleFileName(String templateTitleFileName) {
		this.templateTitleFileName = templateTitleFileName;
	}
	
	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	@Override
	public List<String> generateTags() throws Exception
	{
		logger.debug("generateTags()");
		
		Map<String, Object> replacementVals = props.getDataSourceKeyValues();
		String contents = formatObj(replacementVals, templateTagsFileName, false);
        List<String> tags = Arrays.asList(contents.split("\\r?\\n"));
		
		return tags;
	}

	@Override
	public String generateTitle() throws Exception
	{
		logger.debug("generateTitle()");
		
		Map<String, Object> replacementVals = props.getDataSourceKeyValues();
		replacementVals.put("testing", "123");
		String contents = formatObj(replacementVals, templateTitleFileName, false);
		return contents;
	}

	@Override
	public String generateContents() throws Exception
	{
		logger.debug("generateContents()");
		
		Map<String, Object> replacementVals = props.getDataSourceKeyValues();
		String htmlContents = formatObj(replacementVals, templateFileName, true);
		
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">"
				+ "<en-note>"
				+ htmlContents
				+ "</en-note>";
		
		return content;
	}
	
	/**
	 */
	private String formatObj(Map<String, Object> map, String templateName, boolean outputMarkup) throws Exception
	{
		String finalScript;
		TemplateEngine engine;
		if (outputMarkup) {
			engine = new MarkupTemplateEngine();
		}
		else {
			engine = new SimpleTemplateEngine();
		}
		
		Template template = engine.createTemplate(getTemplateURL(templateName));
		try {
			Writable writable = template.make(map);
			finalScript = writable.toString();
		}
		catch (Exception e) {
			throw new Exception("Problems writing script " + templateName, e);
		}
        
        return finalScript;
	}
	
	/**
	 */
	private URL getTemplateURL(String file) 
	{
		URL resource = this.getClass().getClassLoader().getResource(file);
		if (resource == null) {
			throw new RuntimeException("Resource not found - " + file);
		}
		return resource; 
	}
}
