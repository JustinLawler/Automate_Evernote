package com.justin.evernote.generator;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import com.justin.evernote.ConfigProperties;
import com.justin.evernote.dataSources.DateDataSource;

/**
 */
public class TestGroovyTemplateNoteGenerator
{
	GroovyTemplateNoteGenerator generator;
	
	ConfigProperties props;
	
	@org.junit.Before
	public void setUp()
	{
		DateDataSource dateDataSource = new DateDataSource();
		
		props = new ConfigProperties();
		props.setDataSources(new HashSet<>(Arrays.asList(dateDataSource)));
		
		String dir = "templates/groovy/";
		generator = new GroovyTemplateNoteGenerator(props);
		generator.setTemplateTitleFileName(dir + "testing_groovy_template_title.tpl");
		generator.setTemplateTagsFileName(dir + "testing_groovy_template_tags.tpl");
		generator.setTemplateFileName(dir + "testing_groovy_templates_no_goals.tpl");
	}
	
	
	@org.junit.Test
	public void testGenerateContents() throws Exception
	{
		String contents = generator.generateContents();
		assertNotNull(contents);
		assertTrue(contents.indexOf("Medium Title - ") > 0);
	}
	
	@org.junit.Test
	public void testGenerateTags() throws Exception
	{
		List<String> tags = generator.generateTags();
		assertNotNull(tags);
		assertEquals(4, tags.size());
		
		assertTrue(tags.contains("testing123"));
		SimpleDateFormat dayFormatter = new SimpleDateFormat("EEEEE");
		GregorianCalendar gc = new GregorianCalendar();
		String dayOfWeek = dayFormatter.format(gc.getTime());
		assertTrue(tags.contains(dayOfWeek));
	}
	
	@org.junit.Test
	public void testGenerateTitle() throws Exception
	{
		String title = generator.generateTitle();
		assertNotNull(title);
		assertTrue("wrong text in [" + title + "]", title.indexOf("Title") > 0);
	}
}
