package com.thoughtworks.codingchallenge.validator;

import com.thoughtworks.codingchallenge.Constants;
import com.thoughtworks.codingchallenge.dataobjects.RomanLetter;

public class Validator
{
	public Validator()
	{
		initialize();
	}

	private void initialize()
	{

	}

	public boolean validateRomanValue( String romanValue )
	{
		if ( !romanValue.matches( Constants.REGEX_ROMAN_NUMBER ) )
			return false;
		else if ( romanValue.matches( Constants.REGEX_ROMAN_NUMBER_REPEATING_MORE_THAN_3 ) )
			return false;

		final String[] romanLetters = romanValue.split( "(?!^)" );

		RomanLetter prev = RomanLetter.DEMO;
		RomanLetter violatedLetter = RomanLetter.DEMO;
		for ( String str : romanLetters )
		{
			final RomanLetter rl = RomanLetter.getRomanValueFromString( str );
			if ( rl.getOrder() > prev.getOrder() && !prev.isValidHigherValue( rl ) )
				if ( violatedLetter != RomanLetter.DEMO && violatedLetter != prev )
					return false;
				else
					violatedLetter = prev;
			
			prev = rl;
		}

		return true;
	}

	class ErrorHolder
	{
		int lineNo;
		String message;
	}

}