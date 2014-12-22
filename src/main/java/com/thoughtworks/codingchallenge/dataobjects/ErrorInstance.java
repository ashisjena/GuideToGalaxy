package com.thoughtworks.codingchallenge.dataobjects;

import com.thoughtworks.codingchallenge.StringHelper;

public class ErrorInstance
{
	private Object[] errorParameters;
	private String message;

	public ErrorInstance( String message, Object... errorParameters )
	{
		this.message = message;
		this.errorParameters = errorParameters;
	}

	@Override
	public String toString()
	{
		return StringHelper.create( message, errorParameters );
	}
}