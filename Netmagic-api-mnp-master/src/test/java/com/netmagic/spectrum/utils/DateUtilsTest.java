package com.netmagic.spectrum.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {

    @InjectMocks
    private DateUtils dateUtils;

    private String inputDate;

    private String expected;

    private static final String YYYYMMDD = "yyyy-MM-dd";

    @Test
    public void testChangeGmtMsToDDMMYYYY() {
        inputDate = "2012-01-15 05:30:00.0";
        expected = "15 Jan 2012";
        assertEquals(expected, DateUtils.changeGmtMsToDDMMYYYY(inputDate));
    }

    @Test
    public void testEmptyChangeGmtMsToDDMMYYYY() {
        String emptyString = "";
        assertEquals(emptyString, DateUtils.changeGmtMsToDDMMYYYY(emptyString));
    }

    @Test
    public void testGetUTCFormattedTimestamp() {
        assertNotNull(DateUtils.getUTCFormattedTimestamp());
    }

    @Test
    public void testGetStartDate() {
        assertNotNull(DateUtils.getStartDate());
    }

    @Test
    public void testGetTimestamp() {
        assertNotNull(DateUtils.getTimestamp());
    }

    @Test
    public void testGetYYYYMMDDHHMMSSFormat() {
        assertNotNull(DateUtils.getYYYYMMDDHHMMSSFormat());
    }

    @Test
    public void testGetEndDate() {
        DateTime now = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern(YYYYMMDD);
        assertEquals(formatter.print(now.plusMonths(3).minusDays(1)), (DateUtils.getEndDate("3")));
    }

    @Test
    public void testCompareSofLastRetrivalTime() {
        Date now = new Date();
        assertFalse(DateUtils.compareSofLastRetrivalTime(new Timestamp(now.getTime())));
    }

}
