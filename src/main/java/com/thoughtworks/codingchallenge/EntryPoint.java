package com.thoughtworks.codingchallenge;

import java.util.List;
import java.util.Map;

import com.thoughtworks.codingchallenge.converter.Converter;
import com.thoughtworks.codingchallenge.dataobjects.InputType;
import com.thoughtworks.codingchallenge.exception.ReaderException;
import com.thoughtworks.codingchallenge.reader.InputReader;
import com.thoughtworks.codingchallenge.reader.InputReader.InputAndLineNo;
import com.thoughtworks.codingchallenge.validator.Validator;

public class EntryPoint
{
	public static void main( String[] args )
	{
		final Thread t1 = new Thread( null, new WorkThread(), "", 256 * ( 1 << 20 ) );
		t1.start();
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
		Map<InputType, List<InputAndLineNo>> inputTypeToLine;
		try
		{
			inputTypeToLine = inputReader.readInputFile();
		}
		catch ( ReaderException e )
		{
			throw new RuntimeException( e );
		}
		
	}
}