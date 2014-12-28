package com.thoughtworks.codingchallenge.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.thoughtworks.codingchallenge.exception.ConverterException;
import com.thoughtworks.codingchallenge.reader.InputReader;
import com.thoughtworks.codingchallenge.reader.InputReader.InputAndLineNo;
import com.thoughtworks.codingchallenge.validator.Validator;

public class ConverterTest
{
	private ExpectedException expectedEx;
	private Converter converter;
	private InputReader inputReader;
	private List<InputAndLineNo> inputsForIntergalacticUnitsToRoman;
	private List<InputAndLineNo> inputsForComodityRatesToRoman;
	private Map<String, String> intergalaticUnitsToRomanMap;
	private HashMap<String, Double> intergalaticComodityToArabianCreditMap;

	@Rule
	public ExpectedException getRule()
	{
		expectedEx = ExpectedException.none();
		return expectedEx;
	}

	@Before
	public void setUp()
	{
		intergalaticComodityToArabianCreditMap = new HashMap<String, Double>();

		converter = new Converter();
		converter.setValidator( new Validator() );

		inputReader = new InputReader();
		inputsForIntergalacticUnitsToRoman = new ArrayList<InputAndLineNo>();
		inputsForIntergalacticUnitsToRoman.add( inputReader.new InputAndLineNo( "glob is IM" ) );
		inputsForIntergalacticUnitsToRoman.add( inputReader.new InputAndLineNo( "pish is MMDDVI" ) );
		inputsForIntergalacticUnitsToRoman.add( inputReader.new InputAndLineNo( "prok is input" ) );

		inputsForComodityRatesToRoman = new ArrayList<InputAndLineNo>();
		inputsForComodityRatesToRoman.add( inputReader.new InputAndLineNo( "glob prok Gold is 57800 Credits" ) );
		intergalaticUnitsToRomanMap = new HashMap<String, String>();
		intergalaticUnitsToRomanMap.put( "glob", "I" );
		intergalaticUnitsToRomanMap.put( "prok", "V" );
	}

	@Test
	public void romanToArabicTest() throws ConverterException
	{
		Assert.assertEquals( 3907L, converter.romanToArabic( "MMMIIIXCM" ).longValue() );
		Assert.assertEquals( 3909L, converter.romanToArabic( "MMMIIIXCMIIIV" ).longValue() );
		Assert.assertEquals( 4409L, converter.romanToArabic( "MMMIIIXCMDIIIV" ).longValue() );
	}

	@Test
	public void romanToArabicWrongValueThrowsExceptionTest() throws ConverterException
	{
		expectedEx.expect( ConverterException.class );
		expectedEx.expectMessage( "Wrong roman Value" );
		converter.romanToArabic( "MMMIIIXCLM" );
	}

	@Test
	public void parseIntergalacticUnitsToRomanTest()
	{
		Map<String, String> intergalaticUnitsToRomanMap = new HashMap<String, String>();
		converter.parseIntergalacticUnitsToRoman( intergalaticUnitsToRomanMap, inputsForIntergalacticUnitsToRoman );
		Assert.assertEquals( "IM", intergalaticUnitsToRomanMap.get( "glob" ) );
	}

	@Test
	public void parseIntergalacticUnitsToRomanWrongInputTest()
	{
		Map<String, String> intergalaticUnitsToRomanMap = new HashMap<String, String>();
		converter.parseIntergalacticUnitsToRoman( intergalaticUnitsToRomanMap, inputsForIntergalacticUnitsToRoman );
		Assert.assertEquals( null, intergalaticUnitsToRomanMap.get( "pish" ) );
		Assert.assertEquals( null, intergalaticUnitsToRomanMap.get( "prok" ) );
	}

	@Test
	public void parseAndEvaluateIntergalacticComodityRateTest() throws ConverterException
	{
		converter.parseAndEvaluateIntergalacticComodityRate( intergalaticUnitsToRomanMap, intergalaticComodityToArabianCreditMap, inputsForComodityRatesToRoman );
		Assert.assertEquals( 14450D, intergalaticComodityToArabianCreditMap.get( "Gold" ), 0D );
	}

	@Test
	public void parseAndEvaluateIntergalacticComodityRateWrongInputTest() throws ConverterException
	{
		inputsForComodityRatesToRoman.add( inputReader.new InputAndLineNo( "glob glob prok prok Silver is 34 Credits" ) );
		inputsForComodityRatesToRoman.add( inputReader.new InputAndLineNo( "glob glob Iron wrong Input" ) );
		inputsForComodityRatesToRoman.add( inputReader.new InputAndLineNo( "glob abc Bronze is 50 Credits" ) );
		converter.parseAndEvaluateIntergalacticComodityRate( intergalaticUnitsToRomanMap, intergalaticComodityToArabianCreditMap, inputsForComodityRatesToRoman );
		Assert.assertEquals( null, intergalaticComodityToArabianCreditMap.get( "Silver" ) );
		Assert.assertEquals( null, intergalaticComodityToArabianCreditMap.get( "Iron" ) );
		Assert.assertEquals( null, intergalaticComodityToArabianCreditMap.get( "Bronze" ) );
	}
}
