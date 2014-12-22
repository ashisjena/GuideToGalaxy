package com.thoughtworks.codingchallenge.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.codingchallenge.Constants;
import com.thoughtworks.codingchallenge.StringHelper;
import com.thoughtworks.codingchallenge.converter.Converter;
import com.thoughtworks.codingchallenge.dataobjects.InputType;
import com.thoughtworks.codingchallenge.exception.ReaderException;
import com.thoughtworks.codingchallenge.validator.Validator;

public class InputReader
{
	private final Validator validator;
	private final Converter converter;
	private final Map<InputType, List<InputAndLineNo>> typeToInputMap = new HashMap<InputType, List<InputAndLineNo>>();
	private final Map<String, Long> intergalaticUnitsToDecimalMap = new HashMap<String, Long>();
	private final Map<String, Long> intergalaticUnitsToArabianCreditsMap = new HashMap<String, Long>();

	public InputReader( Validator validator, Converter converter )
	{
		this.validator = validator;
		this.converter = converter;
		initializeTypeToInputMap();
	}

	private void initializeTypeToInputMap()
	{
		typeToInputMap.put( InputType.INTERGALACTIC_UNITS_IN_ROMAN, new ArrayList<InputAndLineNo>() );
		typeToInputMap.put( InputType.GROUPOF_INTERGALACTIC_UNITS_IN_ARABIAN_CREDITS, new ArrayList<InputAndLineNo>() );
		typeToInputMap.put( InputType.QUESTION_FOR_DECIMAL_PRICE, new ArrayList<InputAndLineNo>() );
		typeToInputMap.put( InputType.QUESTION_FOR_ARABIAN_CREDITS, new ArrayList<InputAndLineNo>() );
	}

	public Map<InputType, List<InputAndLineNo>> readInputFile() throws ReaderException
	{
		return readInputFile( Constants.INPUT_FILE );
	}

	public Map<InputType, List<InputAndLineNo>> readInputFile( String fileName ) throws ReaderException
	{
		final BufferedReader br;
		try
		{
			br = new BufferedReader( new FileReader( fileName ) );
			String inputLine = br.readLine().trim();
			int lineNo = 1;
			while ( inputLine != null && !inputLine.matches( "//s+" ) )
			{
				if ( inputLine.matches( Constants.REGEX_INTERGALACTIC_UNITS_IN_ROMAN ) )
				{
					if ( !validator.validateRomanValue( StringHelper.getValuePart( inputLine ) ) )
						continue;
					converter.parseIntergalacticUnitsInRoman( intergalaticUnitsToDecimalMap, inputLine );
					typeToInputMap.get( InputType.INTERGALACTIC_UNITS_IN_ROMAN ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				}
				else if ( inputLine.matches( Constants.REGEX_INTERGALACTIC_UNITS_IN_ARABIAN_CREDITS ) )
				{
					converter.parseIntergalacticUnitsInCredits( intergalaticUnitsToArabianCreditsMap, inputLine );
					typeToInputMap.get( InputType.GROUPOF_INTERGALACTIC_UNITS_IN_ARABIAN_CREDITS ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				}
				else if ( inputLine.matches( Constants.REGEX_QUESTION_FOR_DECIMAL_PRICE ) )
					typeToInputMap.get( InputType.QUESTION_FOR_DECIMAL_PRICE ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				else if ( inputLine.matches( Constants.REGEX_QUESTION_FOR_ARABIAN_CREDITS ) )
					typeToInputMap.get( InputType.QUESTION_FOR_ARABIAN_CREDITS ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				else if ( inputLine.endsWith( Constants.QUESTION_LITERAL ) )
					typeToInputMap.get( InputType.INVALID_INPUT ).add( new InputAndLineNo( inputLine, lineNo++ ) );

				inputLine = br.readLine().trim();
			}
			br.close();
		}
		catch ( IOException e )
		{
			throw new ReaderException( e );
		}

		return typeToInputMap;
	}

	public class InputAndLineNo implements Comparable<InputAndLineNo>
	{
		private Integer lineNo;
		private String input;

		public InputAndLineNo( String input, int lineNo )
		{
			this.input = input;
			this.lineNo = lineNo;
		}

		public InputAndLineNo( String input )
		{
			this.input = input;
			this.lineNo = Integer.MAX_VALUE;
		}

		@Override
		public boolean equals( Object obj )
		{
			if ( obj == null || !( obj instanceof InputAndLineNo ) )
				return false;

			if ( ( ( InputAndLineNo ) obj ).input.equals( input ) )
				return true;
			else
				return false;
		}

		@Override
		public int compareTo( InputAndLineNo other )
		{
			return this.lineNo.compareTo( other.lineNo );
		}
	}
}