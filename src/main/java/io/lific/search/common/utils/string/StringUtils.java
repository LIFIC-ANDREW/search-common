package io.lific.search.common.utils.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Character.isSpaceChar;


public class StringUtils {

	public enum CharCodeType {KOREAN, ENG, NUM, ETC}

	private static final String SPECIAL_CHAR_REGEXP = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]+";

	private static final Pattern URL_PATTERN = Pattern.compile("((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?+-=\\\\.&]*)", Pattern.CASE_INSENSITIVE);

	private static final Pattern EMAIL_PATTERN = Pattern.compile("([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)");

	private StringUtils() {

	}

	public static List<String> splitIgnoringInQuotes(String input, String delim) {
		if (input == null) return Collections.emptyList();

		List<String> list = new ArrayList<>();
		Matcher matcher = Pattern.compile("[\"" + delim + "]").matcher(input);
		int pos = 0;
		boolean quoteMode = false;
		while (matcher.find()) {
			String sep = matcher.group();
			if ("\"".equals(sep)) {
				quoteMode = !quoteMode;
			} else if (!quoteMode && delim.equals(sep)) {
				int toPos = matcher.start();
				if (input.charAt(pos) == '\"') pos++;
				if (input.charAt(toPos - 1) == '\"') toPos--;
				list.add(input.substring(pos, toPos));
				pos = matcher.end();
			}
		}
		if (pos < input.length()) {
			int toPos = input.length();
			if (input.charAt(pos) == '\"') pos++;
			if (input.charAt(toPos - 1) == '\"') toPos--;
			list.add(input.substring(pos, toPos));
		}

		return list;
	}

	public static String charsToString(char[] src, int sIdx, int eIdx) {
		char[] dest = new char[eIdx - sIdx];
		System.arraycopy(src, sIdx, dest, 0, eIdx - sIdx);
		return new String(dest);
	}

	public static CharCodeType getCharCodeType(char ch) {
		if (isKoreanChar(ch)) return CharCodeType.KOREAN;
		else if (isEnglishChar(ch)) return CharCodeType.ENG;
		else if (isNumberChar(ch)) return CharCodeType.NUM;

		return CharCodeType.ETC;
	}

	public static boolean isKoreanChar(char ch) {
		// ?????? ?????? : U+1100 ~ U+11FF
		// ????????? ?????? ?????? : U+3130 ~ U+318F
		// ?????? ?????? ??????-A : U+A960 ~ U+A97F
		// ?????? ?????? ?????? : U+AC00 ~ U+D7AF
		// ?????? ?????? ??????-B : U+D7B0 ~ U+D7FF

		return (ch >= '\u1100' && ch <= '\u11FF')
			|| (ch >= '\u3130' && ch <= '\u318F')
			|| (ch >= '\uA960' && ch <= '\uA97F')
			|| (ch >= '\uAC00' && ch <= '\uD7FF');
	}

	public static boolean isSpecialChar(char ch) {
		return !isKoreanChar(ch) && !isEnglishChar(ch) && !isNumberChar(ch) && !isSpaceChar(ch);
	}

	public static boolean isSpecialChar(String str) {
		return str.matches(SPECIAL_CHAR_REGEXP);
	}

	public static boolean hasWhiteSpace(String input) {
		return input.indexOf(' ') >= 0;
	}

	public static boolean isEnglishChar(char input) {
		return (input >= 'a' && input <= 'z') || (input >= 'A' && input <= 'Z');
	}

	public static boolean isEnglishChar(CharSequence input) {
		for (int cnum = 0; cnum < input.length(); cnum++) {
			if (!isEnglishChar(input.charAt(cnum))) return false;
		}
		return true;
	}

	public static boolean isEnglishChar(String input) {
		for (char ch : input.toCharArray()) {
			if (!isEnglishChar(ch)) return false;
		}
		return true;
	}

	public static boolean isNumberChar(char input) {
		return input >= '0' && input <= '9';
	}

	public static boolean isNumberChar(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static String removeUrl(String text) {
		Matcher m = URL_PATTERN.matcher(text);
		int i = 0;
		while (m.find()) {
			text = text.replaceAll(m.group(i), "").trim();
			i++;
		}
		return text;
	}

	public static String removeEmail(String text) {
		Matcher m = EMAIL_PATTERN.matcher(text);
		int i = 0;
		while (m.find()) {
			text = text.replaceAll(m.group(i), "").trim();
			i++;
		}
		return text;
	}

	// all whitespace ([ \t\n\x0B\f\r]) -> " "
	public static String refineAllWhiteSpace(String text) {
		return text.replaceAll("\\s+", " ");
	}

	public static String convertToString(Map<?, ?> map) {
		return map.keySet().stream()
			.map(key -> key + "=" + map.get(key))
			.collect(Collectors.joining(", ", "{", "}"));
	}

}
