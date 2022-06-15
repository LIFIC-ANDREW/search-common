package io.lific.search.common.utils.throwable;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.stream.IntStream;

class ConsumerWithThrowableTest {

	@Test
	void streamExceptionTest() {
		try {
			Consumer<String> consumer = ConsumerWithThrowable.castConsumerWithThrowable(s -> {
				System.out.println(s.toUpperCase());
				if (s.equals("error")) {
					throw new Exception("error");
				}
			});
			consumer.accept("error");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}