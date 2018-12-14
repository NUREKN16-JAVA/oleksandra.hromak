package ua.nure.kn.gromak.usermanagement.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.gromak.usermanagement.User;
import ua.nure.kn.gromak.usermanagement.db.DaoFactory;
import ua.nure.kn.gromak.usermanagement.db.MockDaoFactory;
import ua.nure.kn.gromak.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainFrameTest extends JFCTestCase {
    private Mock mockUserDao;
    private MainFrame mainFrame;

    private Component find(Class<?> componentClass, String name){
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Sorry, could not find" + name + " ", component);
        return component;
    }


    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory",MockDaoFactory.class.getName());
        DaoFactory.init(properties);
        mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
        mockUserDao.expectAndReturn("findAll",new ArrayList());
        setHelper(new JFCTestHelper());
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public void tearDown() throws Exception {
        mockUserDao.verify();
        mainFrame.setVisible(false);
        TestHelper.cleanUp(this);
        super.tearDown();
    }

    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(3, table.getColumnCount());
        assertEquals(Messages.getString("user_management.userTable"), table.getColumnName(0));
        assertEquals(Messages.getString("user_management.userTable2"), table.getColumnName(1));
        assertEquals(Messages.getString("user_management.userTable3"), table.getColumnName(2));
    }

    public void testAddUser(){
        String firstName = "George";
        String lastName = "Bush";
        Date now = new Date();

        DateFormat formatter = new SimpleDateFormat();
        String date = formatter.format(now);

        User user = new User(firstName, lastName, now);
        User expectedUser = new User(1L, firstName,lastName, now);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        ArrayList<User> users = new ArrayList<User>();
        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable table;
        table = (JTable) find(JTable.class, "userTable");
        assertEquals(0,table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        JButton okButton = (JButton) find(JButton.class, "okayButton");

        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

        find(JButton.class, "cancelButton");
        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        table = (JTable) find(JTable.class, "userTable");
        assertEquals(1,table.getRowCount());
    }
}