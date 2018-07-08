package com.demo.exception;

public class CustomError {

	String errDesc;

	/**
	 * Custom errors
	 * 
	 * @param errCode
	 * @param errDesc
	 */
	public CustomError(String errDesc) {

		this.errDesc = errDesc;
	}

	public CustomError() {
		super();
	}

	@Override
	public String toString() {
		return "CustomError [errDesc=" + errDesc + "]";
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}
	
	

}
