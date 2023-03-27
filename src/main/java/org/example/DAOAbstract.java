package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAOAbstract<T> {
    private final Connection CONN;
    private final String TABLE;

    public DAOAbstract(Connection conn, String table) {
        this.CONN = conn;
        this.TABLE = table;
    }

    public void createTable(Class<T> cls) {
        Field[] fields = cls.getDeclaredFields();
        Field id = getIdField(fields);

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ")
                .append(TABLE).append("(");

        sql.append(id.getName())
                .append(" INT AUTO_INCREMENT PRIMARY KEY,");

        for (Field f : fields) {
            if (f != id) {
                f.setAccessible(true);
                sql.append(f.getName()).append(" ");

                if (f.getType() == Integer.class || f.getType() == int.class) {
                    sql.append("INT,");
                } else if (f.getType() == Double.class || f.getType() == double.class) {
                    sql.append("DOUBLE,");
                } else if (f.getType() == String.class) {
                    sql.append("VARCHAR(100),");
                } else
                    throw new RuntimeException("****** Wrong type ******");
            }
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");

        try {
            try (Statement st = CONN.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void add(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            StringBuilder sbSQL = new StringBuilder();
            StringBuilder sbColums = new StringBuilder();
            StringBuilder sbValues = new StringBuilder();
            sbSQL.append("INSERT INTO " + TABLE + " ( ");

            for (int i = 0; i < fields.length; i++) {
                if (fields[i].isAnnotationPresent(Prim.class)) continue;
                fields[i].setAccessible(true);
                sbColums.append(fields[i].getName() + ",");
                sbValues.append("\"" + fields[i].get(t) + "\",");
            }

            sbColums.deleteCharAt(sbColums.length() - 1);
            sbValues.deleteCharAt(sbValues.length() - 1);

            sbSQL.append(sbColums).append(")").append(" VALUES(").append(sbValues.append(")"));

            Statement st = CONN.createStatement();
            st.execute(sbSQL.toString());
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Field getIdField(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Prim.class)) {
                return fields[i];
            }
        }
        throw new RuntimeException("Primary Key wasn't found in this class");
    }

    public void showOrderTotalPayByClientId(int clientId) {
        try (Statement st = CONN.createStatement();) {
            ResultSet resultSet = st.executeQuery("SELECT SUM(sumOfOrder) FROM OrdersTable WHERE clientID = " + clientId);
            while (resultSet.next()) {
                System.out.println(Double.valueOf(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getCONN() {
        return CONN;
    }

    public String getTABLE() {
        return TABLE;
    }
}
