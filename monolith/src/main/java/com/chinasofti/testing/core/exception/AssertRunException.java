package com.chinasofti.testing.core.exception;

public class AssertRunException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2443153845946290233L;

	/*无参构造函数*/
    public AssertRunException(){
        super();
    }
    
    public AssertRunException( String type , String message )
    {
    	super("[" + type + "]:" + message);
    }
    //用详细信息指定一个异常
    public AssertRunException(String message){
        super(message);
    }
    
    //用指定的详细信息和原因构造一个新的异常
    public AssertRunException(String message, Throwable cause){
        super(message,cause);
    }
    
  //用指定的详细信息和原因构造一个新的异常
    public AssertRunException(String type , String message, Throwable cause){
        super( "[" + type + "]:" + message,cause);
    }
    
    //用指定原因构造一个新的异常
    public AssertRunException(Throwable cause) {
        super(cause);
    }
}
