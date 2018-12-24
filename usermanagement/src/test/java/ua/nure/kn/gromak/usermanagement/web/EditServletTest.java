package ua.nure.kn.gromak.usermanagement.web;

import ua.nure.kn.gromak.usermanagement.User;

import java.text.DateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class EditServletTest extends MockServletTestCase {

    private static final Long TEST_ID = 1000L;
    private static final String TEST_ID_STR = "1000";
    private static final String TEST_LAST_NAME = "Gromak";
    private static final String TEST_FIRST_NAME = "Alexandra";
    private static final String TITLE_ERROR = "Error";
    private static final String TITLE_MES = "Could not find error message";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);    }

    public void testEdit() {
        Date date = new Date();
        User user = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, date);
        getMockUserDao().expect("update", user);

        addRequestParameter("id", TEST_ID_STR);
        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okayButton", "Okay");
        doPost();
    }

    public void testEditEmptyFirstName() {
        Date date = new Date();
        addRequestParameter("id", TEST_ID_STR);
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okayButton", "Okay");
        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

    public void testEditEmptyLastName() {
        Date date = new Date();
        addRequestParameter("id", TEST_ID_STR);
        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okayButton", "Okay");
        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

    public void testEditEmptyDate() {
        Date date = new Date();
        addRequestParameter("id", TEST_ID_STR);
        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("okayButton", "Okay");
        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

    public void testEditInvalidDate() {
        Date date = new Date();
        addRequestParameter("id", TEST_ID_STR);
        addRequestParameter("firstName", TEST_FIRST_NAME);
        addRequestParameter("lastName", TEST_LAST_NAME);
        addRequestParameter("date", "helphelphelp");
        addRequestParameter("okayButton", "Okay");
        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(TITLE_ERROR);
        assertNotNull(TITLE_MES, errorMessage);
    }

}
