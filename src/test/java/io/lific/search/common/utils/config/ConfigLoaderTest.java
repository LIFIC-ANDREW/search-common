package io.lific.search.common.utils.config;

import com.typesafe.config.Config;
import io.lific.search.common.utils.test.TestClass;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigLoaderTest extends TestClass {

	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");

	@Test
	public void test() {
		File configFile = getResourceFile("test.conf");

		check(ConfigLoader.load(configFile));
		check(ConfigLoader.parse(
			"values {\n" +
				"string-value = \"abc\"\n" +
				"integer-value = 100\n" +
				"long-value = 100000000000\n" +
				"double-value = 10000000.1234\n" +
				"stringlist-value = [ \"a1\", \"a2\", \"a3\" ]\n" +
				"datetime-value = \"20190308\"\n" +
				"boolean-value = true\n" +
				"}")
		);
		check((TestConfig) ConfigLoader.build(TestConfig.class, configFile));
	}

	public void check(Config config) {
		assertEquals("abc", Configuration.getStringFromConfig(config, "values.string-value", true));
		assertEquals(Integer.valueOf(100), Configuration.getIntegerFromConfig(config, "values.integer-value", true));
		assertEquals(Long.valueOf(100000000000L), Configuration.getLongFromConfig(config, "values.long-value", true));
		assertEquals(Double.valueOf(10000000.1234), Configuration.getDoubleFromConfig(config, "values.double-value", true));
		assertArrayEquals(new String[]{"a1", "a2", "a3"}, Configuration.getStringListFromConfig(config, "values.stringlist-value", true).toArray(new String[0]));
		assertEquals(formatter.parseDateTime("20190308"), Configuration.getDateTimeFromConfig(config, "values.datetime-value", formatter, true));
		assertEquals(true, Configuration.getBooleanFromConfig(config, "values.boolean-value", true));
	}

	public void check(TestConfig config) {
		assertEquals("abc", config.getStringValue());
		assertEquals(100, config.getIntegerValue());
		assertEquals(100000000000L, config.getLongValue());
		assertEquals(10000000.1234d, config.getDoubleValue(), 0.00001d);
		List<String> list = new ArrayList<>(3);
		list.add("a1");
		list.add("a2");
		list.add("a3");
		assertEquals(list, config.getStringListValue());
		assertEquals(formatter.parseDateTime("20190308"), config.getDateTimeValue());
		assertTrue(config.getBooleanValue());

	}

}