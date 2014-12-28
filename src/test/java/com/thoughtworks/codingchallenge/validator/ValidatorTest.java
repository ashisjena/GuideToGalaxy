package com.thoughtworks.codingchallenge.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest
{
	private Validator validator;
	private boolean setUpIsDone;

	@Before
	public void setUp()
	{
		if ( setUpIsDone )
			return;
		validator = new Validator();
		setUpIsDone = true;
	}

	@Test
	public void validateRomanValueTest()
	{
		Assert.assertEquals( true, validator.validateRomanValue( "MMMIIIXCMDIIIXCDCCCIIIXCLIIIXLXXXIIIXVIVIII" ) );
	}

	@Test
	public void validateRomanValueWrongRepeatationTest()
	{
		Assert.assertEquals( false, validator.validateRomanValue( "MMDDVI" ) );
		Assert.assertEquals( false, validator.validateRomanValue( "MMDLLVI" ) );
		Assert.assertEquals( false, validator.validateRomanValue( "MMDLVVI" ) );
	}

	@Test
	public void validateRomanValueWithMoreThan3RepeatingLetterTest()
	{
		Assert.assertEquals( false, validator.validateRomanValue( "MMCCCCI" ) );
	}

	@Test
	public void validateRomanValueWrongOrderOfRepeatationTest()
	{
		Assert.assertEquals( false, validator.validateRomanValue( "MXCMMI" ) );
	}

	@Test
	public void validateRomanValueWithNeverSubtractedTest()
	{
		Assert.assertEquals( false, validator.validateRomanValue( "MMVDI" ) );
	}

	@Test
	public void validateRomanValueAllowOneSmallValueCanBeSubtractedFromAnyLargeValueTest()
	{
		Assert.assertEquals( true, validator.validateRomanValue( "MMMXMXDXCXLXVI" ) );
	}
}