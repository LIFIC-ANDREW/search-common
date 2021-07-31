package io.lific.search.common.utils.throwable;

import java.util.function.Function;

@FunctionalInterface
public interface FunctionWithThrowable<T, R, E extends Throwable> extends Function<T, R> {

	static <T, R, E extends Throwable> FunctionWithThrowable<T, R, E> castFunctionWithThrowable(final FunctionWithThrowable<T, R, E> functionWithThrowable) {
		return functionWithThrowable;
	}

	R applyWithThrowable(final T v1) throws E;

	@Override
	default R apply(final T v1) {
		try {
			return applyWithThrowable(v1);
		} catch (final RuntimeException | Error exception) {
			throw exception;
		} catch (final Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}
}
