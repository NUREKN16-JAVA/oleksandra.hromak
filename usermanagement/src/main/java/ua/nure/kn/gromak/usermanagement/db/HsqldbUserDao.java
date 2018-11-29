package ua.nure.kn.gromak.usermanagement.db;

import ua.nure.kn.gromak.usermanagement.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

class HsqldbUserDao implements UserDao {
    private static final String INSERT_QUERY = "INSERT INTO users (Name, Surname, YearBirth) VALUES(?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT id, Name, Surname, YearBirth FROM users";
    private static final String FIND_BY_ID_QUERY = "SELECT id, Name, Surname, YearBirth FROM users WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET Name = ?, Surname = ?, YearBirth = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private ConnectionFactory connectionFactory;

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public HsqldbUserDao() {
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            int n = statement.executeUpdate();
            if(n != 1) {
                throw new DatabaseException("Number of the inserted rows: " + n);
            }
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            if(keys.next()) {
                user.setId(new Long(keys.getLong(1)));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return user;

        } catch(DatabaseException e) {
            throw e;
        }

        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User update(User user) throws DatabaseException {
        Connection connection = connectionFactory.createConnection();
        try {
            PreparedStatement statement = connection
                    .prepareStatement(UPDATE_QUERY);
            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setDate(3,new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());
            int number = statement.executeUpdate();
            if(number != 1){
                throw new DatabaseException("Number of updated raws: " + number);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return user;
    }

    @Override
    public User find(Long id) throws DatabaseException {
        User user = new User();
        try {
            user = null;
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
            preparedStatement.setLong(1,id);
            ResultSet oneUserResultSet = preparedStatement.executeQuery();
            if (oneUserResultSet.next()){
                user = new User();
                user.setId(new Long(oneUserResultSet.getLong("ID")));
                user.setFirstName(oneUserResultSet.getString("Name"));
                user.setLastName(oneUserResultSet.getString("Surname"));
                user.setDateOfBirth(oneUserResultSet.getDate("YearBirth"));
            }
            connection.close();
            preparedStatement.close();
            oneUserResultSet.close();
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Collection findAll() throws DatabaseException {
        Collection result = new LinkedList<>();

        Connection connection = connectionFactory.createConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while(resultSet.next()) {
                User user = new User();
                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return result;
    }

    @Override
    public User delete(User user) throws DatabaseException {
        Connection connection = connectionFactory.createConnection();
        try {
            PreparedStatement statement = connection
                    .prepareStatement(DELETE_QUERY);
            statement.setLong(1,user.getId());
            int number = statement.executeUpdate();
            if(number != 1){
                throw new DatabaseException("Number of deleted raws: " + number);
            }


            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return user;
    }

}