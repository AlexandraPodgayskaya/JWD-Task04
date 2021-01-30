package by.epam.text_operation.server.controller.processing.reader;

import java.io.File;
import java.util.List;

import by.epam.text_operation.exception.ServerException;

public interface CommonReader {

	public List<String> take(File file) throws ServerException;
}
