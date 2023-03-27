package org.example;

import java.sql.Connection;

public class DAOImplementation<T> extends DAOAbstract<T> {
    public DAOImplementation(Connection conn, String table) {
        super(conn, table);
    }
}
