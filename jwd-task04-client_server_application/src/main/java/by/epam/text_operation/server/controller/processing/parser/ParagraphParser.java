package by.epam.text_operation.server.controller.processing.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;

public class ParagraphParser {

	private static ParagraphParser paragraphParser = new ParagraphParser();
	private final static String SENTENCE = "([^.!?]+[.!?])";

	private ParagraphParser() {
	}

	public static ParagraphParser getParagraphParser() {
		return paragraphParser;
	}

	public List<CompositePartText> parse(String paragraph) {
		List<CompositePartText> sentences = new ArrayList<CompositePartText>();

		Pattern patternSentence = Pattern.compile(SENTENCE);
		Matcher matcherSentence = patternSentence.matcher(paragraph);
		String sentence = null;
		while (matcherSentence.find()) {
			sentence = matcherSentence.group();
			SentenceParser sentenceParser = SentenceParser.getSentenceParser();
			CompositePartText sentenceText = sentenceParser.parse(sentence);
			sentences.add(sentenceText);
		}
		return sentences;
	}
}
