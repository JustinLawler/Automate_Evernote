package com.justin.evernote.dataSources;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 */
public class TestTextLinesFromFileDataSource
{
	@Test
	public void test() throws Exception
	{
		// configuring the object
		TextLinesFromFileDataSource ds = new TextLinesFromFileDataSource();
		ds.setDirectoryName("datasources");
		ds.setTextFileNames(Arrays.asList("test_goals1", "test_goals2"));
		ds.setFileNameSuffix("txt");
		
		Map<String, Object> data = ds.getDataMap();
		assertEquals(2, data.size());
		assertTrue(data.containsKey("test_goals1"));
		assertTrue(data.containsKey("test_goals2"));
		
		assertTrue(List.class.isInstance(data.get("test_goals1")));
		List vals1 = (List)data.get("test_goals1");
		assertEquals(3, vals1.size());
		assertEquals("test goal1 - 1", vals1.get(0));
		assertEquals("test goal1 - 2", vals1.get(1));
		assertEquals("test goal1 - 3", vals1.get(2));
		
		assertTrue(List.class.isInstance(data.get("test_goals2")));
		List vals2 = (List)data.get("test_goals2");
		assertEquals(3, vals2.size());
		assertEquals("test goal2 - 1", vals2.get(0));
		assertEquals("test goal2 - 2", vals2.get(1));
		assertEquals("test goal2 - 3", vals2.get(2));
	}
}
