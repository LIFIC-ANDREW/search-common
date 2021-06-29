package io.lific.search.common.utils.config;

import com.typesafe.config.Config;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;


public class TestConfig extends Configuration {

	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");

	private final String stringValue;
	private final int integerValue;
	private final long longValue;
	private final double doubleValue;
	private final List<String> stringListValue;
	private final DateTime dateTimeValue;
	private final boolean booleanValue;

	public TestConfig(Config config) {
		super(config);
		stringValue = getStringFromConfig(config, "values.string-value", true);
		integerValue = getIntegerFromConfig(config, "values.integer-value", true);
		longValue = getLongFromConfig(config, "values.long-value", true);
		doubleValue = getDoubleFromConfig(config, "values.double-value", true);
		stringListValue = getStringListFromConfig(config, "values.stringlist-value", true);
		dateTimeValue = getDateTimeFromConfig(config, "values.datetime-value", formatter, true);
		booleanValue = getBooleanFromConfig(config, "values.boolean-value", true);
	}

	public static DateTimeFormatter getFormatter() {
		return formatter;
	}

	public String getStringValue() {
		return stringValue;
	}

	public int getIntegerValue() {
		return integerValue;
	}

	public long getLongValue() {
		return longValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public List<String> getStringListValue() {
		return stringListValue;
	}

	public DateTime getDateTimeValue() {
		return dateTimeValue;
	}

	public boolean getBooleanValue() {
		return booleanValue;
	}

}
