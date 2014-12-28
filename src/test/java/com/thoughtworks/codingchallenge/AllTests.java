package com.thoughtworks.codingchallenge;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.thoughtworks.codingchallenge.converter.ConverterTest;
import com.thoughtworks.codingchallenge.reader.InputReaderTest;
import com.thoughtworks.codingchallenge.validator.ValidatorTest;

@RunWith( Suite.class )
@SuiteClasses( { EntryPointTest.class, InputReaderTest.class, ConverterTest.class, ValidatorTest.class } )
public class AllTests
{

}