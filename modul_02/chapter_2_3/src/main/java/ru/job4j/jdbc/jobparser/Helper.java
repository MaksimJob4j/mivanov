package ru.job4j.jdbc.jobparser;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Helper {
private final static Logger LOGGER = Logger.getLogger(ru.job4j.jdbc.jobparser.Helper.class);

    /**
     * Парсинг даты из строки.
     * @param dateStr String.
     * @return LocalDateTime.
     */
    static LocalDateTime getDateFromString(String dateStr) {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("dateStr: " + dateStr);
        DateTimeFormatter form = DateTimeFormatter.ofPattern("d MMM yy", new Locale("ru"));
        if (dateStr.contains("сегодня")) {
            dateStr = dateStr.replace("сегодня", LocalDateTime.now().format(form));
        } else if (dateStr.contains("вчера")) {
            dateStr = dateStr.replace("вчера", LocalDateTime.now().minusDays(1).format(form));
        }
        dateStr = dateStr.replace("май", "мая");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy, HH:mm", new Locale("ru"));
        LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
        LOGGER.debug("Возвращаем дату " + date);
        return date;
    }

    /**
     * Период между двумя датами LocalDateTime в миллисекундах.
     * @param fomDate Дата начала отсчета.
     * @param toDate Дата конца отсчета.
     * @return Продолжительность периода в миллесекундах.
     */
    static Long mlSec(LocalDateTime fomDate, LocalDateTime toDate) {
        LOGGER.debug("Вызван метод");
        LOGGER.debug("fomDate: " + fomDate + " toDate: " + toDate);
        Long result;

        if (toDate.toLocalDate().isAfter(fomDate.toLocalDate())) {
            result =  86_400_000 + mlSec(fomDate.plusDays(1), toDate); // 24 * 60 * 60 * 1000
            LOGGER.debug("Возвращаем: " + result);
            return result;
        } else if (toDate.toLocalDate().compareTo(fomDate.toLocalDate()) == 0) {
            result = (
                    (toDate.getHour() - fomDate.getHour()) * 60 * 60
                    + (toDate.getMinute() - fomDate.getMinute()) * 60
                    + (toDate.getSecond() - fomDate.getSecond()) + 1)
                    * 1000L;
            LOGGER.debug("Возвращаем: " + result);
            return result;
        } else {
            result =  -mlSec(toDate, fomDate);
            LOGGER.debug("Возвращаем: " + result);
            return result;
        }
    }
}
