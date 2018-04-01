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
        LOGGER.debug("Вызван метод LocalDateTime c с параметром: " + dateStr);
        DateTimeFormatter form = DateTimeFormatter.ofPattern("d MMM yy", new Locale("ru"));
        if (dateStr.contains("сегодня")) {
            LOGGER.debug("Сторка содержит слово \"сегодня\"");
            dateStr = dateStr.replace("сегодня", LocalDateTime.now().format(form));
        } else if (dateStr.contains("вчера")) {
            LOGGER.debug("Сторка содержит слово \"вчера\"");
            dateStr = dateStr.replace("вчера", LocalDateTime.now().minusDays(1).format(form));
        }
        LOGGER.debug("Замена в строке \"май\" на \"мая\" при наличии");
        dateStr = dateStr.replace("май", "мая");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy, HH:mm", new Locale("ru"));
        LOGGER.debug("Парсинг строки в дату");
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
        LOGGER.debug("Вызван метод mlSec с параметрами: fomDate-" + fomDate + " toDate-" + toDate);
        Long result;

        if (toDate.toLocalDate().isAfter(fomDate.toLocalDate())) {
            LOGGER.debug("День fromDate раньше дня toDate");
            result =  86_400_000 + mlSec(fomDate.plusDays(1), toDate); // 24 * 60 * 60 * 1000
            LOGGER.debug("Возвращаем: " + result);
            return result;
        } else if (toDate.toLocalDate().compareTo(fomDate.toLocalDate()) == 0) {
            LOGGER.debug("Дни fromDate и toDate совпадают");
            result = (
                    (toDate.getHour() - fomDate.getHour()) * 60 * 60
                    + (toDate.getMinute() - fomDate.getMinute()) * 60
                    + (toDate.getSecond() - fomDate.getSecond()) + 1)
                    * 1000L;
            LOGGER.debug("Возвращаем: " + result);
            return result;
        } else {
            LOGGER.debug("День fromDate позже дня toDate");
            result =  -mlSec(toDate, fomDate);
            LOGGER.debug("Возвращаем: " + result);
            return result;
        }
    }
}
