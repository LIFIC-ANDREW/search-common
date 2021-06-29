package io.lific.search.common.utils.date;

import io.lific.search.common.utils.test.TestClass;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static io.lific.search.common.utils.date.DateTimeUtils.TimeUnit.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class DateTimeUtilsTest extends TestClass {

    private static final Calendar calendar = Calendar.getInstance();

    private static final Date date;
    private static final DateTime dateTime;

    private static final Date date1;
    private static final DateTime dateTime1;

    private static final Date date2;
    private static final DateTime dateTime2;

    private static final Date date3;
    private static final DateTime dateTime3;

    private static final Date date4;
    private static final DateTime dateTime4;

    private static final Date date5;
    private static final DateTime dateTime5;

    private static final Date date6;
    private static final DateTime dateTime6;

    static {
        calendar.set(1981, Calendar.MARCH, 9, 2, 30, 49); // monday
        date = calendar.getTime(); // 1981.03.09 02:30:49
        dateTime = new DateTime(date);

        calendar.set(2001, Calendar.MARCH, 9, 2, 30, 49);
        date1 = calendar.getTime(); // 2001.03.09 02:30:49
        dateTime1 = new DateTime(date1);

        calendar.set(1981, Calendar.MAY, 9, 2, 30, 49);
        date2 = calendar.getTime(); // 1981.05.09 02:30:49
        dateTime2 = new DateTime(date2);

        calendar.set(1981, Calendar.MARCH, 19, 2, 30, 49);
        date3 = calendar.getTime(); // 1981.03.19 02:30:49
        dateTime3 = new DateTime(date3);

        calendar.set(1981, Calendar.MARCH, 9, 4, 30, 49);
        date4 = calendar.getTime(); // 1981.03.09 04:30:49
        dateTime4 = new DateTime(date4);

        calendar.set(1981, Calendar.MARCH, 9, 2, 40, 49);
        date5 = calendar.getTime(); // 1981.03.09 02:40:49
        dateTime5 = new DateTime(date5);

        calendar.set(1981, Calendar.MARCH, 9, 2, 30, 59);
        date6 = calendar.getTime(); // 1981.03.09 02:30:59
        dateTime6 = new DateTime(date6);
    }

    @Test
    public void test() {
        assertEquals(date1, DateTimeUtils.plusYear(date, 20));
        assertEquals(date2, DateTimeUtils.plusMonth(date, 2));
        assertEquals(date3, DateTimeUtils.plusDay(date, 10));
        assertEquals(date4, DateTimeUtils.plusHour(date, 2));
        assertEquals(date5, DateTimeUtils.plusMinute(date, 10));
        assertEquals(date6, DateTimeUtils.plusSecond(date, 10));

        assertEquals(date1, DateTimeUtils.plusDate(date, 20, YEAR));
        assertEquals(date2, DateTimeUtils.plusDate(date, 2, MONTH));
        assertEquals(date3, DateTimeUtils.plusDate(date, 10, DAY));
        assertEquals(date4, DateTimeUtils.plusDate(date, 2, HOUR));
        assertEquals(date5, DateTimeUtils.plusDate(date, 10, MINUTE));
        assertEquals(date6, DateTimeUtils.plusDate(date, 10, SECOND));
        assertEquals(date, DateTimeUtils.plusDate(date, 1, WEEK));

        assertEquals(dateTime1, DateTimeUtils.plusDateTime(dateTime, 20, YEAR));
        assertEquals(dateTime2, DateTimeUtils.plusDateTime(dateTime, 2, MONTH));
        assertEquals(dateTime3, DateTimeUtils.plusDateTime(dateTime, 10, DAY));
        assertEquals(dateTime4, DateTimeUtils.plusDateTime(dateTime, 2, HOUR));
        assertEquals(dateTime5, DateTimeUtils.plusDateTime(dateTime, 10, MINUTE));
        assertEquals(dateTime6, DateTimeUtils.plusDateTime(dateTime, 10, SECOND));
        assertEquals(dateTime, DateTimeUtils.plusDateTime(dateTime, 1, WEEK));

        calendar.set(1982, Calendar.APRIL, 29, 18, 40, 53); // wednesday
        Date after = calendar.getTime();
        assertEquals(1, DateTimeUtils.getTimeUnitDiff(after, date, YEAR));
        assertEquals(12 + 1, DateTimeUtils.getTimeUnitDiff(after, date, MONTH));
        assertEquals((365 + 51) / 7, DateTimeUtils.getTimeUnitDiff(after, date, WEEK));
        assertEquals(365 + 51, DateTimeUtils.getTimeUnitDiff(after, date, DAY));
        assertEquals((365 + 51) * 24 + 16, DateTimeUtils.getTimeUnitDiff(after, date, HOUR));
        assertEquals(((365 + 51) * 24 + 16) * 60 + 10, DateTimeUtils.getTimeUnitDiff(after, date, MINUTE));
        assertEquals((((365 + 51) * 24 + 16) * 60 + 10) * 60 + 4, DateTimeUtils.getTimeUnitDiff(after, date, SECOND));

        assertEquals(
            date2
            , DateTimeUtils.min(date1, date2)
        );
        assertEquals(
            date2
            , DateTimeUtils.min(date2, date1)
        );
        assertEquals(
            date1
            , DateTimeUtils.max(date1, date2)
        );
        assertEquals(
            date1
            , DateTimeUtils.max(date2, date1)
        );
        assertEquals(
            dateTime2
            , DateTimeUtils.min(dateTime1, dateTime2)
        );
        assertEquals(
            dateTime2
            , DateTimeUtils.min(dateTime2, dateTime1)
        );
        assertEquals(
            dateTime1
            , DateTimeUtils.max(dateTime1, dateTime2)
        );
        assertEquals(
            dateTime1
            , DateTimeUtils.max(dateTime2, dateTime1)
        );

        calendar.set(2001, Calendar.JANUARY, 1, 0, 0, 0);
        checkFirstDateTime(calendar, date1, dateTime1, YEAR);
        calendar.set(2001, Calendar.MARCH, 1, 0, 0, 0);
        checkFirstDateTime(calendar, date1, dateTime1, MONTH);
        calendar.set(2001, Calendar.MARCH, 9, 0, 0, 0);
        checkFirstDateTime(calendar, date1, dateTime1, DAY);
        calendar.set(2001, Calendar.MARCH, 9, 2, 0, 0);
        checkFirstDateTime(calendar, date1, dateTime1, HOUR);
        calendar.set(2001, Calendar.MARCH, 9, 2, 30, 0);
        checkFirstDateTime(calendar, date1, dateTime1, MINUTE);
        calendar.set(2001, Calendar.MARCH, 9, 2, 30, 49);
        checkFirstDateTime(calendar, date1, dateTime1, SECOND);

        calendar.set(2001, Calendar.DECEMBER, 31, 23, 59, 59);
        checkLastDateTime(calendar, date1, dateTime1, YEAR);
        calendar.set(2001, Calendar.MARCH, 31, 23, 59, 59);
        checkLastDateTime(calendar, date1, dateTime1, MONTH);
        calendar.set(2001, Calendar.MARCH, 9, 23, 59, 59);
        checkLastDateTime(calendar, date1, dateTime1, DAY);
        calendar.set(2001, Calendar.MARCH, 9, 2, 59, 59);
        checkLastDateTime(calendar, date1, dateTime1, HOUR);
        calendar.set(2001, Calendar.MARCH, 9, 2, 30, 59);
        checkLastDateTime(calendar, date1, dateTime1, MINUTE);
        calendar.set(2001, Calendar.MARCH, 9, 2, 30, 49);
        checkLastDateTime(calendar, date1, dateTime1, SECOND);
    }

    @Test
    public void testRangeDates() {
        assertThat(
            DateTimeUtils.getRangeDates(date, date1, YEAR)
            , is(new Date[]{
                date
                , DateTimeUtils.plusYear(date, 1)
                , DateTimeUtils.plusYear(date, 2)
                , DateTimeUtils.plusYear(date, 3)
                , DateTimeUtils.plusYear(date, 4)
                , DateTimeUtils.plusYear(date, 5)
                , DateTimeUtils.plusYear(date, 6)
                , DateTimeUtils.plusYear(date, 7)
                , DateTimeUtils.plusYear(date, 8)
                , DateTimeUtils.plusYear(date, 9)
                , DateTimeUtils.plusYear(date, 10)
                , DateTimeUtils.plusYear(date, 11)
                , DateTimeUtils.plusYear(date, 12)
                , DateTimeUtils.plusYear(date, 13)
                , DateTimeUtils.plusYear(date, 14)
                , DateTimeUtils.plusYear(date, 15)
                , DateTimeUtils.plusYear(date, 16)
                , DateTimeUtils.plusYear(date, 17)
                , DateTimeUtils.plusYear(date, 18)
                , DateTimeUtils.plusYear(date, 19)
                , DateTimeUtils.plusYear(date, 20)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(dateTime, dateTime1, YEAR)
            , is(new DateTime[]{
                dateTime
                , DateTimeUtils.plusDateTime(dateTime, 1, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 2, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 3, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 4, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 5, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 6, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 7, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 8, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 9, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 10, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 11, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 12, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 13, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 14, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 15, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 16, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 17, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 18, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 19, YEAR)
                , DateTimeUtils.plusDateTime(dateTime, 20, YEAR)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(date, date2, MONTH)
            , is(new Date[]{
                date
                , DateTimeUtils.plusMonth(date, 1)
                , DateTimeUtils.plusMonth(date, 2)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(dateTime, dateTime2, MONTH)
            , is(new DateTime[]{
                dateTime
                , DateTimeUtils.plusDateTime(dateTime, 1, MONTH)
                , DateTimeUtils.plusDateTime(dateTime, 2, MONTH)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(date, date3, DAY)
            , is(new Date[]{
                date
                , DateTimeUtils.plusDay(date, 1)
                , DateTimeUtils.plusDay(date, 2)
                , DateTimeUtils.plusDay(date, 3)
                , DateTimeUtils.plusDay(date, 4)
                , DateTimeUtils.plusDay(date, 5)
                , DateTimeUtils.plusDay(date, 6)
                , DateTimeUtils.plusDay(date, 7)
                , DateTimeUtils.plusDay(date, 8)
                , DateTimeUtils.plusDay(date, 9)
                , DateTimeUtils.plusDay(date, 10)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(dateTime, dateTime3, DAY)
            , is(new DateTime[]{
                dateTime
                , DateTimeUtils.plusDateTime(dateTime, 1, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 2, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 3, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 4, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 5, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 6, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 7, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 8, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 9, DAY)
                , DateTimeUtils.plusDateTime(dateTime, 10, DAY)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(date, date4, HOUR)
            , is(new Date[]{
                date
                , DateTimeUtils.plusHour(date, 1)
                , DateTimeUtils.plusHour(date, 2)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(dateTime, dateTime4, HOUR)
            , is(new DateTime[]{
                dateTime
                , DateTimeUtils.plusDateTime(dateTime, 1, HOUR)
                , DateTimeUtils.plusDateTime(dateTime, 2, HOUR)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(date, date5, MINUTE)
            , is(new Date[]{
                date
                , DateTimeUtils.plusMinute(date, 1)
                , DateTimeUtils.plusMinute(date, 2)
                , DateTimeUtils.plusMinute(date, 3)
                , DateTimeUtils.plusMinute(date, 4)
                , DateTimeUtils.plusMinute(date, 5)
                , DateTimeUtils.plusMinute(date, 6)
                , DateTimeUtils.plusMinute(date, 7)
                , DateTimeUtils.plusMinute(date, 8)
                , DateTimeUtils.plusMinute(date, 9)
                , DateTimeUtils.plusMinute(date, 10)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(dateTime, dateTime5, MINUTE)
            , is(new DateTime[]{
                dateTime
                , DateTimeUtils.plusDateTime(dateTime, 1, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 2, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 3, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 4, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 5, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 6, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 7, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 8, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 9, MINUTE)
                , DateTimeUtils.plusDateTime(dateTime, 10, MINUTE)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(date, date6, SECOND)
            , is(new Date[]{
                date
                , DateTimeUtils.plusSecond(date, 1)
                , DateTimeUtils.plusSecond(date, 2)
                , DateTimeUtils.plusSecond(date, 3)
                , DateTimeUtils.plusSecond(date, 4)
                , DateTimeUtils.plusSecond(date, 5)
                , DateTimeUtils.plusSecond(date, 6)
                , DateTimeUtils.plusSecond(date, 7)
                , DateTimeUtils.plusSecond(date, 8)
                , DateTimeUtils.plusSecond(date, 9)
                , DateTimeUtils.plusSecond(date, 10)
            })
        );
        assertThat(
            DateTimeUtils.getRangeDates(dateTime, dateTime6, SECOND)
            , is(new DateTime[]{
                dateTime
                , DateTimeUtils.plusDateTime(dateTime, 1, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 2, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 3, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 4, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 5, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 6, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 7, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 8, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 9, SECOND)
                , DateTimeUtils.plusDateTime(dateTime, 10, SECOND)
            })
        );
    }

    @Test
    public void testGetDurationString() {
        assertEquals(
            "03:50:00"
            , DateTimeUtils.getDurationString(3600 * 3 + 60 * 50)
        );
    }

    private static void checkFirstDateTime(Calendar firstCal, Date date, DateTime dateTime, DateTimeUtils.TimeUnit unit) {
        Date firstDate = new Date(firstCal.getTime().getTime() / 1000 * 1000);
        DateTime firstDateTime = new DateTime(firstDate);
        assertEquals(
            firstDate
            , DateTimeUtils.getFirstDateTime(date, unit)
        );
        assertEquals(
            firstDateTime
            , DateTimeUtils.getFirstDateTime(dateTime, unit)
        );
    }

    private static void checkLastDateTime(Calendar lastCal, Date date, DateTime dateTime, DateTimeUtils.TimeUnit unit) {
        Date lastDate = new Date(lastCal.getTime().getTime() / 1000 * 1000 + 999);
        DateTime lastDateTime = new DateTime(lastDate);
        assertEquals(
            lastDate
            , DateTimeUtils.getLastDateTime(date, unit)
        );
        assertEquals(
            lastDateTime
            , DateTimeUtils.getLastDateTime(dateTime, unit)
        );
    }

}