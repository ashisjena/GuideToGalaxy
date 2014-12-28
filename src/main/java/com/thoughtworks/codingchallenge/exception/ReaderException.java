package com.thoughtworks.codingchallenge.exception;

public class ReaderException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ReaderException()
	{
		super();
	}

	public ReaderException( String s )
	{
		super( s );
	}

	public ReaderException( Throwable e )
	{
		super( e );
	}
}