package by.epam.text_operation.server.controller.processing.parser;

import java.util.ArrayList;
import java.util.List;

import by.epam.text_operation.server.entity.part_text.TypeComponent;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;
import by.epam.text_operation.server.entity.part_text.impl.SimplePartText;

public class SentenceParser {

	private static SentenceParser sentenceParser = new SentenceParser();
	private final static String PARTS_SENTENCE = "\\b";
	private final static String SPACE = "\\s+";
	private final static String PUNCTUATION_MARK = "[.,?!();:=<>«»]+\\s*";

	private SentenceParser() {
	}

	public static SentenceParser getSentenceParser() {
		return sentenceParser;
	}

	public CompositePartText parse(String sentence) {
		CompositePartText sentenceText = new CompositePartText(TypeComponent.SENTENCE);

		List<SimplePartText> componentsSentence = new ArrayList<SimplePartText>();
		String[] partsSentence = sentence.split(PARTS_SENTENCE);
		for (String part : partsSentence) {
			if (part.matches(SPACE)) {
				componentsSentence.add(new SimplePartText(TypeComponent.SPACE, part));
			} else if (part.matches(PUNCTUATION_MARK)) {
				componentsSentence.add(new SimplePartText(TypeComponent.PUNCTUATION_MARK, part));
			} else {
				componentsSentence.add(new SimplePartText(TypeComponent.WORD, part));
			}
		}
		sentenceText.addComponent(componentsSentence);
		return sentenceText;
	}
}
