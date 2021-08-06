package io.lific.search.common.utils.throwable;

import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerWithThrowable<T, E extends Throwable> extends Consumer<T> {

	static <T, E extends Throwable> ConsumerWithThrowable<T, E> castConsumerWithThrowable(final ConsumerWithThrowable<T, E> consumerWithThrowable) {
		return consumerWithThrowable;
	}

	static <T, E extends Throwable> ConsumerWithThrowable<T, E> asConsumerWithThrowable(final Consumer<T> consumer) {
		return consumer::accept;
	}

	@Override
	default void accept(final T v1) {
		try {
			acceptWithThrowable(v1);
		} catch (final RuntimeException | Error exception) {
			throw exception;
		} catch (final Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	void acceptWithThrowable(final T v1) throws E;

}