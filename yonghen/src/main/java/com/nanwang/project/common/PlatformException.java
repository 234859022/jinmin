package com.nanwang.project.common;

public class PlatformException extends Exception{
	
    private static final long serialVersionUID = -227961057827878851L;

    private int exceptionCode;

    public int getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public PlatformException() {
    }

    public PlatformException(String arg0) {
        super(arg0);
    }
    
    public PlatformException(String arg0, int exceptionCode) {
        super(arg0);
        this.exceptionCode = exceptionCode;
    }

    public PlatformException(Throwable arg0) {
        super(arg0);
    }

    public PlatformException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public PlatformException(String arg0, Throwable arg1, int exceptionCode) {
        super(arg0, arg1);
        this.exceptionCode = exceptionCode;
    }
}
