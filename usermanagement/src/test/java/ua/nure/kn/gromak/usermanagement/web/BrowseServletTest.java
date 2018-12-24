package ua.nure.kn.gromak.usermanagement.web;

import org.junit.Before;
import org.junit.Test;
import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DatabaseException;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BrowseServletTest extends MockServletTestCase {

    private static final Long TEST_ID = 1000L;
    private static final String TEST_ID_STR = "1000";
    private static final String TEST_LAST_NAME = "Gromak";
    private static final String TEST_FIRST_NAME = "Alexandra";
    private static final String TITLE_MES = "Could not find error message";
    private static final String TITLE_ERROR = "Incorrect ID";

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    @Test
    public void testBrowse() {
        User user = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        List<User> list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);
        doGet();
        Collection<User> collection = (Collection<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull(TITLE_MES, collection);
        assertSame(list, collection);

    }

    public void testEdit() {
        User user = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", TEST_ID, user);
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", TEST_ID_STR);
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(TITLE_MES, user);
        assertSame(user, userInSession);
    }


    public void testDetails() {
        User user = new User(TEST_ID,  TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", TEST_ID, user);
        addRequestParameter("detailsButton", "Details");
        addRequestParameter("id", TEST_ID_STR);
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(TITLE_MES, userInSession);
        assertEquals(user, userInSession);
    }

    public void testDelete() {
        User user = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", TEST_ID, user);
        getMockUserDao().expect("delete", user);
        addRequestParameter("deleteButton", "Delete");
        addRequestParameter("id", TEST_ID_STR);
        doPost();

        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("No such user in session scope", userInSession);
    }

    public void testDeleteWithIncorrectId() {
        getMockUserDao().expectAndThrow("find", TEST_ID, new DatabaseException(TITLE_ERROR));
        addRequestParameter("deleteButton", "Delete");
        addRequestParameter("id", TEST_ID_STR);
        doPost();
        String mockErrorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(TITLE_MES, mockErrorMessage);
        assertTrue(mockErrorMessage.contains(mockErrorMessage));
    }

    private class BrowseServlet {
    }
}