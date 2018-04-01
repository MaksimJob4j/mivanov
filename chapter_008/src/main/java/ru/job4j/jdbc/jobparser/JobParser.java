package ru.job4j.jdbc.jobparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class JobParser {
    private final static Logger LOGGER = Logger.getLogger(ru.job4j.jdbc.jobparser.JobParser.class);
    private final File propFile  = new File("jobparser.properties");
    private Properties prop = new Properties();
    private SQLHandler sqlHandler = new SQLHandler();
    private String urlGlobal;
    private LocalDateTime updateDate;
    private LocalDateTime newUpdateDate;
    private Long updateTimeout;

    private void init() {
        LOGGER.debug("Вызван метод init");
        try (FileInputStream fileInputStream = new FileInputStream(propFile)) {
            LOGGER.debug("Чтение Properties из файла" + propFile);
            prop.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Ошибка " + e);
        }
        urlGlobal = prop.getProperty("url");
        LOGGER.debug("url:" + urlGlobal);
        String updateDateStr = prop.getProperty("update");
        LOGGER.debug("Прочитана дата последнего обновления: " + updateDateStr);
        if (updateDateStr == null || updateDateStr.equals("")) {
            updateDate = LocalDateTime.of(LocalDateTime.now().getYear(), 1, 1, 0, 0, 0);
        } else {
            updateDate = LocalDateTime.parse(updateDateStr);
        }
        LOGGER.info("Дата последнего обновления установлена на: " + updateDate);
        updateTimeout = Long.parseLong(prop.getProperty("timeoutMinutes"));
        LOGGER.info("Период обновления: " + updateTimeout + " минут(а)");
        newUpdateDate = updateDate.plusMinutes(updateTimeout);
        LOGGER.debug("Дата следующего обновленмя установлена: " + newUpdateDate);
        LOGGER.debug("Properties прочтены из файла" + propFile);
    }

    private List<JobOffer> parsOffers() {
        LOGGER.debug("Вызван метод parsOffers");
        newUpdateDate = LocalDateTime.now();
        return parsOffersURL(urlGlobal, fixedTopics());
    }

    private Integer fixedTopics() {
        LOGGER.debug("Вызван метод fixedTopics");
        Integer result = 0;
        try {
            Document docFirst = Jsoup.connect(urlGlobal).get();
            Document docSecond = Jsoup.connect(getNextPageLink(urlGlobal)).get();
            Elements trElementsFirst = docFirst.select(".forumTable tr").next();
            Elements trElementsSecond = docSecond.select(".forumTable tr").next();
            while (trElementsFirst.first().text().equals(trElementsSecond.first().text())) {
                result++;
                LOGGER.debug("Найдено закрепленных строк: " + result);
                trElementsFirst = trElementsFirst.next();
                trElementsSecond = trElementsSecond.next();
            }
        } catch (IOException e) {
            LOGGER.error("Ошибка " + e);
        }
        return result;
    }

    private String getNextPageLink(String url) {
        LOGGER.debug("Вызван метод getNextPageLink с параметром " + url);
        String result = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".sort_options td b").next();
            result = elements.attr("href");
            LOGGER.debug("Получена ссылка: " + result);
        } catch (IOException e) {
            LOGGER.error("Ошибка " + e);
        }
        return result;
    }

    private List<JobOffer> parsOffersURL(String url, Integer fixedTopics) {
        LOGGER.info("Вызван метод parsOffersURL с параметрами: url- " + url + " К-во закрепленныых строк-" + fixedTopics);
        List<JobOffer> result = new LinkedList<>();
        try {
            Document doc = Jsoup.connect(url).get();

            Elements trElements = doc.select(".forumTable tr").next();
            for (int i = 0; i < fixedTopics; i++) {
                if (urlGlobal.equals(url)) {
                    LOGGER.debug("Парсинг закрепленной строки");
                    // Парсинг закрепленных строк на первой странице форума
                    JobOffer offer = parsOffer(trElements.first());
                    if (updateDate.isBefore(offer.changed) && suitableTopic(offer.jobTopic)) {
                        result.add(offer);
                        LOGGER.info("Найден подходящий офер: " + offer.jobTopic);
                    }
                }
                LOGGER.debug("Переход к следующей строке");
                trElements = trElements.next();
            }
            LOGGER.debug("Парсинг строк");
            for (Element trElement : trElements) {
                LOGGER.debug("Парсинг строки");
                JobOffer offer = parsOffer(trElement);
                LOGGER.debug("Проверка даты офера");
                if (updateDate.isBefore(offer.changed)) {
                    LOGGER.debug("Проверка офера на соответствие условию");
                    if (suitableTopic(offer.jobTopic)) {
                        LOGGER.info("Найден подходящий офер: " + offer.jobTopic);
                        result.add(offer);
                    }
                } else {
                    LOGGER.debug("Выход из метода и возврат " + result.size() + " найденных строк");
                    return result;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Ошибка " + e);
        }
        LOGGER.debug("Получаем ссылку на следующую страницу");
        String nextPage = getNextPageLink(url);
        LOGGER.debug("Получена ссылка: " + nextPage);
        if (!nextPage.equals("")) {
            LOGGER.debug("Закпускаем парсинг по новой ссылке и добавляем результат к уже полученным оферам");
            result.addAll(parsOffersURL(nextPage, fixedTopics));
        }
        LOGGER.debug("Выход из метода и возврат " + result.size() + " найденных строк");
        return result;
    }

    private JobOffer parsOffer(Element trElement) {
        LOGGER.debug("Вызван метод parsOffer с параметром: " + trElement);
        LOGGER.debug("Запускается парсинг элемента");
        JobOffer offer = new JobOffer();
        Elements tds = trElement.select("td");
        Element postsListTopicA = tds.select(".postslisttopic a").first();
        offer.jobTopic = postsListTopicA.text();
        offer.jobLink = postsListTopicA.attr("href");
        String closedTopic = tds.select(".postslisttopic .closedTopic").text();
        if (closedTopic != null && closedTopic.contains("закрыт")) {
            offer.closed = true;
        }
        Element altColA = tds.select(".altCol a").first();
        offer.authorName = altColA.text().trim();
        offer.authorLink = altColA.attr("href");
        offer.changed = Helper.getDateFromString(tds.last().text());
        LOGGER.debug("Возврат офера из метода");
        return offer;
    }

    private Boolean suitableTopic(String topic) {
        LOGGER.debug("Вызван метод parsOffer с параметром: " + topic);
        topic = topic.toLowerCase();
        String[] unsuitableWords = {"javascript", "java script", "java-script"};
        for (String str: unsuitableWords) {
            topic = topic.replaceAll(str, "");
        }
        Boolean result = topic.contains("java");
        LOGGER.debug("Возвращаем " + result);
        return result;
    }

    private void startProgram() {
        LOGGER.debug("Вызван метод startProgram");
        init();
        sqlHandler.initBase(prop);
        while (true) {
            if (LocalDateTime.now().isAfter(newUpdateDate)) {
                LOGGER.debug("Проверка показала, что время парсинга уже наступило");
                startParser();
            } else {
                LOGGER.debug("Проверка показала, что время парсинга еще не наступило");
                try {
                    Long timeToSlip = Helper.mlSec(LocalDateTime.now(), newUpdateDate);
                    LOGGER.debug("Посчитано время на сон " + timeToSlip + "мс");
                    timeToSlip = timeToSlip > 0 ? timeToSlip : 0;
                    LOGGER.info("Программа уходит на сон на " + timeToSlip + "мс");
                    Thread.sleep(timeToSlip);
                } catch (InterruptedException e) {
                    LOGGER.error("Ошибка " + e);
                }
            }
        }
    }

    private void startParser() {
        LOGGER.debug("Вызван метод startParser");
        List<JobOffer> offers = parsOffers();
        Boolean baseHasActualDate = true;
        LOGGER.debug("К записи в базу готово " + offers.size() + " строк.");
        if (offers.size() > 0) {
            baseHasActualDate = sqlHandler.saveData(offers);
        }
        if (baseHasActualDate) {
            setNewDateUpdate();
            LOGGER.debug("Установлена новая дата парсинга");
        }
    }

    private void setNewDateUpdate() {
        LOGGER.debug("Вызван метод setNewDateUpdate");
        updateDate = newUpdateDate;
        LOGGER.debug("Дата прошедшего обновления установлена на " + updateDate);
        newUpdateDate = updateDate.plusMinutes(updateTimeout);
        LOGGER.info("Дата следующего обновления установлена на " + newUpdateDate);
        LOGGER.debug("Запись новой даты в Propertys");
        prop.setProperty("update", updateDate.toString());
        LOGGER.debug("Сохранение Propertys в файл");
        try (FileOutputStream fileOutputStream = new FileOutputStream(propFile)) {
            prop.store(fileOutputStream, null);
        } catch (IOException e) {
            LOGGER.error("Ошибка " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        JobParser jobParser = new JobParser();
        jobParser.startProgram();
    }
}
