package by.epam.text_operation.server.validation;

import by.epam.text_operation.common.entity.Request;

public class RequestValidator {

	public static boolean correctRequest(Object request) {
		if (request instanceof Request) {
			Request requestCorrectType = (Request) request;
			return (requestCorrectType.getOperation() != null && requestCorrectType.getFile() != null);
		} else {
			return false;
		}
	}
}
