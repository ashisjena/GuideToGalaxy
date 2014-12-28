package com.thoughtworks.codingchallenge.converter;

import java.util.List;
import java.util.Map;

import com.thoughtworks.codingchallenge.common.Constants;
import com.thoughtworks.codingchallenge.common.StringHelper;
import com.thoughtworks.codingchallenge.dataobjects.RomanLetter;
import com.thoughtworks.codingchallenge.exception.ConverterException;
import com.thoughtworks.codingchallenge.reader.InputReader.InputAndLineNo;
import com.thoughtworks.codingchallenge.validator.Validator;

public class Converter
{
	private Validator validator;

	public void setValidator( Validator validator )
	{
		this.validator = validator;
	}

	public Long romanToArabic( String romanValue ) throws ConverterException
	{
		if ( !this.validator.validateRomanValue( romanValue ) )
			throw new ConverterException( "Wrong roman Value" );
		long arabicValue = 0;
		final String[] romanLetters = romanValue.split( "(?!^)" );
		RomanLetter prev = RomanLetter.DEMO;
		long prevSum = 0;
		for ( String letter : romanLetters )
		{
			final RomanLetter rl = RomanLetter.getRomanValueFromString( letter );
			if ( rl.getOrder() > prev.getOrder() )
			{
				arabicValue += rl.getArabicValue() - 2 * prevSum;
				prevSum = rl.getArabicValue() - prevSum;
			}
			else
			{
				if ( rl.getOrder() < prev.getOrder() && prev != RomanLetter.DEMO )
					prevSum = 0;
				prevSum += rl.getArabicValue();
				arabicValue += rl.getArabicValue();
			}

			prev = rl;
		}

		return arabicValue;
	}

	public void parseIntergalacticUnitsToRoman( Map<String, String> intergalaticUnitsToRomanMap, List<InputAndLineNo> inputs )
	{
		for ( InputAndLineNo input : inputs )
		{
			if ( !input.getInput().matches( Constants.REGEX__INTERGALACTIC_UNITS_IN_ROMAN ) || !validator.validateRomanValue( StringHelper.getValuePart( input.getInput() ) ) )
				continue;
			String inputLine = input.getInput();
			int index = inputLine.lastIndexOf( " is " );
			intergalaticUnitsToRomanMap.put( inputLine.substring( 0, index ), inputLine.substring( index + " is ".length() ) );
		}
	}

	public void parseAndEvaluateIntergalacticComodityRate( Map<String, String> intergalaticUnitsToRomanMap, Map<String, Double> intergalaticComodityToArabianCreditMap, List<InputAndLineNo> inputs ) throws ConverterException
	{
		for ( InputAndLineNo input : inputs )
		{
			String inputLine = input.getInput();
			if ( !inputLine.matches( Constants.REGEX__NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS ) )
				continue;

			String nonValuePart = inputLine.substring( 0, inputLine.lastIndexOf( " is " ) );
			String[] tokens = nonValuePart.split( "\\s+" );
			StringBuilder noOfComodityInRoman = new StringBuilder();
			boolean hasErrorOccured = false;
			for ( int index = 0; index < tokens.length - 1; index++ )
			{
				String romanValue = intergalaticUnitsToRomanMap.get( tokens[index] );
				if ( romanValue == null )
				{
					hasErrorOccured = true;
					break;
				}
				noOfComodityInRoman.append( romanValue );
			}
			if ( hasErrorOccured )
				continue;

			if ( !validator.validateRomanValue( noOfComodityInRoman.toString() ) )
				continue;

			Long noOfComodity = romanToArabic( noOfComodityInRoman.toString() );

			String comodity = tokens[tokens.length - 1];
			Long totalPrice = Long.parseLong( StringHelper.getValuePart( inputLine ) );
			intergalaticComodityToArabianCreditMap.put( comodity, ( totalPrice.doubleValue() / noOfComodity.doubleValue() ) );
		}
	}
}