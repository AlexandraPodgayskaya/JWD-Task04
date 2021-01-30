package by.epam.text_operation.server.service.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.epam.text_operation.server.entity.part_text.ComponentText;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;

public class TakeInformation {

	public List<String> takeWordSentenceWithoutRepeat(CompositePartText text) {
		List<String> wordsSentenceWithoutRepeat = new ArrayList<String>();
		List<ComponentText> sentencesText = text.getComponent();

		Set<String> wordsWithoutRepeat;
		List<String> wordsSentence;
		for (ComponentText sentence : sentencesText) {
			wordsWithoutRepeat = new LinkedHashSet<String>();
			wordsSentence = sentence.getContent();
			
			for (String word : wordsSentence) {
				wordsWithoutRepeat.add(word.toLowerCase());
			}
			wordsSentenceWithoutRepeat.addAll(wordsWithoutRepeat);
		}
		return wordsSentenceWithoutRepeat;
	}

	public Map<String, Integer> takeNumberWordRepeat(List<String> words) {
		Map<String, Integer> numberWordRepeat = new HashMap<String, Integer>();
		int numberRepeat;
		for (String word : words) {
			numberRepeat = 0;
			for (String wordSentence : words) {
				if (word.equalsIgnoreCase(wordSentence)) {
					numberRepeat++;
				}
			}
			numberWordRepeat.put(word, numberRepeat);
		}
		return numberWordRepeat;
	}

	public List<CompositePartText> takeListCompositeComponent(CompositePartText text) {
		List<ComponentText> componentText = text.getComponent();
		List<CompositePartText> sentences = new ArrayList<CompositePartText>();
		CompositePartText sentence;
		for (ComponentText component : componentText) {
			if (component instanceof CompositePartText) {
				sentence = (CompositePartText) component;
				sentences.add(sentence);
			}
		}
		return sentences;
	}

	public Set<String> takeAllPalindromes(String text) {
		Set<String> palindromes = new HashSet<>();
		int startForEven;
		int startForOdd;
		int start;
		for (int i = 0; i < text.length(); i++) {
			start = i;
			startForEven = i + 1;
			startForOdd = i;
			palindromes.addAll(findPalindromes(text, start, startForEven));
			palindromes.addAll(findPalindromes(text, start, startForOdd));
		}
		return palindromes;
	}

	public final static Set<String> findPalindromes(String text, int left, int right) {
		Set<String> result = new HashSet<>();
		while (left >= 0 && right < text.length() && text.charAt(left) == text.charAt(right)) {
			result.add(text.substring(left, right + 1));
			left--;
			right++;
		}
		return result;
	}
}
