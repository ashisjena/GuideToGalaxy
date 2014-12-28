package com.thoughtworks.codingchallenge;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntryPointTest
{
	private ByteArrayOutputStream bos;

	@Before
	public void setUp()
	{
		bos = new ByteArrayOutputStream();
		System.setOut( new PrintStream( bos ) );
	}

	@AfterClass
	public static void cleanUp()
	{
		System.setOut( System.out );
	}

	@Test
	public void test() throws InterruptedException
	{
		Thread t1 = new Thread( new WorkThread() );
		t1.start();
		t1.join();
		Assert.assertEquals( "pish tegj glob glob is 42" + System.lineSeparator() + "glob prok Silver is 68 Credits" + System.lineSeparator() + "glob prok Gold is 57800 Credits" + System.lineSeparator() + "glob prok Iron is 782 Credits" + System.lineSeparator() + "I have no idea what you are talking about" + System.lineSeparator(), bos.toString() );
	}
}