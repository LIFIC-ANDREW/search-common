package io.lific.search.common.utils.throwable;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface IntConsumerWithThrowable<E extends Throwable> extends IntConsumer {

	static <E extends Throwable> IntConsumerWithThrowable<E> castIntConsumerWithThrowable(final IntConsumerWithThrowable<E> intconsumerwiththrowable) {
		return intconsumerwiththrowable;
	}

	static <E extends Throwable> IntConsumerWithThrowable<E> asIntConsumerWithThrowable(final IntConsumer intconsumer) {
		return intconsumer::accept;
	}

	@Override
	default void accept(final int v1) {
		try {
			acceptWithThrowable(v1);
		} catch (final RuntimeException | Error exception) {
			throw exception;
		} catch (final Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	void acceptWithThrowable(final int v1) throws E;

}
