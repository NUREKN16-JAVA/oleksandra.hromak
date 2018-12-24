package ua.nure.kn.gromak.usermanagement.web;

import ua.nure.kn.gromak.usermanagement.User;

import java.text.DateFormat;
import java.util.Date;

public class AddServletTest extends MockServletTestCase {

    private static final Long TEST_ID = 1000L;
    private static final String TEST_LAST_NAME = "Gromak";
    private static final String TEST_FIRST_NAME = "Alexandra";
    private static final String TITLE_ERROR = "Error";
    private static final String TITLE_MES = "Could not find error message";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }
    public void testAdd() {
        Date date = new Date();
        User newUser = new User(TEST_FIRST_NAME, TEST_LAST_NAME, date);
        User user = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, date);

        getMockUserDao().expectAndReturn("create", newUser, user);

        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okayButton", "Okay");
        doPost();
    }

    public void testAddEmptyFirstName() {
        Date date = new Date();
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okayButton", "Okay");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

    public void testAddEmptyLastName() {
        Date date = new Date();
        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okayButton", "Okay");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

    public void testAddEmptyDate() {
        Date date = new Date();
        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("okayButton", "Okay");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

    public void testAddInvalidDate() {
        Date date = new Date();
        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("date", "helphelphelp");
        addRequestParameter("okayButton", "Okay");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

}