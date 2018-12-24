package ua.nure.kn.gromak.usermanagement.db;

import ua.nure.kn.gromak.usermanagement.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class MockUserDao implements UserDao {
    private long id = 0;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) throws DatabaseException {
        Long currentId = new Long(++id);
        user.setId(currentId);
        users.put(currentId, user);
        return null;
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public User delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        return user;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) throws DatabaseException {
    }

}

