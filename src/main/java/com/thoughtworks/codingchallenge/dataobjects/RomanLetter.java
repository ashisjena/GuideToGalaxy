package com.thoughtworks.codingchallenge.dataobjects;

import java.util.Arrays;
import java.util.List;

public enum RomanLetter
{
	M( "M", 1000, 7, true, false, null ), 
	D( "D", 500, 6, false, false, null ), 
	C( "C", 100, 5, true, true, Arrays.asList( new RomanLetter[]{D, M} ) ), 
	L( "L", 50, 4, false, false, null ), 
	X( "X", 10, 3, true, true, Arrays.asList( new RomanLetter[]{L, C} ) ), 
	V( "V", 5, 2, false, false, null ), 
	I( "I", 1, 1, true, true, Arrays.asList( new RomanLetter[]{V, X} ) ), 
	DEMO( "", Long.MIN_VALUE, Integer.MAX_VALUE, false, false, null );

	private String romanValue;
	private long arabicValue;
	private boolean isRepeatable;
	private boolean isSubtractable;
	private List<RomanLetter> validHigherValues;
	private int order;

	RomanLetter( String romanValue, long arabicValue, int order, boolean isRepeatable, boolean isSubtractable, List<RomanLetter>  validHigherValues)
	{
		this.romanValue = romanValue;
		this.arabicValue = arabicValue;
		this.isRepeatable = isRepeatable;
		this.isSubtractable = isSubtractable;
		this.validHigherValues = validHigherValues;
		this.order = order;
	}

	public static RomanLetter getRomanValueFromString( String str )
	{
		switch( str )
		{
		case "I":
			return I;
		case "V":
			return V;
		case "X":
			return X;
		case "L":
			return L;
		case "C":
			return C;
		case "D":
			return D;
		case "M":
			return M;
		default:
			return DEMO;
		}
	}
	
	public int getOrder()
	{
		return order;
	}

	public String getRomanValue()
	{
		return romanValue;
	}

	public long getArabicValue()
	{
		return arabicValue;
	}

	public boolean isRepeatable()
	{
		return isRepeatable;
	}

	public boolean isSubtractable()
	{
		return isSubtractable;
	}
	
	public boolean isValidHigherValue( RomanLetter value )
	{
		if ( !isSubtractable )
			throw new RuntimeException( "Is not Subtractable" );

		if ( validHigherValues.contains( value ) )
			return true;
		else
			return false;
	}
}