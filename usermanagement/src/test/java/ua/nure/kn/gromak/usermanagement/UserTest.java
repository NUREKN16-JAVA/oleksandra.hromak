package ua.nure.kn.gromak.usermanagement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;
    private static final Long ID = 1L;
    private static final String FIRST_NAME_ETALONE = "Alexandra";
    private static final String LAST_NAME_ETALONE = "Gromak";
    private static final String FULL_NAME_ETALONE = "Gromak, Alexandra";

    // Was written in November 2018
    private static final int YEAR_OF_BIRTH = 1999;
    private static final int ETALONE_AGE_1 = 19;
    private static final int DAY_OF_BIRTH_1 = 13;
    private static final int MONTH_OF_BIRTH_1 = 7;
    private static final int ETALONE_AGE_2 = 18;

    //b-day was earlier this month
    private static final int DAY_OF_BIRTH_2 = 1;
    private static final int MONTH_OF_BIRTH_2 = Calendar.NOVEMBER;

    // In case b-day was earlier this year
    private static final int DAY_OF_BIRTH_3 = 13;
    private static final int MONTH_OF_BIRTH_3 = Calendar.JUNE;

    // In case b-day is today
    private static final int DAY_OF_BIRTH_4 = 3;
    private static final int MONTH_OF_BIRTH_4 = Calendar.DECEMBER;

    // In case b-day is coming later this month
    private static final int DAY_OF_BIRTH_5 = 30;
    private static final int MONTH_OF_BIRTH_5 = Calendar.NOVEMBER;

    // In case b-day is coming later this year;
    private static final int DAY_OF_BIRTH_6 = 6;
    private static final int MONTH_OF_BIRTH_6 = Calendar.DECEMBER;

    @Before
    public void setUp() throws Exception {
        user = new User(1L, "Alexandra", "Gromak", new Date(99, 8, 13));
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

    //If the birthday was earlier this year
    @Test
    public void birthdayWasThisYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_1, user.getAge());
    }

    //If the birthday will be later this year
    @Test
    public void birthdayWillThisYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_6, DAY_OF_BIRTH_6);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_2, user.getAge());
    }

    //If the birthday was earlier this month
    @Test
    public void birthdayWasThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_1, user.getAge());
    }

    //If the birthday is later this month
    @Test
    public void birthdayWillThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_5, DAY_OF_BIRTH_5);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_2, user.getAge());
    }

    //If the birthday is equals current date
    @Test
    public void birthdayIsEqualsCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_4, DAY_OF_BIRTH_4);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETALONE_AGE_1, user.getAge());
    }
}