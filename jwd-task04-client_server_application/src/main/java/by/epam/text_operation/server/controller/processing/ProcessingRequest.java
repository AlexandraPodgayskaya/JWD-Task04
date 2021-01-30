package by.epam.text_operation.server.controller.processing;

import java.util.List;

import by.epam.text_operation.common.entity.Request;
import by.epam.text_operation.common.entity.Respons;
import by.epam.text_operation.exception.ServerException;
import by.epam.text_operation.server.controller.processing.parser.TextParser;
import by.epam.text_operation.server.controller.processing.reader.CommonReader;
import by.epam.text_operation.server.controller.processing.reader.impl.TxtReaderImpl;
import by.epam.text_operation.server.entity.part_text.TypeComponent;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;
import by.epam.text_operation.server.service.operation.DeleteOperation;

public class ProcessingRequest {

	public Respons process(Request request) throws ServerException {
		CommonReader reader = new TxtReaderImpl();
		List<String> blocksText = reader.take(request.getFile());

		TextParser textParser = TextParser.getTextParser();
		CompositePartText wholeText = textParser.parse(blocksText);

		DeleteOperation deleteOperation = new DeleteOperation();
		deleteOperation.deleteComponentText(wholeText, TypeComponent.BLOCK_CODE);

		String result = request.getOperation().doOperation(wholeText, request.getOtherInformation());
		Respons respons = new Respons();
		respons.setContent(result);

		return respons;
	}
}
