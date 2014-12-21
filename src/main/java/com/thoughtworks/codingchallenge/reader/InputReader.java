package com.thoughtworks.codingchallenge.reader;

import com.thoughtworks.codingchallenge.converter.Converter;
import com.thoughtworks.codingchallenge.validator.Validator;

public class InputReader
{
	private final Validator validator;
	private final Converter converter;

	public InputReader( Validator validator, Converter converter )
	{
		this.validator = validator;
		this.converter = converter;
	}

	public void readFile()
	{
		
	}
}