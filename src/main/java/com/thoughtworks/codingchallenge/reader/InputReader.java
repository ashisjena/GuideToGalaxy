package com.thoughtworks.codingchallenge.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.thoughtworks.codingchallenge.common.Constants;
import com.thoughtworks.codingchallenge.converter.Converter;
import com.thoughtworks.codingchallenge.dataobjects.InputType;
import com.thoughtworks.codingchallenge.exception.ConverterException;
import com.thoughtworks.codingchallenge.exception.ReaderException;
import com.thoughtworks.codingchallenge.validator.Validator;

public class InputReader
{
	private Converter converter;
	private Validator validator;
	private final Map<InputType, List<InputAndLineNo>> typeToInputMap = new HashMap<InputType, List<InputAndLineNo>>();
	private final Map<String, String> intergalaticUnitsToRomanMap = new HashMap<String, String>();
	private final Map<String, Double> intergalaticComodityToArabianCreditMap = new HashMap<String, Double>();

	public InputReader()
	{
		initializeTypeToInputMap();
	}

	public void setConverter( Converter converter )
	{
		this.converter = converter;
	}

	public void setValidator( Validator validator )
	{
		this.validator = validator;
	}

	private void initializeTypeToInputMap()
	{
		typeToInputMap.put( InputType.INTERGALACTIC_UNITS_IN_ROMAN, new ArrayList<InputAndLineNo>() );
		typeToInputMap.put( InputType.NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS, new ArrayList<InputAndLineNo>() );
		typeToInputMap.put( InputType.QUESTION_INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS, new ArrayList<InputAndLineNo>() );
		typeToInputMap.put( InputType.QUESTION_NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS, new ArrayList<InputAndLineNo>() );
		typeToInputMap.put( InputType.INVALID_QUESTION_GRAMMAR, new ArrayList<InputReader.InputAndLineNo>() );
		typeToInputMap.put( InputType.INVALID_INPUT_GRAMMAR, new ArrayList<InputReader.InputAndLineNo>() );
	}

	public void readInputFileAndPrintAnswers() throws ReaderException, ConverterException
	{
		readInputFileAndPrintAnswers( Constants.INPUT_FILE );
	}

