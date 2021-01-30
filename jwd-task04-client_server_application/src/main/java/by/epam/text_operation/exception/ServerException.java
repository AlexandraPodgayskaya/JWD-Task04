package by.epam.text_operation.exception;

public class ServerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServerException() {
		
	}
	
	public ServerException (String message) {
		super(message);
	}
	
	public ServerException (String message, Exception exception) {
		super(message, exception);
	}
	
	public ServerException(Exception exception) {
		super(exception);
	}

}
