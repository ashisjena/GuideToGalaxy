package com.thoughtworks.codingchallenge.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest
{
	private Validator validator;

	@Before
	public void setUp()
	{
		validator = new Validator();
	}

	@Test
	public void validateRomanValueTest()
	{
		Assert.assertEquals( true, validator.validateRomanValue( "MMMIIIXCMDIIIXCDCCCIIIXCLIIIXLXXXIIIXVIVIII" ) );
		Assert.assertEquals( true, validator.validateRomanValue( "IXCM" ) );
	}
	
	@Test
	public void validateRomanValueNonRomanCharacterTest()
	{
		Assert.assertEquals( false, validator.validateRomanValue( "MAX" ) );
	}
	
	@Test
	public void validateRomanValueWithWrongSubstractionTest()
	{
		Assert.assertEquals( false, validator.validateRomanValue( "MIMXD" ) );
		Assert.assertEquals( false, validator.validateRomanValue( "MCXCMMI" ) );
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