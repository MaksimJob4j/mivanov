package ru.job4j.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.music.dao.StoreException;
import ru.job4j.music.entities.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoleFilter implements Filter {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.music.RoleFilter.class);

    private final MusicLogic logic = MusicLogic.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.traceEntry();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        try {
            if (loginUser != null) {
                if ((req.getRequestURI().contains("/create_user") && loginUser.getRole().getLevel() < 5)
                        || (req.getRequestURI().contains("/delete_user") && loginUser.getRole().getLevel() != 10)) {
                    resp.sendRedirect(String.format("%s/", req.getContextPath()));
                } else if (
                        (req.getRequestURI().contains("/user_info") || req.getRequestURI().contains("/update_user"))
                                && loginUser.getRole().getLevel() != 10
                                && !String.valueOf(loginUser.getId()).equals(req.getParameter("id"))) {
                    User user = null;
                    user = logic.findUserById(req.getParameter("id"));
                    if (user == null || loginUser.getRole().getLevel() <= user.getRole().getLevel()) {
                        resp.sendRedirect(String.format("%s/", req.getContextPath()));
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (StoreException e) {
            LOGGER.error("error", e);
            resp.sendError(500, e.getMessage());
        }
    }
}