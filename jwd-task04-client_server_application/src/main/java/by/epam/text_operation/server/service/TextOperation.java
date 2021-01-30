package by.epam.text_operation.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.epam.text_operation.exception.ServerException;
import by.epam.text_operation.server.entity.part_text.ComponentText;
import by.epam.text_operation.server.entity.part_text.TypeComponent;
import by.epam.text_operation.server.entity.part_text.impl.CompositePartText;
import by.epam.text_operation.server.entity.part_text.impl.SimplePartText;
import by.epam.text_operation.server.service.operation.DeleteOperation;
import by.epam.text_operation.server.service.operation.SortingComponent;
import by.epam.text_operation.server.service.operation.TakeInformation;
import by.epam.text_operation.server.validation.DataOperationValidator;

public enum TextOperation {
	ONE("Найти наибольшее количество предложений текста, в которых есть одинаковые слова") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			TakeInformation takeInformation = new TakeInformation();
			List<String> wordsSentenceWithoutRepeat = takeInformation.takeWordSentenceWithoutRepeat(text);
			Map<String, Integer> numberWordRepeat = takeInformation.takeNumberWordRepeat(wordsSentenceWithoutRepeat);
			int maxNumberSentence = Integer.MIN_VALUE;

			for (int numberRepeat : numberWordRepeat.values()) {
				if (numberRepeat > maxNumberSentence) {
					maxNumberSentence = numberRepeat;
				}
			}
			return String.valueOf(maxNumberSentence);
		}
	},
	TWO("Вывести все предложения заданного текста в порядке возрастания количества слов в каждом из них") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			TakeInformation takeInformation = new TakeInformation();
			List<CompositePartText> sentences = takeInformation.takeListCompositeComponent(text);
			SortingComponent sortingComponent = new SortingComponent();
			sortingComponent.sortNumberWordSentence(sentences);

			StringBuilder result = new StringBuilder();

			for (ComponentText sentence : sentences) {
				sentence.show(result);
			}
			return result.toString();
		}
	},
	THREE("Найти такое слово в первом предложении, которого нет ни в одном из остальных предложений") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			TakeInformation takeInformation = new TakeInformation();

			List<ComponentText> componentsText = text.getComponent();
			final int correctNumberSentence = 2;
			final int numberFirstSentence = 0;
			if (componentsText.size() < correctNumberSentence) {
				throw new ServerException("incorrect content of the request");
			}
			ComponentText firstComponent = componentsText.get(numberFirstSentence);
			CompositePartText firstSentence = null;
			if (firstComponent instanceof CompositePartText) {
				firstSentence = (CompositePartText) firstComponent;
			}
			componentsText.remove(numberFirstSentence);

			List<String> wordsFirstSentenceWithoutRepeat = takeInformation.takeWordSentenceWithoutRepeat(firstSentence);
			List<String> wordsOtherSentencesWithoutRepeat = takeInformation.takeWordSentenceWithoutRepeat(text);

			String result = "все слова первого предложения присутствуют хотя бы в одном из остальных предложениях";
			for (String wordFirstSentence : wordsFirstSentenceWithoutRepeat) {
				if (!wordsOtherSentencesWithoutRepeat.contains(wordFirstSentence)) {
					result = wordFirstSentence;
					break;
				}
			}
			return result;
		}
	},
	FOUR("Во всех вопросительных предложениях текста найти и напечатать без повторений слова заданной длины") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			List<ComponentText> componentsText = text.getComponent();
			Iterator<ComponentText> iterator = componentsText.iterator();
			ComponentText component;
			List<String> sentence;
			String lastMark;

			while (iterator.hasNext()) {
				component = iterator.next();
				sentence = component.getContent();
				lastMark = sentence.get(sentence.size() - 1).trim();
				if (!lastMark.equals(QUESTION_MARK)) {
					iterator.remove();
				}
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			TakeInformation takeInformation = new TakeInformation();
			List<String> wordsSentenceWithoutRepeat = takeInformation.takeWordSentenceWithoutRepeat(text);

			Set<String> wordsAllSentencesWithoutRepeat = new LinkedHashSet<String>();
			for (String word : wordsSentenceWithoutRepeat) {
				if (word.length() == Integer.parseInt(informationRequest)) {
					wordsAllSentencesWithoutRepeat.add(word);
				}
			}
			String result = String.join(DELIMITER_WORDS, wordsAllSentencesWithoutRepeat);
			return result;
		}
	},
	FIVE("В каждом предложении текста поменять местами первое слово с последним, не изменяя длины предложения") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			TakeInformation takeInformation = new TakeInformation();
			List<CompositePartText> sentences = takeInformation.takeListCompositeComponent(text);
			List<ComponentText> partsSentence;
			int lengthSentence;
			int lengthSentenceWithOneWord = 2;
			ComponentText firstWord;
			ComponentText firstWordSentence;

			for (CompositePartText sentence : sentences) {
				partsSentence = sentence.getComponent();
				lengthSentence = partsSentence.size();
				if (lengthSentence == lengthSentenceWithOneWord) {
					break;
				}
				firstWord = null;
				int counterIndexFirstWord = 0;
				while (firstWord == null && counterIndexFirstWord < lengthSentence) {
					firstWordSentence = partsSentence.get(counterIndexFirstWord);
					if (firstWordSentence.getType().equals(TypeComponent.WORD)) {
						firstWord = firstWordSentence;
					} else {
						counterIndexFirstWord++;
					}
				}
				ComponentText lastWord = null;
				ComponentText lastMarkSentence;
				int counterIndexLastWord = 1;

				while (lastWord == null && counterIndexLastWord < lengthSentence) {
					lastMarkSentence = partsSentence.get(lengthSentence - counterIndexLastWord);
					if (lastMarkSentence.getType().equals(TypeComponent.WORD)) {
						lastWord = partsSentence.get(lengthSentence - counterIndexLastWord);
					} else {
						counterIndexLastWord++;
					}
				}
				partsSentence.set(counterIndexFirstWord, lastWord);
				partsSentence.set(lengthSentence - counterIndexLastWord, firstWord);
			}
			StringBuilder result = new StringBuilder();
			text.show(result);
			return result.toString();
		}
	},
	SIX("Напечатать слова текста в алфавитном порядке по первой букве. Слова, начинающиеся с новой буквы, печатать с красной строки") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			List<String> allWordsText = text.getContent();
			SortingComponent sortingComponent = new SortingComponent();
			sortingComponent.sortAlphabetically(allWordsText);

			StringBuilder result = new StringBuilder();
			String firstWord = allWordsText.get(0);
			String firstLetter = String.valueOf(firstWord.charAt(0));
			for (String word : allWordsText) {
				if (!word.startsWith(firstLetter.toLowerCase())) {
					result.append(LINE_BREAK);
				}
				result.append(word + DELIMITER_WORDS);
				firstLetter = String.valueOf(word.charAt(0));
			}
			return result.toString();
		}
	},
	SEVEN("Рассортировать слова текста по возрастанию доли гласных букв (отношение количества гласных к общему количеству букв в слове)") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			List<String> allWordsText = text.getContent();
			SortingComponent sortingcomponent = new SortingComponent();
			sortingcomponent.sortProportionVowels(allWordsText);

			String result = String.join(DELIMITER_WORDS, allWordsText);
			return result;
		}
	},
	EIGHT("Слова текста, начинающиеся с гласных букв, рассортировать в алфавитном порядке по первой согласной букве слова") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			List<String> allWordsText = text.getContent();
			deleteOperation.deleteWordFirstConsonant(allWordsText);

			SortingComponent sortingComponent = new SortingComponent();
			sortingComponent.sortFirstConsonant(allWordsText);

			String result = String.join(DELIMITER_WORDS, allWordsText);
			return result;
		}
	},
	NINE("Все слова текста рассортировать по возрастанию количества заданной буквы в слове. Слова с одинаковым количеством букв расположить в алфавитном порядке") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			List<String> allWordsText = text.getContent();
			SortingComponent sortingComponent = new SortingComponent();
			sortingComponent.sortNumberLetterIncrease(allWordsText, informationRequest);

			String result = String.join(DELIMITER_WORDS, allWordsText);
			return result;
		}
	},
	TEN("Существует текст и список слов. Для каждого слова из заданного списка найти, сколько раз оно встречается в каждом предложении, и рассортировать слова по убыванию общего количества вхождений") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			String[] words = informationRequest.split(INFORMATION_REQUEST_SPLIT);
			List<String> wordsList = new ArrayList<String>();
			for (String word : words) {
				wordsList.add(word.trim().toLowerCase());
			}

			List<String> allWordsText = text.getContent();
			TakeInformation takeInformation = new TakeInformation();
			Map<String, Integer> numberWordRepeat = takeInformation.takeNumberWordRepeat(allWordsText);

			SortingComponent sortingComponent = new SortingComponent();
			sortingComponent.sortNumberWordRepeat(wordsList, numberWordRepeat);

			StringBuilder result = new StringBuilder();
			for (String word : wordsList) {
				result.append(word + SPACE + numberWordRepeat.get(word) + LINE_BREAK);
			}
			return result.toString();
		}
	},

	ELEVEN("В каждом предложении текста исключить подстроку максимальной длины, начинающуюся и заканчивающуюся заданными символами") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			List<ComponentText> sentences = text.getComponent();

			String[] symbols = informationRequest.split(INFORMATION_REQUEST_SPLIT);
			String firstSymbol = symbols[0].trim();
			String secondSymbol = symbols[1].trim();
			String sentence;
			int indexFirstSymbol;
			int indexSecondSymbol;
			int startSentence = 0;
			int nextSymbolAfterSecond;
			StringBuilder result = new StringBuilder();
			for (ComponentText sentenceText : sentences) {
				sentence = String.join(WITHOUT_DIVISION, sentenceText.getContent());
				if (sentence.contains(firstSymbol) && sentence.contains(secondSymbol)) {
					indexFirstSymbol = sentence.indexOf(firstSymbol);
					indexSecondSymbol = sentence.lastIndexOf(secondSymbol);
					if (indexSecondSymbol > indexFirstSymbol) {
						result.append(sentence.substring(startSentence, indexFirstSymbol));
						nextSymbolAfterSecond = indexSecondSymbol + 1;
						result.append(sentence.substring(nextSymbolAfterSecond, sentence.length()));
						continue;
					}
				}
				result.append(sentence);
			}
			return result.toString();
		}
	},
	TWELVE("Из текста удалить все слова заданной длины, начинающиеся на согласную букву") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			TakeInformation takeInformation = new TakeInformation();
			List<String> wordsTextWithoutRepeat = takeInformation.takeWordSentenceWithoutRepeat(text);
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteWordFirstVowel(wordsTextWithoutRepeat);
			Iterator<String> iterator = wordsTextWithoutRepeat.iterator();
			String word;
			while (iterator.hasNext()) {
				word = iterator.next();
				if (word.length() != Integer.parseInt(informationRequest.trim())) {
					iterator.remove();
				}
			}
			List<String> wholeText = text.getContent();
			iterator = wholeText.iterator();
			while (iterator.hasNext()) {
				word = iterator.next();
				for (String wordGivenLength : wordsTextWithoutRepeat) {
					if (word.equalsIgnoreCase(wordGivenLength)) {
						iterator.remove();
						break;
					}
				}
			}
			String result = String.join(WITHOUT_DIVISION, wholeText);
			return result;
		}
	},
	THIRTEEN(
			"Отсортировать слова в тексте по убыванию количества вхождений заданного символа, а в случае равенства – по алфавиту") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			DeleteOperation deleteOperation = new DeleteOperation();
			deleteOperation.deleteSpaceAndPunctuationMark(text);

			List<String> allWordsText = text.getContent();
			SortingComponent sortingComponent = new SortingComponent();
			sortingComponent.sortNumberLetterDecrease(allWordsText, informationRequest.trim());

			String result = String.join(DELIMITER_WORDS, allWordsText);
			return result.toString();
		}
	},
	FOURTEEN(
			"В заданном тексте найти подстроку максимальной длины, являющуюся палиндромом, т.е. читающуюся слева направо и справа налево одинаково") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			List<String> allText = text.getContent();
			String allWordsText = String.join(WITHOUT_DIVISION, allText);
			TakeInformation takeInformation = new TakeInformation();
			Set<String> allPalindromes = takeInformation.takeAllPalindromes(allWordsText);

			String maximumPalindrom = Collections.max(allPalindromes, new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					return s1.length() - s2.length();
				}
			});
			return maximumPalindrom;
		}
	},
	FIFTEEN("Преобразовать каждое слово в тексте, удалив из него все последующие (предыдущие) вхождения первой (последней) буквы этого слова") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			TakeInformation takeInformation = new TakeInformation();
			List<CompositePartText> sentences = takeInformation.takeListCompositeComponent(text);

			StringBuilder result = new StringBuilder();
			DeleteOperation deleteOperation = new DeleteOperation();
			List<ComponentText> partsSentence;
			SimplePartText word;
			String wordWithDeleteLetter;
			ComponentText newWord;

			for (CompositePartText sentence : sentences) {
				partsSentence = sentence.getComponent();
				for (int i = 0; i < partsSentence.size(); i++) {
					word = (SimplePartText) partsSentence.get(i);
					if (word.getType().equals(TypeComponent.WORD)) {
						wordWithDeleteLetter = deleteOperation.deleteLetterAsFirstAndPast(word.getValue());
						newWord = new SimplePartText(TypeComponent.WORD, wordWithDeleteLetter);
						partsSentence.set(i, newWord);
					}
				}
				sentence.show(result);
			}
			return result.toString();
		}
	},

	SIXTEEN("В некотором предложении текста слова заданной длины заменить указанной подстрокой, длина которой может не совпадать с длиной слова") {
		public String doOperation(CompositePartText text, String informationRequest) throws ServerException {
			if (!DataOperationValidator.isCorrect(text, informationRequest, this)) {
				throw new ServerException("incorrect content of the request");
			}
			String[] information = informationRequest.split(INFORMATION_REQUEST_SPLIT);
			int numberSentence = Integer.parseInt(information[0].trim()) - 1;
			int wordLength = Integer.parseInt(information[1].trim());
			String substring = information[2].trim();

			TakeInformation takeInformation = new TakeInformation();
			List<CompositePartText> sentences = takeInformation.takeListCompositeComponent(text);
			if (numberSentence > sentences.size()) {
				throw new ServerException("sentence number is greater than the number of sentences in the text");
			}
			CompositePartText someSentence = sentences.get(numberSentence);
			List<ComponentText> partsSentence = someSentence.getComponent();
			SimplePartText part;
			String valuePart;
			ComponentText newComponent;
			for (int i = 0; i < partsSentence.size(); i++) {
				part = (SimplePartText) partsSentence.get(i);
				valuePart = part.getValue();
				if (part.getType().equals(TypeComponent.WORD) && valuePart.length() == wordLength) {
					newComponent = new SimplePartText(null, substring);
					partsSentence.set(i, newComponent);
				}
			}
			StringBuilder result = new StringBuilder();
			someSentence.show(result);
			return result.toString();
		}
	};

	public final String operation;
	private final static String QUESTION_MARK = "?";
	private final static String DELIMITER_WORDS = "; ";
	private final static String LINE_BREAK = "\n";
	private final static String INFORMATION_REQUEST_SPLIT = ",";
	private final static String SPACE = " ";
	private final static String WITHOUT_DIVISION = "";

	TextOperation(String contentOperation) {
		operation = contentOperation;
	}

	public abstract String doOperation(CompositePartText text, String informationRequest) throws ServerException;

}
