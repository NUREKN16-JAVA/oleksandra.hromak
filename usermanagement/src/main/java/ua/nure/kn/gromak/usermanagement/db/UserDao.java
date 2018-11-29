package ua.nure.kn.gromak.usermanagement.db;

import ua.nure.kn.gromak.usermanagement.User;

import java.util.Collection;

public interface UserDao {
    /**
     *"@param user with null id
     * @return modifies iser with auto generated id from DB
     */
    User create(User user) throws DatabaseException;

    User find(Long id) throws DatabaseException;

    Collection<User> findAll() throws DatabaseException;

    User update(User user) throws DatabaseException;

    User delete(User user) throws DatabaseException;

    void setConnectionFactory(ConnectionFactory connectionFactory);
}



