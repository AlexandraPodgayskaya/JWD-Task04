package by.epam.text_operation.server.service.operation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import by.epam.text_operation.server.entity.part_text.ComponentText;
import by.epam.text_operation.server.entity.part_text.TypeComponent;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;

public class SortingComponent {
	private final static String VOWELS = "аеёиоуыэюяaeiouy";
	private final static String CONSONANTS = "бвгджзйклмнпрстфхцчшщbcdfghjklmnpqrstvxzw";

	public void sortAlphabetically(final List<String> words) {
		Collections.sort(words, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		});
	}

	public void sortNumberWordSentence(final List<CompositePartText> sentences) {
		Collections.sort(sentences, new Comparator<CompositePartText>() {
			@Override
			public int compare(CompositePartText sentence1, CompositePartText sentence2) {
				List<ComponentText> componentsText = sentence1.getComponent();
				int numberWords1 = 0;
				for (ComponentText componentText : componentsText) {
					if (componentText.getType().equals(TypeComponent.WORD)) {
						numberWords1++;
					}
				}
				componentsText = sentence2.getComponent();
				int numberWords2 = 0;
				for (ComponentText componentText : componentsText) {
					if (componentText.getType().equals(TypeComponent.WORD)) {
						numberWords2++;
					}
				}
				return numberWords1 - numberWords2;
			}
		});
	}

	public void sortProportionVowels(final List<String> words) {
		Collections.sort(words, new Comparator<String>() {
			@Override
			public int compare(String word1, String word2) {
				return Double.compare(getPercentChars(word1.toLowerCase()), getPercentChars(word2.toLowerCase()));
			}
		});
	}

	public final static double getPercentChars(String word) {
		double counter = 0;
		for (char symbol : word.toCharArray()) {
			for (char vowel : VOWELS.toCharArray()) {
				if (symbol == vowel) {
					counter++;
				}
			}
		}
		return 100 * (counter / word.length());
	}

	public void sortFirstConsonant(final List<String> words) {
		Collections.sort(words, new Comparator<String>() {
			@Override
			public int compare(String word1, String word2) {
				Character consonantWord1 = getFirstConsonant(word1);
				Character consonantWord2 = getFirstConsonant(word2);
				return consonantWord1.compareTo(consonantWord2);
			}
		});
	}

	public final static Character getFirstConsonant(String word) {
		for (int i = 0; i < word.length(); ++i) {
			if (isConsonant(word.charAt(i))) {
				return word.charAt(i);
			}
		}
		return Character.MIN_VALUE;
	}

	public final static boolean isConsonant(Character symbol) {
		return CONSONANTS.contains(symbol.toString());

	}

	public void sortNumberLetterIncrease(final List<String> words, final String letter) {
		Collections.sort(words, new Comparator<String>() {
			@Override
			public int compare(String word1, String word2) {
				int result = counter(word1.toLowerCase(), letter) - counter(word2.toLowerCase(), letter);
				if (result == 0) {
					return word1.compareToIgnoreCase(word2);
				}
				return result;
			}
		});
	}
	
	public final static int counter(String word, String letter) {
		int numberLetters = 0;
		for (int i = 0; i < word.length(); i++) {
			if (letter.charAt(0) == word.charAt(i)) {
				numberLetters++;
			}
		}
		return numberLetters;
	}

	public void sortNumberLetterDecrease(final List<String> words, final String letter) {
		Collections.sort(words, new Comparator<String>() {
			@Override
			public int compare(String word1, String word2) {
				int result = counter(word2.toLowerCase(), letter) - counter(word1.toLowerCase(), letter);
				if (result == 0) {
					return word1.compareToIgnoreCase(word2);
				}
				return result;
			}
		});
	}

	public void sortNumberWordRepeat(final List<String> wordsList, final Map<String, Integer> numberWordRepeat) {
		Collections.sort(wordsList, new Comparator<String>() {
			@Override
			public int compare(String words1, String words2) {
				return numberWordRepeat.get(words2) - numberWordRepeat.get(words1);
			}
		});
	}

}