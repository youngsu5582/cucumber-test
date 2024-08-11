package corea.support;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeInstantProvider {

    public static Instant from(final String localDateTime) {
        return from(LocalDateTime.parse(localDateTime));
    }

    public static Instant from(final LocalDateTime localDateTime) {
        return Instant.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
    }

    public static Instant from(final LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
