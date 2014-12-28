package com.thoughtworks.codingchallenge.common;

public class Constants
{
	public final static String REGEX__INTERGALACTIC_UNITS_IN_ROMAN = "[A-Za-z]+ (?i)is [IVXLCDM]+";
	public final static String REGEX__NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS = "[[A-Za-z]+ ]+(?i)is \\d+ (?i)Credits";
	public final static String REGEX_QUESTION__INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS = "(?i)how (?i)much (?i)is [[A-Za-z]+\\s+]+" + "\\?$";
	public final static String REGEX_QUESTION__NO_OF_COMODITY_PRICE_IN_ARABIAN_CREDITS = "(?i)how (?i)many (?i)Credits (?i)is [[A-Za-z]+\\s+]+" + "\\?$";
	public final static String REGEX_QUESTION_PART_INTERGALACTIC_UNITS_TO_ARABIAN_CREDITS = "((?i)how (?i)much (?i)is )|([\\s][\\?])";
	public final static String REGEX_QUESTION_PART_FOR_COMODITY_PRICE_IN_ARABIAN_CREDITS = "((?i)how (?i)many (?i)Credits (?i)is )|([\\s][\\?])";
	public final static String REGEX_ROMAN_NUMBER = "[IVXLCDM]+";

	public final static String QUESTION_LITERAL = "?";

	public final static String INPUT_FILE = "InputFile";

	public final static String EMPTY_STRING = "";

	public final static String MSG_INTERGALACTIC_UNIT_NOT_FOUND = "Input for Intergalactic unit is not found in the inputs";
	public final static String MSG_WRONG_INTERGALACTIC_UNIT_ORDER = "Wrong order of intergalactic unit is asked";
	public final static String MSG_COMODITY_NOT_FOUND = "Comodity not found in the inputs";
	public final static String MSG_GRAMMAR_ERROR = "Grammar Error in the input";
	public final static String MSG_WRONG_QUESTION = "I have no idea what you are talking about";
}