package com.thoughtworks.codingchallenge.common;

public class StringHelper
{
	public static String create( String message, Object[] args )
	{
		if ( ( args == null ) || ( args.length == 0 ) )
			return message;

		message = parse( message, args );
		return message;
	}

	private static String parse( String baseMessage, Object[] args )
	{
		for ( int i = 0; i < args.length; ++i )
		{
			if ( args[i] == null )
				baseMessage = baseMessage.replaceAll( "%" + ( i + 1 ), "NULL" );
			else
				baseMessage = baseMessage.replaceAll( "%" + ( i + 1 ), args[i].toString() );
		}
		return baseMessage;
	}

	public static String getValuePart( String inputLine )
	{
		return inputLine.substring( inputLine.lastIndexOf( " is " ) + 4 ).replaceAll( " (?i)Credits", "" );
	}
}