package ua.nure.kn.gromak.usermanagment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class UserTest {
    private User user;
    private static final Long ID = 1L;
    private static final String FIRST_NAME_ETALONE = "Alexandra";
    private static final String LAST_NAME_ETALONE = "Gromak";
    private static final String FULL_NAME_ETALONE = "Gromak, Alexandra";

    private static final int YEAR_OF_BIRTH = 1999;
    private static final int ETALONE_AGE_1 = 19;
    private static final int DAY_OF_BIRTH_1 = 13;
    private static final int MONTH_OF_BIRTH_1 = 7;

    private static final int ETALONE_AGE_2 = 18;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFullName() {
        user.setFirstName(FIRST_NAME_ETALONE);
        user.setLastName(LAST_NAME_ETALONE);
        assertEquals(FULL_NAME_ETALONE, user.getFullName());
    }

    @Test
    public void testGetAge1(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_1, DAY_OF_BIRTH_1);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_1, user.getAge());
    }

    //If the birthday was this year
    @Test
    public void birthdayWasThisYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, calendar.get(Calendar.MONTH)-2, 13);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_1, user.getAge());
    }

    //If the birthday will be later this year
    @Test
    public void birthdayWillThisYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, calendar.get(Calendar.MONTH)+2, 6);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_2, user.getAge());
    }

    //If the birthday was earlier this month
    @Test
    public void birthdayWasThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, calendar.get(Calendar.MONTH), 1);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_1, user.getAge());
    }

    //If the birthday is later this month
    @Test
    public void birthdayWillThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, calendar.get(Calendar.MONTH), 29);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_2, user.getAge());
    }

    //If the birthday is equals current date
    @Test
    public void birthdayIsEqualsCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_1, user.getAge());
    }
}