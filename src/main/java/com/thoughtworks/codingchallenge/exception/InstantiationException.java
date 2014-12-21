package com.thoughtworks.codingchallenge.exception;

public class InstantiationException extends RuntimeException
{
	private static final long serialVersionUID = -8441929162975509111L;

	public InstantiationException()
	{
		super();
	}

	public InstantiationException( String s )
	{
		super( s );
	}
}