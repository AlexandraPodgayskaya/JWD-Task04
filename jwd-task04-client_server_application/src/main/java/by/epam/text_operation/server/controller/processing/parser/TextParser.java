package by.epam.text_operation.server.controller.processing.parser;

import java.util.List;

import by.epam.text_operation.server.entity.part_text.TypeComponent;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;
import by.epam.text_operation.server.entity.part_text.impl.SimplePartText;

public class TextParser {

	private static TextParser textParser = new TextParser();
	private final static String BLOCK_CODE = "\\s*Example:.+}\\s*";

	private TextParser() {
	}

	public static TextParser getTextParser() {
		return textParser;
	}

	public CompositePartText parse(List<String> blocksText) {
		CompositePartText wholeText = new CompositePartText(TypeComponent.TEXT);

		for (String block : blocksText) {
			if (block.matches(BLOCK_CODE)) {
				wholeText.addComponent(new SimplePartText(TypeComponent.BLOCK_CODE, block));
				continue;
			}
			ParagraphParser paragraphParser = ParagraphParser.getParagraphParser();
			List<CompositePartText> sentences = paragraphParser.parse(block);
			wholeText.addComponent(sentences);
		}
		return wholeText;
	}
}
