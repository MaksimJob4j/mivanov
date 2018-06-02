package ru.job4j.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class CityController extends HttpServlet {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.CityController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.traceEntry();
        resp.setContentType("text/json");
        String country = req.getParameter("country");
        StringBuilder str = new StringBuilder();
        Set<String> cities = new CityHandler().getCities(country);
        if (cities.size() > 0) {
            str.append("[");
            for (String cit : cities) {
                if (!cit.equals("Other")) {
                    str.append("{\"city\" : \"" + cit + "\"}, ");
                }
            }
            if (str.length() > 1) {
                str.delete(str.length() - 2, str.length());
            }
            str.append("]");
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(str.toString());
        writer.flush();
    }
}