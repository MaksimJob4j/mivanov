/**
 * 1. Crud servlet [#2512]
 * Создать модель User c полями name, login, email, createDate.
 * Необходимо создать сервлет UsersServlet и определить там методы do Get Post Put Delete.
 * Каждый метод сервлета должен выполнять только одно действие - создать пользователя, редактировать, получить. Удалить.
 * Тестирование сервлета осуществялять через Test RESTFull service
 * Для хранения данных использовать базу данных Postgresql.
 * Все взаимодействие с базой данных вынеси в отдельный класс UserStore. Этот класс должен быть потокобезопастный сингленот
 * https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
 * Использовать Eager initiazitation.
 * В web.xml указать для UsersServiler режим load-on-startup
 * http://www.xyzws.com/servletfaq/what-is-%3Cloadonstartup%3E-in-webxml-file/24
 * В сервлете создать поле
 * private final UserStore users = UserStore.getInstance();
 * В задании так же объяснить почему при запуске сервера происходить создание коннекта к базе.
 * https://www.javaworld.com/article/3040564/learn-java/java-101-class-and-object-initialization-in-java.html
 */
package ru.job4j.crudservlet;