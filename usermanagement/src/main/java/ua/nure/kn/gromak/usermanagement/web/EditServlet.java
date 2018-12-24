package ua.nure.kn.gromak.usermanagement.web;
import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DaoFactory;
import ua.nure.kn.gromak.usermanagement.db.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditServlet extends HttpServlet {

    private static final String TITLE_ERROR = "Error";
    private static final String FIRST_N = "First name is empty";
    private static final String LAST_N = "Last name is empty";
    private static final String DATE = "Date is empty";
    private static final String DATE_INCR = "Date format is incorrect!";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("okayButton") != null){
            doOkay(req, resp);
        } else if(req.getParameter("cancelButton") != null){
            doCancel(req, resp);
        } else{
            showPage(req, resp);
        }
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    private void doOkay(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        User user = null;
        try {
            user = getUser(req);
        } catch (ValidationException e1) {
            req.setAttribute(TITLE_ERROR, e1.getMessage());
            showPage(req, resp);
            return;
        } try {
            processUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
            new ServletException();
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        req.getRequestDispatcher("/browse.jsp").forward(req, resp);
    }

    private User getUser(HttpServletRequest req) throws ValidationException {
        User user = new User();
        String id = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String date = req.getParameter("date");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        if (firstName == null || firstName.length() == 0) {
            throw new ValidationException(FIRST_N);
        }
        if (lastName == null || lastName.length() == 0) {
            throw new ValidationException(LAST_N);
        }
        if(date == null || date.length() == 0){
            throw new ValidationException(DATE);
        }
        if(id != null){
            user.setId(new Long(id));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(format.parse(date));
        } catch (ParseException e) {
            throw new ValidationException(DATE_INCR);
        }
        return user;
    }

    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().update(user);
    }
}
