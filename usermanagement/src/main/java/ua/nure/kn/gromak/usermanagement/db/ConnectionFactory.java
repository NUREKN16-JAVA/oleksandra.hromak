package ua.nure.kn.gromak.usermanagement.db;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection() throws DatabaseException;
}
