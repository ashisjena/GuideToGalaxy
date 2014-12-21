package com.thoughtworks.codingchallenge;

import com.thoughtworks.codingchallenge.converter.Converter;
import com.thoughtworks.codingchallenge.reader.InputReader;
import com.thoughtworks.codingchallenge.validator.Validator;

public class EntryPoint
{
	public static void main( String[] args )
	{
		final Thread t1 = new Thread( null, new WorkThread(), "", 256 * ( 1 << 20 ) );
		t1.start();
		try
		{
			t1.join();
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
			System.out.println( "Thread has been interupted" );
		}
	}
}

class WorkThread implements Runnable
{
	@Override
	public void run()
	{
		Validator validator = new Validator();
		Converter converter = new Converter();
		InputReader inputReader = new InputReader( validator, converter );
		inputReader.readFile();
	}
}