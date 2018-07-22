package ru.job4j.userservlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoleFilter implements Filter {
    private final static Logger LOGGER = LogManager.getLogger(ru.job4j.userservlet.RoleFilter.class);

    private final ValidateService users = ValidateService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.traceEntry();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.traceEntry();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String login = String.valueOf(req.getSession().getAttribute("login"));
        if (!login.equals("")) {
            User loginUser = null;
            try {
                loginUser = users.findByLogin(login);
            } catch (UserException e) {
                e.printStackTrace();
            }
            if (req.getRequestURI().contains("/create")
                    && !loginUser.getRole().equals("admin")) {
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else if (req.getRequestURI().contains("/edit")
                    && !loginUser.getRole().equals("admin")
                    && !loginUser.getId().equals(req.getParameter("id"))) {
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        LOGGER.traceEntry();
    }
}
