package ua.nure.kn.gromak.usermanagement.web;

import ua.nure.kn.gromak.usermanagement.User;

import static org.junit.Assert.assertNull;

public class DetailsServletTest extends MockServletTestCase {

    private static final String BACK_BUT = "backButton";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    public void testBack() {
        addRequestParameter(BACK_BUT, "Back");
        User user = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("User was not deleted from session", user);
    }
}