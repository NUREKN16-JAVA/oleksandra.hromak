package ua.nure.kn.gromak.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
    private final static DaoFactory INSTANCE = new DaoFactory();
    private static final String USER_DAO_IMPL = "ua.nure.kn.gromak.usermanagement.db.UserDao";
    private static Properties properties;
    private static DaoFactory instance;

    public ConnectionFactory getConnectionFactory() {
        String driver = properties.getProperty("connection.driver");
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        String url = properties.getProperty("connection.url");
        return new ConnectionFactoryImpl(driver, url, user, password);
    }

    private DaoFactory() {
        this.properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));

        } catch (IOException e) {
            throw new RuntimeException("Can not create ", e);
        }
    }

    public static void init(Properties prop) {
        properties = prop;
        instance = null;
    }


    public UserDao getUserDao() {
        UserDao userDao = null;

        try {
            Class userDaoImplementationClass = Class
                    .forName(properties
                            .getProperty(USER_DAO_IMPL));

            userDao = (UserDao) userDaoImplementationClass.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Cannot load the DAO implementation class!", e);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        return userDao;
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

}