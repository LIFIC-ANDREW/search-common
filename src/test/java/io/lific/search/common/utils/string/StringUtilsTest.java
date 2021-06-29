package io.lific.search.common.utils.string;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;


public class StringUtilsTest {

    @Test
    public void test() {
        List<String> testList = new ArrayList<>(3);
        testList.add("abc def");
        testList.add("ghi");
        testList.add("jkl");
        assertThat(
                StringUtils.splitIgnoringInQuotes("\"abc def\" ghi jkl", " ")
                , is(testList)
        );

        assertEquals(
                "bcdef"
                , StringUtils.charsToString(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'}, 1, 6)
        );

        assertEquals(
                StringUtils.CharCodeType.KOREAN
                , StringUtils.getCharCodeType('가')
        );
        assertEquals(
                StringUtils.CharCodeType.ENG
                , StringUtils.getCharCodeType('A')
        );
        assertEquals(
                StringUtils.CharCodeType.NUM
                , StringUtils.getCharCodeType('1')
        );
        assertEquals(
                StringUtils.CharCodeType.ETC
                , StringUtils.getCharCodeType('@')
        );

        assertTrue(StringUtils.isSpecialChar('#'));
        assertFalse(StringUtils.isSpecialChar('a'));
        assertTrue(StringUtils.isSpecialChar("!@#"));
        assertFalse(StringUtils.isSpecialChar("abc"));

        assertTrue(StringUtils.hasWhiteSpace("abc def"));
        assertFalse(StringUtils.hasWhiteSpace("abcdef"));

        assertTrue(StringUtils.isEnglishChar("abc"));
        assertFalse(StringUtils.isEnglishChar("abc가나다"));
        assertTrue(StringUtils.isEnglishChar('a'));
        assertFalse(StringUtils.isEnglishChar('가'));
        assertTrue(StringUtils.isEnglishChar((CharSequence) "abc"));
        assertFalse(StringUtils.isEnglishChar((CharSequence) "abc가나다"));

        assertTrue(StringUtils.isNumberChar("12300"));
        assertFalse(StringUtils.isNumberChar("abcdef"));
        assertTrue(StringUtils.isNumberChar('0'));
        assertFalse(StringUtils.isNumberChar('가'));
        assertFalse(StringUtils.isNumberChar('a'));
        assertFalse(StringUtils.isNumberChar('#'));

        assertEquals(
                "is google web site."
                , StringUtils.removeUrl("https://www.google.com/ is google web site.")
        );

        assertEquals(
                "is google mail address."
                , StringUtils.removeEmail("abc@gmail.com is google mail address.")
        );

        assertEquals(
                "a b c d e f"
                , StringUtils.refineAllWhiteSpace("a\tb\nc\fd\re f")
        );
    }
}