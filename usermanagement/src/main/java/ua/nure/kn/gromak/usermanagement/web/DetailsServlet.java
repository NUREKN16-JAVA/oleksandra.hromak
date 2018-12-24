package ua.nure.kn.gromak.usermanagement.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailsServlet extends HttpServlet {

    private static final String T_USER = "user";
    private static final String BACK_BUTT = "backButton";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        if (req.getParameter(BACK_BUTT) != null) {
            req.getSession(true).removeAttribute(T_USER);
            req.getRequestDispatcher("/browse").forward(req, resp);
        } else {
            req.getRequestDispatcher("/details.jsp").forward(req, resp);
        }
    }
}