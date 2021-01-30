package by.epam.text_operation.server.service.operation;

import java.util.Iterator;
import java.util.List;

import by.epam.text_operation.server.entity.part_text.ComponentText;
import by.epam.text_operation.server.entity.part_text.TypeComponent;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;

public class DeleteOperation {
	private final static String CONSONANTS = "бвгджзйклмнпрстфхцчшщbcdfghjklmnpqrstvxzw";
	private final static String VOWELS = "аеёиоуыэюяaeiouy";

	public void deleteComponentText(CompositePartText partsText, TypeComponent typeComponent) {
		List<ComponentText> componentsText = partsText.getComponent();
		Iterator<ComponentText> iterator = componentsText.iterator();
		while (iterator.hasNext()) {
			ComponentText component = iterator.next();
			if (component.getType().equals(typeComponent)) {
				iterator.remove();
			}
		}
	}

	public void deleteSpaceAndPunctuationMark(CompositePartText text) {
		List<ComponentText> partsText = text.getComponent();
		for (ComponentText part : partsText) {
			if (part.getType().equals(TypeComponent.SENTENCE)) {
				CompositePartText sentence = (CompositePartText) part;
				deleteComponentText(sentence, TypeComponent.PUNCTUATION_MARK);
				deleteComponentText(sentence, TypeComponent.SPACE);
			}
		}
	}

	public void deleteWordFirstConsonant(List<String> words) {
		Iterator<String> iterator = words.iterator();
		String word;
		int numberFirstLetter = 0;
		while (iterator.hasNext()) {
			word = iterator.next();
			for (char consonant : CONSONANTS.toCharArray()) {
				if (word.toLowerCase().toCharArray()[numberFirstLetter] == consonant) {
					iterator.remove();
				}
			}
		}
	}
	
	public void deleteWordFirstVowel (List<String> words) {
		Iterator<String> iterator = words.iterator();
		String word;
		int numberFirstLetter = 0;
		while (iterator.hasNext()) {
			word = iterator.next();
			for (char vowel : VOWELS.toCharArray()) {
				if (word.toLowerCase().toCharArray()[numberFirstLetter] == vowel) {
					iterator.remove();
				}
			}
		}	
	}

	public String deleteLetterAsFirstAndPast(String wordText) {
		String word = wordText.toLowerCase();
		String result = word;
		StringBuilder builderWords;
		char[] symbolsWord;
		char firstSymbol;
		char pastSymbol;
		int indexPastSymbol;
		if (word.length() > 2) {
			builderWords = new StringBuilder();
			symbolsWord = word.toCharArray();
		    firstSymbol = symbolsWord[0];
			builderWords.append(firstSymbol);
			
			indexPastSymbol = symbolsWord.length - 1;
			pastSymbol = symbolsWord[indexPastSymbol];
			for (int i = 1; i < indexPastSymbol; i++) {
				if (symbolsWord[i] != firstSymbol && symbolsWord[i] != pastSymbol) {
					builderWords.append(symbolsWord[i]);
				}
			}
			builderWords.append(pastSymbol);
			result = builderWords.toString();
		}
		return result;
	}
}
