/**
 * 1. Crud servlet, Web app architecture. [#2512]
 *
 * В это задании нужно создать веб приложение для работы с пользователем.
 * Модель данных.
 * Создать модель User c полями id, name, login, email, createDate. Это модель данных.
 *
 * ---Presentation layout.
 * Необходимо создать сервлет UserServlet и определить там методы doGet doPost. Это будет слой Presentation.
 * Метод doGet - должен отдавать список всех пользователей в системе.
 * Метод doPost - должен  уметь делать три вещи, создавать пользователя, обновлять поля пользователя, удалять пользователя.
 * Применить шаблон dispatch-pattern - https://github.com/peterarsentev/code_quality_principles#2-dispatch-pattern-instead-of-multiple-if-statements-and-switch-anti-pattern
 * Тестирование сервлета осуществлять через Test RESTFull service
 *
 * ---Logic Layout
 * Далее создайте класс ValidateService - это слой Logic. В нем нужно добавить методы
 * add, update, delete, findAll, findById
 * Каждый метод должен производить валидацию данных. Например, при обновлении полей нужно проверить. что такой пользователь существует.
 * Класс ValidateService сделать синглетоном. Использовать Eager initiazitation.
 *
 * ---Persistent Layout
 * Создать интерфейс Store c методами add, update, delete, findAll, findById
 * Сделать реализацию этого интерфейса MemoryStore. Сделать из него синлетон.
 *
 * Класс MemoryStore - должен быть потокобезопастный. вы можете использовать внутри потокбезопастные коллекции.
 * В web.xml указать для UserServiler режим load-on-startup
 * http://www.xyzws.com/servletfaq/what-is-%3Cloadonstartup%3E-in-webxml-file/24
 *
 *  Связывание слоев.
 *  Связывание слоев происходит за счет передачи ссылок на объект синглетона.
 *  Помните слой может взаимодействавать только с ниже стоящим слоем.
 *
 */
package ru.job4j.crudservlet2;
