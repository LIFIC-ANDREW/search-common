package io.lific.search.common.utils.config;

import java.net.URL;


public class Loader {

	private Loader() {
	}

	public static URL getResource(String resource) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		if (classLoader == null) classLoader = Loader.class.getClassLoader();

		URL url = classLoader.getResource(resource);

		return (url == null) ? ClassLoader.getSystemResource(resource) : url;
	}

}
