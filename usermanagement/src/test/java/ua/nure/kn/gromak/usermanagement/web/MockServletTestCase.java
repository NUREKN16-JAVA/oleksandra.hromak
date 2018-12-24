package ua.nure.kn.gromak.usermanagement.web;

import com.mockobjects.dynamic.Mock;
import org.junit.After;
import org.junit.Before;
import ua.nure.kn.gromak.usermanagement.db.DaoFactory;
import ua.nure.kn.gromak.usermanagement.db.MockDaoFactory;

import java.util.Properties;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    private static final String DAO_FACTORY = "dao.factory";
    private Mock mockUserDao;

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty(DAO_FACTORY, MockDaoFactory.class.getName());
        DaoFactory.init(properties);
        setMockUserDao(((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao());
    }

    @After
    protected void tearDown() throws Exception {
        super.tearDown();
        mockUserDao.verify();
    }



}
