package io.lific.search.common.utils.throwable;

import org.junit.jupiter.api.Test;

import java.util.List;

class FunctionWithThrowableTest {

	@Test
	void streamExceptionTest() {
		try {
			List<String> list = List.of("a", "b", "c");
			list.stream().map(
				FunctionWithThrowable.castFunctionWithThrowable(
					value -> {
						if (value.equals("c")) {
							throw new Exception("error");
						}
						value = value + "-test";
						return value;
					}
				)
			);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}