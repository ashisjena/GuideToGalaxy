package com.thoughtworks.codingchallenge;

public class Constants
{
	public final static String REGEX_INTERGALACTIC_UNITS_IN_ROMAN = "[A-Za-z]+ is [IVXLCDM]+";
	public final static String REGEX_INTERGALACTIC_UNITS_IN_ARABIAN_CREDITS = "[[A-Za-z]+\\s+]+is\\s+\\d+\\s+Credits";
	public final static String REGEX_QUESTION_FOR_DECIMAL_PRICE = "how much is [[A-Za-z]+\\s+]+" + "\\?$";
	public final static String REGEX_QUESTION_FOR_ARABIAN_CREDITS = "how many Credits is [[A-Za-z]+\\s+]+" + "\\?$";
	public final static int MAX_NO_OF_REPEATATION = 3;
	public final static String REGEX_ROMAN_NUMBER = "[IVXLCDM]+";
	public final static String REGEX_ROMAN_NUMBER_REPEATING_MORE_THAN_3 = "[IVXLCDM]*((I{4,})|(X{4,})|(C{4,})|(M{4,}))[IVXLCDM]*";
	public final static String INPUT_FILE = "InputFile";
	public final static String QUESTION_LITERAL = "?";
}