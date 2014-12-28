package com.thoughtworks.codingchallenge.exception;

public class ConverterException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ConverterException()
	{

	}

	public ConverterException( String msg )
	{
		super( msg );
	}

	public ConverterException( Throwable e )
	{
		super( e );
	}
}
