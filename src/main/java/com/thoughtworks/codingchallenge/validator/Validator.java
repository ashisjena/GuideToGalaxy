package com.thoughtworks.codingchallenge.validator;

import com.thoughtworks.codingchallenge.common.Constants;
import com.thoughtworks.codingchallenge.dataobjects.RomanLetter;

public class Validator
{
	public boolean validateRomanValue( String romanValue )
	{
		if ( !romanValue.matches( Constants.REGEX_ROMAN_NUMBER ) )
			return false;

		final String[] romanLetters = romanValue.split( "(?!^)" );

		RomanLetter prev = RomanLetter.DEMO;
		RomanLetter violatedLetter = RomanLetter.DEMO;
		int noOfRepeatation = 1;
		for ( int index = 0; index < romanLetters.length; index++ )
		{
			final RomanLetter rl = RomanLetter.getRomanValueFromString( romanLetters[index] );

			if ( rl.getOrder() > prev.getOrder() )
			{
				if ( !prev.isSubtractable() )
					return false;
				else if ( !prev.isValidHigherValue( rl ) || !backTrack( romanLetters, index, rl, rl.getOrder() ) )
				{
					if ( violatedLetter != RomanLetter.DEMO && violatedLetter != prev )
						return false;
					else
						violatedLetter = prev;
				}
			}

			if ( rl == prev )
			{
				if ( !rl.isRepeatable() || ++noOfRepeatation > 3 )
					return false;
				else
				{
					for ( int i = index - 2; i >= 0; i-- )
					{
						RomanLetter romLetter = RomanLetter.getRomanValueFromString( romanLetters[i] );
						if ( romLetter == prev )
							continue;
						else if ( romLetter.getOrder() < prev.getOrder() )
							return false;
						else
							break;
					}
				}
			}
			else
				noOfRepeatation = 1;

			prev = rl;
		}

		return true;
	}

	private boolean backTrack( String[] romanLetters, int index, RomanLetter romanLetter, int maxOrder )
	{
		if ( --index < 0 )
			return true;

		RomanLetter prev = RomanLetter.getRomanValueFromString( romanLetters[index] );

		if ( prev.getOrder() > romanLetter.getOrder() )
			if ( prev.getOrder() < maxOrder )
				return false;
			else
				return true;

		return backTrack( romanLetters, index, prev, maxOrder );
	}
}