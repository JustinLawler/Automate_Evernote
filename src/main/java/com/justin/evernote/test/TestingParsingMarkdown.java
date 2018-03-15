package com.justin.evernote.test;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 */
public class TestingParsingMarkdown
{
	/**
	 */
	public static void main(String[] args)
	{
		Parser parser = Parser.builder().build();
		Node document = parser.parse("# Title\n## testing123\nThis is *Sparta* in **bold**\n");
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
		System.out.println(html);
		
	}
}
