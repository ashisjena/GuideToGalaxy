package com.thoughtworks.codingchallenge;

import com.thoughtworks.codingchallenge.converter.Converter;
import com.thoughtworks.codingchallenge.exception.ConverterException;
import com.thoughtworks.codingchallenge.exception.ReaderException;
import com.thoughtworks.codingchallenge.reader.InputReader;
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
		converter.setValidator( validator );

		InputReader inputReader = new InputReader();
		inputReader.setValidator( validator );
		inputReader.setConverter( converter );

		try
		{
			inputReader.readInputFileAndPrintAnswers();
		}
		catch ( ReaderException | ConverterException e )
		{
			throw new RuntimeException( e );
		}
	}
}