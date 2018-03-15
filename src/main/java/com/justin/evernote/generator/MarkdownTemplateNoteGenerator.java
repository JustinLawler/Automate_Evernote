package com.justin.evernote.generator;

import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Testing default implentation to render from markdown.
 * 
 * Note - this is dump. This won't be able to substitute in variables like dates or external data into it.
 * 
 * TODO:
 * Read title in from markdown file - configured from the template name 
 * Read tags in from markdown file
 */
@Component
@ConfigurationProperties("MarkdownTemplateNoteGenerator")
public class MarkdownTemplateNoteGenerator implements INoteGenerator 
{
	private String templateName;
	
	/**
	 */
	public MarkdownTemplateNoteGenerator()
	{
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Override
	public List<String> generateTags()
	{
		// TODO
		List<String> tags = new ArrayList<String>();
		tags.add("testing12345");
		
		return tags;
	}

	@Override
	public String generateTitle()
	{
		// TODO
		
		SimpleDateFormat fullDateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		GregorianCalendar gc = new GregorianCalendar();
		
		return "Test note from HTML text - " + fullDateFormatter.format(gc.getTime());
	}

	@Override
	public String generateContents() throws Exception
	{
		String htmlContents = createHTMLFromMarkdown(templateName);
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">"
				+ "<en-note>"
				+ htmlContents
				+ "</en-note>";
		
		return content;
	}

	/**
	 */
	private String createHTMLFromMarkdown(String markdownFile) throws Exception
	{
		URL classURL = this.getClass().getClassLoader().getResource(markdownFile);
		String markdownContents = IOUtils.toString(classURL, Charset.defaultCharset());
		
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdownContents);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		String html = renderer.render(document);
		
		return html;
	}
}