	public void readInputFileAndPrintAnswers( String fileName ) throws ReaderException, ConverterException
	{
		try (final BufferedReader br = new BufferedReader( new InputStreamReader( getClass().getResourceAsStream( "/" + fileName ) ) ))
		{
			String inputLine = br.readLine();
			int lineNo = 1;
			while ( inputLine != null && !inputLine.matches( "//s+" ) )
			{
				inputLine = inputLine.trim();
				if ( inputLine.matches( Constants.REGEX__INTERGALACTIC_UNITS_IN_ROMAN ) )
					typeToInputMap.get( InputType.INTERGALACTIC_UNITS_IN_ROMAN ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				else if ( inputLine.matches( Constants.REGEX__NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS ) )
					typeToInputMap.get( InputType.NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				else if ( inputLine.matches( Constants.REGEX_QUESTION__INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS ) )
					typeToInputMap.get( InputType.QUESTION_INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				else if ( inputLine.matches( Constants.REGEX_QUESTION__NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS ) )
					typeToInputMap.get( InputType.QUESTION_NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				else if ( inputLine.endsWith( Constants.QUESTION_LITERAL ) )
					typeToInputMap.get( InputType.INVALID_QUESTION_GRAMMAR ).add( new InputAndLineNo( inputLine, lineNo++ ) );
				else
					typeToInputMap.get( InputType.INVALID_INPUT_GRAMMAR ).add( new InputAndLineNo( inputLine, lineNo++ ) );

				inputLine = br.readLine();
			}
		}
		catch ( IOException e )
		{
			throw new ReaderException( e );
		}

		parseAndEvaluateInputs();
		for ( QuestionAnswer questionAnswer : evaluateQuestions() )
			System.out.println( questionAnswer.getAnswer() );
	}

	private void parseAndEvaluateInputs() throws ConverterException
	{
		converter.parseIntergalacticUnitsToRoman( intergalaticUnitsToRomanMap, typeToInputMap.get( InputType.INTERGALACTIC_UNITS_IN_ROMAN ) );
		converter.parseAndEvaluateIntergalacticComodityRate( intergalaticUnitsToRomanMap, intergalaticComodityToArabianCreditMap, typeToInputMap.get( InputType.NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS ) );
	}

	private SortedSet<QuestionAnswer> evaluateQuestions() throws ConverterException
	{
		final SortedSet<QuestionAnswer> orderedQuestionAnswer = new TreeSet<QuestionAnswer>();
		evaluateIntergalacticUnitsInRomanQuestions( orderedQuestionAnswer, typeToInputMap.get( InputType.QUESTION_INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS ) );
		evaluateComodityValueQuestions( orderedQuestionAnswer, typeToInputMap.get( InputType.QUESTION_NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS ) );
		addWrongQuestionsAndResponse( orderedQuestionAnswer, typeToInputMap.get( InputType.INVALID_QUESTION_GRAMMAR ) );
		return Collections.unmodifiableSortedSet( orderedQuestionAnswer );
	}

	private void addWrongQuestionsAndResponse( Set<QuestionAnswer> orderedQuestionAnswer, List<InputAndLineNo> listOfQuestions )
	{
		for ( InputAndLineNo input : listOfQuestions )
			orderedQuestionAnswer.add( new QuestionAnswer( input, Constants.MSG_WRONG_QUESTION ) );
	}

	private void evaluateComodityValueQuestions( Set<QuestionAnswer> orderedQuestionAnswer, List<InputAndLineNo> listOfQuestions ) throws ConverterException
	{
		for ( InputAndLineNo input : listOfQuestions )
		{
			boolean hasErrorOccured = false;
			StringBuilder sb = new StringBuilder();
			String[] tokens = input.getInput().replaceAll( Constants.REGEX_QUESTION_PART_FOR_COMODITY_PRICE_IN_ARABIAN_CREDITS, Constants.EMPTY_STRING ).split( " " );
			for ( int index = 0; index < tokens.length - 1; index++ )
				if ( !checkForIntergalacticUnitsAndAppend( orderedQuestionAnswer, input, tokens[index], sb ) )
				{
					hasErrorOccured = true;
					break;
				}

			if ( hasErrorOccured )
				continue;

			if ( !validator.validateRomanValue( sb.toString() ) )
				orderedQuestionAnswer.add( new QuestionAnswer( input, Constants.MSG_WRONG_INTERGALACTIC_UNIT_ORDER ) );
			else
			{
				final String nameOfComodity = tokens[tokens.length - 1];
				final Double valueOfComodity = intergalaticComodityToArabianCreditMap.get( nameOfComodity );
				if ( valueOfComodity == null )
				{
					if ( checkInWrongInputs( nameOfComodity, InputType.INVALID_QUESTION_GRAMMAR ) )
						orderedQuestionAnswer.add( new QuestionAnswer( input, Constants.MSG_GRAMMAR_ERROR ) );
					else
						orderedQuestionAnswer.add( new QuestionAnswer( input, Constants.MSG_INTERGALACTIC_UNIT_NOT_FOUND ) );
				}
				else
					orderedQuestionAnswer.add( new QuestionAnswer( input, input.getInput().replaceAll( Constants.REGEX_QUESTION_PART_FOR_COMODITY_PRICE_IN_ARABIAN_CREDITS, Constants.EMPTY_STRING ) + " is " + String.format( "%.0f", converter.romanToArabic( sb.toString() ) * valueOfComodity ) + " Credits" ) );
			}
		}
	}

	private void evaluateIntergalacticUnitsInRomanQuestions( Set<QuestionAnswer> orderedQuestionAnswer, List<InputAndLineNo> listOfQuestions ) throws ConverterException
	{
		for ( InputAndLineNo input : listOfQuestions )
		{
			boolean hasErrorOccured = false;
			StringBuilder sb = new StringBuilder();
			for ( String intergalacticUnit : input.getInput().replaceAll( Constants.REGEX_QUESTION_PART_INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS, Constants.EMPTY_STRING ).split( " " ) )
				if ( !checkForIntergalacticUnitsAndAppend( orderedQuestionAnswer, input, intergalacticUnit, sb ) )
				{
					hasErrorOccured = true;
					break;
				}

			if ( hasErrorOccured )
				continue;

			if ( !validator.validateRomanValue( sb.toString() ) )
				orderedQuestionAnswer.add( new QuestionAnswer( input, Constants.MSG_WRONG_INTERGALACTIC_UNIT_ORDER ) );
			else
				orderedQuestionAnswer.add( new QuestionAnswer( input, input.getInput().replaceAll( Constants.REGEX_QUESTION_PART_INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS, Constants.EMPTY_STRING ) + " is " + converter.romanToArabic( sb.toString() ).toString() ) );
		}
	}

	private boolean checkForIntergalacticUnitsAndAppend( Set<QuestionAnswer> orderedQuestionAnswer, InputAndLineNo input, String intergalacticUnit, StringBuilder sb )
	{
		String romanValue = intergalaticUnitsToRomanMap.get( intergalacticUnit );
		if ( romanValue == null )
		{
			if ( checkInWrongInputs( romanValue, InputType.INVALID_INPUT_GRAMMAR ) )
				orderedQuestionAnswer.add( new QuestionAnswer( input, Constants.MSG_GRAMMAR_ERROR ) );
			else
				orderedQuestionAnswer.add( new QuestionAnswer( input, Constants.MSG_INTERGALACTIC_UNIT_NOT_FOUND ) );

			return false;
		}
		else
			sb.append( romanValue );

		return true;
	}

	private boolean checkInWrongInputs( String input, InputType type )
	{
		for ( InputAndLineNo wrongInput : typeToInputMap.get( type ) )
			if ( wrongInput.getInput().contains( input ) )
				return true;

		return false;
	}

	public class QuestionAnswer implements Comparable<QuestionAnswer>
	{
		private InputAndLineNo input;
		private String reply;

		public QuestionAnswer( InputAndLineNo input, String reply )
		{
			this.input = input;
			this.reply = reply;
		}

		public String getQuestion()
		{
			return input.getInput();
		}

		public String getAnswer()
		{
			return reply;
		}

		public int getLineNo()
		{
			return input.getLineNo();
		}

		@Override
		public int compareTo( QuestionAnswer other )
		{
			return this.input.lineNo.compareTo( other.input.lineNo );
		}
	}

	public class InputAndLineNo
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

		public String getInput()
		{
			return input;
		}

		public Integer getLineNo()
		{
			return lineNo;
		}
	}
}