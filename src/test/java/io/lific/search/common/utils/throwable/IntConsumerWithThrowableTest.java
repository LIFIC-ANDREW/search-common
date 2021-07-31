package io.lific.search.common.utils.throwable;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class IntConsumerWithThrowableTest {

	@Test
	void streamExceptionTest() {
		try {
			IntStream.range(0, 10)
				.forEachOrdered(IntConsumerWithThrowable.castIntConsumerWithThrowable(
					n -> {
						System.out.println(n);
						if (n == 9) {
							throw new Exception("error");
						}
					}
				));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}