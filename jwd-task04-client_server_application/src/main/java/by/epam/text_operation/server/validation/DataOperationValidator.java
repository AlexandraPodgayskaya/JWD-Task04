package by.epam.text_operation.server.validation;

import java.util.List;

import by.epam.text_operation.server.entity.part_text.ComponentText;
import by.epam.text_operation.server.entity.part_text.TypeComponent;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;
import by.epam.text_operation.server.service.TextOperation;
import by.epam.text_operation.server.service.operation.DeleteOperation;

public class DataOperationValidator {
	private final static String WORDS_LIST = "([^.,?!();:=<>«»]+?,\\s*)+?[^.,?!();:=<>«»]+";
	private final static String TWO_SYMBOLS = "[^,]\\s*,\\s*[^,\\s]";
	private final static String WORD_LENGTH = "\\s*\\d\\s*";
	private final static String ONE_SYMBOL = "\\s*.\\s*";
	private final static String NUMBER_LENGTH_SUBSTRING = "\\s*\\d\\s*,\\s*\\d\\s*,.+";

	public static boolean isCorrect(CompositePartText text, String informationRequest, TextOperation numberOperation) {
		if (text == null) {
			return false;
		}
		List<ComponentText> componentsText = text.getComponent();
		DeleteOperation deleteOperation = new DeleteOperation();

		for (ComponentText component : componentsText) {
			if (component.getType().equals(TypeComponent.BLOCK_CODE)) {
				deleteOperation.deleteComponentText(text, TypeComponent.BLOCK_CODE);
			}
		}

		switch (numberOperation) {
		case TEN:
			return informationRequest.matches(WORDS_LIST);
		case ELEVEN:
			return informationRequest.matches(TWO_SYMBOLS);
		case TWELVE:
			return informationRequest.matches(WORD_LENGTH);
		case THIRTEEN:
			return informationRequest.matches(ONE_SYMBOL);
		case SIXTEEN:
			return informationRequest.matches(NUMBER_LENGTH_SUBSTRING);
		default:
			return true;
		}

	}

}
