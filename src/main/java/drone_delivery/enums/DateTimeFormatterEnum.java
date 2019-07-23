/*
 * Author: Samuel Wanck
 */

package drone_delivery.enums;

import java.time.format.DateTimeFormatter;

public enum DateTimeFormatterEnum {

    HOURS_MINUTES_SECONDS(DateTimeFormatter.ofPattern("HH:mm:ss"));

    private DateTimeFormatter dateTimeFormatter;

    DateTimeFormatterEnum(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
