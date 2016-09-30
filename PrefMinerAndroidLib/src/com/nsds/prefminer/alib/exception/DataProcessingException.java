package com.nsds.prefminer.alib.exception;

public class DataProcessingException extends Exception{

	private static final long serialVersionUID = 1234;

	public DataProcessingException()
	{
		super("Data has not processed! "
				+ "Please process it by calling \"processData\" method of \"Data\" class.");
	}
}
