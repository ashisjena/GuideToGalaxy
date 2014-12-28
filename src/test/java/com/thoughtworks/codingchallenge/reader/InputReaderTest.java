package com.thoughtworks.codingchallenge.reader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.thoughtworks.codingchallenge.converter.Converter;
import com.thoughtworks.codingchallenge.exception.ConverterException;
import com.thoughtworks.codingchallenge.exception.ReaderException;
import com.thoughtworks.codingchallenge.validator.Validator;

public class InputReaderTest
{
	private ByteArrayOutputStream bos;
	private InputReader inputReader;
	private ExpectedException expectedEx;

	@Rule
	public ExpectedException getRule()
	{
		expectedEx = ExpectedException.none();
		return expectedEx;
	}

	@Before
	public void setUp()
	{
		bos = new ByteArrayOutputStream();
		System.setOut( new PrintStream( bos ) );

		inputReader = new InputReader();
		Validator validator = new Validator();
		Converter converter = new Converter();
		converter.setValidator( validator );
		inputReader.setValidator( validator );
		inputReader.setConverter( converter );
	}

	@AfterClass
	public static void cleanUp()
	{
		System.setOut( System.out );
	}

	@Test
	public void readInputFileAndPrintAnswersTest() throws ReaderException, ConverterException
	{
		inputReader.readInputFileAndPrintAnswers();
		Assert.assertEquals( "pish tegj glob glob is 42" + System.lineSeparator() + "glob prok Silver is 68 Credits" + System.lineSeparator() + "glob prok Gold is 57800 Credits" + System.lineSeparator() + "glob prok Iron is 782 Credits" + System.lineSeparator() + "I have no idea what you are talking about" + System.lineSeparator(), bos.toString() );
	}

	@Test
	public void readInputFileAndPrintAnswersWithOtherInputFileTest() throws ReaderException, ConverterException
	{
		inputReader.readInputFileAndPrintAnswers( "TestInput1" );
		Assert.assertEquals( "Wrong order of intergalactic unit is asked" + System.lineSeparator() + "pish glob prok is 16" + System.lineSeparator() + "I have no idea what you are talking about" + System.lineSeparator(), bos.toString() );
	}

	@Test
	public void readInputFileAndPrintAnswersWrongFilePathThrowsExceptionTest() throws ReaderException, ConverterException
	{
		expectedEx.expect( NullPointerException.class );
		inputReader.readInputFileAndPrintAnswers( "WrongFile" );
	}
}