package io.lific.search.common.utils.collection;

import io.lific.search.common.utils.test.TestClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StringIgnoreSetTest extends TestClass {

    @Test
    public void testAll() {
        StringIgnoreSet set = new StringIgnoreSet();

        Object abc = "abc";
        Object ABC = "ABC";

        // 대소문자 삽입/삭제
        set.add(ABC.toString());
        assertTrue(set.contains(abc));
        set.remove(abc);
        assertFalse(set.contains(ABC));
        set.add(abc.toString());
        assertTrue(set.contains(ABC));
        set.remove(ABC);
        assertFalse(set.contains(abc));
    }

}