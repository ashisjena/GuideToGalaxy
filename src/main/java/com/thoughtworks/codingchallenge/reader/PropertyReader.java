package com.thoughtworks.codingchallenge.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.thoughtworks.codingchallenge.Constants;
import com.thoughtworks.codingchallenge.exception.InstantiationException;
import com.thoughtworks.codingchallenge.exception.ReaderException;

public class PropertyReader
{
	private static class Holder
	{
		static PropertyReader INSTANCE = new PropertyReader();
	}

	public static PropertyReader getInstance()
	{
		return Holder.INSTANCE;
	}

	private PropertyReader()
	{
		if ( Holder.INSTANCE != null )
			throw new InstantiationException( "Already has been Initialized" );
	}

	public Map<String, Integer> readConversionRatePropertyFile() throws ReaderException
	{
		return readConversionRatePropertyFile( Constants.CONVERSION_RATES_FILENAME );
	}

	public Map<String, Integer> readConversionRatePropertyFile( String propFileName ) throws ReaderException
	{
		final Map<String, Integer> keyValueMap = new HashMap<String, Integer>();
		final InputStream inputStream = getClass().getClassLoader().getResourceAsStream( propFileName );
		final Properties prop = new Properties();
		if ( inputStream != null )
			try
			{
				prop.load( inputStream );
			}
			catch ( IOException e )
			{
				throw new ReaderException( e );
			}
		else
			throw new ReaderException( "property file '" + propFileName + "' not found in the classpath" );

		for ( Entry<Object, Object> entry : prop.entrySet() )
			keyValueMap.put( entry.getKey().toString(), Integer.parseInt( entry.getValue().toString() ) );

		try
		{
			inputStream.close();
		}
		catch ( IOException e )
		{
			throw new ReaderException( e );
		}

		return Collections.unmodifiableMap( keyValueMap );
	}
}