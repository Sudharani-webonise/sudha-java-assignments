package com.netmagic.spectrum.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This class used to format the date
 * 
 * @author webonise
 *
 */
public class DateUtils {

    private static final String GMT_MS = "yyyy-MM-dd HH:mm:ss.S";

    private static final String GMT_MM = "yyyy-MM-dd HH:mm:ss";

    private static final String DDMMMYYYY = "dd MMM yyyy";

    private static final String YYYYMMDD = "yyyy-MM-dd";

    public static String changeGmtMsToDDMMYYYY(String date) {
        if ( !date.isEmpty() ) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(GMT_MS);
            return formatter.parseLocalDate(date).toString(DDMMMYYYY);
        }
        return date;
    }

    public static String getStartDate() {
        DateTime now = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern(YYYYMMDD);
        return formatter.print(now);
    }

    public static String getUTCFormattedTimestamp() {
        DateTime now = new DateTime();
        return now.toDateTime(DateTimeZone.UTC).toString();
    }

    public static Timestamp getTimestamp() {
        Date now = new Date();
        return new Timestamp(now.getTime());
    }

    public static String getYYYYMMDDHHMMSSFormat() {
        DateTime now = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern(GMT_MM);
        return formatter.print(now);
    }

    public static String getEndDate(String cntrperiod) {
        DateTime now = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern(YYYYMMDD);
        return formatter.print(now.plusMonths(Integer.parseInt(cntrperiod)).minusDays(1));
    }

    public static boolean compareSofLastRetrivalTime(Timestamp lastRetrivalTime) {
        int timeDifference = Math.toIntExact(TimeUnit.MINUTES
                .convert(System.currentTimeMillis() - lastRetrivalTime.getTime(), TimeUnit.MILLISECONDS));
        if ( timeDifference >= 3 ) {
            return true;
        }
        return false;
    }
}
