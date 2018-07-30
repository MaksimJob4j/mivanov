package ru.job4j.todolist.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.job4j.todolist.dao.StoreException;
import ru.job4j.todolist.store.ItemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeDoneController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ChangeDoneController.class);

	private ItemStore store = ItemStore.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        try {
            store.changeDone(Integer.parseInt(req.getParameter("id")));
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}