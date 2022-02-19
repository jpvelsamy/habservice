package core;

import java.util.LinkedHashSet;



public class HabServiceException extends Exception {

	private static final long serialVersionUID = -7603165587984941682L;

	public HabServiceException(Exception e) {
		super(e);
	}

	public HabServiceException(String string) {
		super(string);
	}

	public HabServiceException(Throwable t) {
		super(t);
	}

	public HabServiceException(String message, Exception e) {
		super(message, e);
	}

	public <T> HabServiceException(String string, LinkedHashSet<T> objectSet) {

	}

	public static String buildStackTrace(HabServiceException e) {
		return buildStackTrace(e);		
	}

	public static String buildStackTrace(Throwable e) {
		StringBuilder stringBuilder = new StringBuilder();
		for (StackTraceElement stackTraceElement : e.getStackTrace()) {
			stackTraceElement.getClassName();
			String fileName = stackTraceElement.getFileName();
			String methodName = stackTraceElement.getMethodName();
			int lineNumber = stackTraceElement.getLineNumber();
			stringBuilder.append(fileName).append(".").append(methodName).append("-").append(lineNumber).append("\n");
		}
		return stringBuilder.toString();
	}
}
