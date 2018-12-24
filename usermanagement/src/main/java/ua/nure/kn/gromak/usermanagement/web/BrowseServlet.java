package ua.nure.kn.gromak.usermanagement.web;

import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DaoFactory;
import ua.nure.kn.gromak.usermanagement.db.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class BrowseServlet extends HttpServlet {

    private static final String TITLE_ERROR = "Error";
    private static final String TITLE_MES = "You must select a user";
    private static final String T_ID = "id";
    private static final String T_USER = "user";
    private static final String T_USERS = "users";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getParameter("addButton"))) {
            add(req, resp);
        } else if (Objects.nonNull(req.getParameter("editButton"))) {
            edit(req, resp);
        } else if (Objects.nonNull(req.getParameter("deleteButton"))) {
            delete(req, resp);
        } else if (Objects.nonNull(req.getParameter("detailsButton"))) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter(T_ID);
        if (Objects.isNull(idString) || idString.trim().isEmpty()) {
            req.setAttribute(TITLE_ERROR, TITLE_MES);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idString));
            req.getSession(true).setAttribute(T_USER, user);
        } catch (Exception e) {
            req.setAttribute(TITLE_ERROR, "Error:" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter(T_ID);
        if (Objects.isNull(idString) || idString.trim().isEmpty()) {
            req.setAttribute(TITLE_ERROR, TITLE_MES);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idString));
            req.getSession(true).setAttribute(T_USER, user);
        } catch (Exception e) {
            req.setAttribute(TITLE_ERROR, "Error:" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/details").forward(req, resp);
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Collection<User> users = DaoFactory.getInstance().getUserDao().findAll();
            req.getSession(true).setAttribute(T_USERS, users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter(T_ID);
        if (Objects.isNull(idString) || idString.trim().isEmpty()) {
            req.setAttribute(TITLE_ERROR, TITLE_MES);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idString));
            DaoFactory.getInstance().getUserDao().delete(user);
        } catch (DatabaseException e) {
            req.setAttribute(TITLE_ERROR, "Error:" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/browse");
    }

    private String getIdUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(T_ID);
        if (id == null || id.trim().isEmpty()) {
            req.setAttribute(TITLE_ERROR, TITLE_MES);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return null;
        }
        return id;
    }
}
