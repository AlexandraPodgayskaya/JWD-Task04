package by.epam.text_operation.exception;

public class ClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClientException() {
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(String message, Exception exception) {
		super(message, exception);
	}

	public ClientException(Exception exception) {
		super(exception);
	}

}
