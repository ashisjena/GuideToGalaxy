package com.thoughtworks.codingchallenge.common;

import java.util.Comparator;
import java.util.Map;

@SuppressWarnings( "rawtypes" )
public class ValueComparator<T> implements Comparator<T>
{
	private final Map<T, ? extends Comparable> map;

	public ValueComparator( Map<T, ? extends Comparable> map )
	{
		this.map = map;
	}

	@SuppressWarnings( { "unchecked" } )
	@Override
	public int compare( T o1, T o2 )
	{
		return map.get( o1 ).compareTo( map.get( o2 ) );
	}
}