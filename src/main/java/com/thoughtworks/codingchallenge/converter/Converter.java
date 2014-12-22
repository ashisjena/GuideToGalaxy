package com.thoughtworks.codingchallenge.converter;

import java.util.Map;

import com.thoughtworks.codingchallenge.StringHelper;
import com.thoughtworks.codingchallenge.dataobjects.RomanLetter;

public class Converter
{
	public long romanToDecimal( String romanValue )
	{
		long decimalValue = 0;
		final String[] romanLetters = romanValue.split( "(?!^)" );
		RomanLetter prev = RomanLetter.DEMO;
		for ( String letter : romanLetters )
		{
			final RomanLetter rl = RomanLetter.getRomanValueFromString( letter );
			if ( rl.getOrder() > prev.getOrder() )
				decimalValue += rl.getDecimalValue() - ( 2 * prev.getDecimalValue() );
			else
				decimalValue += rl.getDecimalValue();
		}

		return decimalValue;
	}

	public String ArabicToRoman( int number )
	{
		return null;
	}

	public void parseIntergalacticUnitsInRoman( Map<String, Long> intergalaticUnitsToDecimalMap, String inputLine )
	{
		inputLine = inputLine.trim();
		long decimalValue = romanToDecimal( StringHelper.getValuePart( inputLine ) );
		intergalaticUnitsToDecimalMap.put( StringHelper.getIntergalacticUnits( inputLine )[0], decimalValue );
	}

	public void parseIntergalacticUnitsInCredits( Map<String, Long> intergalaticUnitsToArabianCreditsMap, String inputLine )
	{
		inputLine = inputLine.trim();
		String[] intergalacticUnits = StringHelper.getIntergalacticUnits( inputLine );
		
	}
}
