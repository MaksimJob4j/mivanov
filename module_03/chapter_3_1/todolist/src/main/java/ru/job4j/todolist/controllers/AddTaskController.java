package ru.job4j.todolist.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.job4j.todolist.dao.Item;
import ru.job4j.todolist.dao.StoreException;
import ru.job4j.todolist.store.ItemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;

public class AddTaskController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(AddTaskController.class);

    private ItemStore store = ItemStore.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();

        Item item = new Item();
        item.setTask(req.getParameter("task"));
        item.setCreated(ZonedDateTime.now());
        item.setDone(false);
        try {
            store.create(item);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}