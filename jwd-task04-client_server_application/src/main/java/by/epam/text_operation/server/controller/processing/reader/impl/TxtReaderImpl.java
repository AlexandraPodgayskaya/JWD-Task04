package by.epam.text_operation.server.controller.processing.reader.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.epam.text_operation.exception.ServerException;
import by.epam.text_operation.server.controller.processing.reader.CommonReader;

public class TxtReaderImpl implements CommonReader {

	private final static int IS_EMPTY = 0;

	@Override
	public List<String> take(File file) throws ServerException {
		
		if (file == null) {
			throw new ServerException("file not found");
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			List<String> blocksText = new ArrayList<String>();
			StringBuilder blockTextBuilder = new StringBuilder();
			String lineText = null;

			while ((lineText = reader.readLine()) != null) {
				if (lineText.isEmpty()) {
					if (blockTextBuilder.length() != IS_EMPTY) {
						blocksText.add(blockTextBuilder.toString());
						blockTextBuilder = new StringBuilder();
					}
					continue;
				}
				blockTextBuilder.append(lineText + " ");
			}
			if (blockTextBuilder.length() != IS_EMPTY) {
				blocksText.add(blockTextBuilder.toString());
			}
			return blocksText;

		} catch (IOException e) {
			System.err.println(e.getMessage());
			throw new ServerException(e);
		}
	}

}
