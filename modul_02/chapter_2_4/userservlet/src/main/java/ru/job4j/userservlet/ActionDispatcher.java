package ru.job4j.userservlet;

import ru.job4j.userservlet.store.UserException;
import ru.job4j.userservlet.store.ValidateService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ActionDispatcher {

    private final Map<Action.Type, Function<Action, String>> dispatch = new HashMap<>();

    private Function<Action, String> add(HttpServletRequest req, ValidateService users) {
        return action -> {
            User user = new User();
            user.setName(req.getParameter("name"));
            user.setLogin(req.getParameter("login"));
            user.setEmail(req.getParameter("email"));
            try {
                return users.add(user).toString();
            } catch (UserException e) {
                return e.getMessage();
            }
        };
    }

    private Function<Action, String> update(HttpServletRequest req, ValidateService users) {
        return action -> {
            try {
                User user = new User();
                user.setId(req.getParameter("id"));
                user.setName(req.getParameter("name"));
                user.setLogin(req.getParameter("login"));
                user.setEmail(req.getParameter("email"));
                return users.update(user).toString();
            } catch (UserException e) {
                return e.getMessage();
            }
        };
    }

    private Function<Action, String> delete(HttpServletRequest req, ValidateService users) {
        return action -> {
            try {
                return users.delete(req.getParameter("id")).toString();
            } catch (UserException e) {
                return e.getMessage();
            }
        };
    }

    ActionDispatcher init(HttpServletRequest req, ValidateService users) {
        this.load(Action.Type.ADD, this.add(req, users));
        this.load(Action.Type.UPDATE, this.update(req, users));
        this.load(Action.Type.DELETE, this.delete(req, users));
        return this;
    }

    private void load(Action.Type type, Function<Action, String> handle) {
        this.dispatch.put(type, handle);
    }

    String execute(final Action action) {
        return this.dispatch.get(
                action.type()
        ).apply(action);
    }
}

interface Action {
    Type type();
    enum Type {
        ADD,
        UPDATE,
        DELETE
    }

}

