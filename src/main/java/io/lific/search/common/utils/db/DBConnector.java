package io.lific.search.common.utils.db;

import java.io.Closeable;
import java.sql.*;
import java.util.Properties;


public class DBConnector implements Closeable {

    private Connection connection;

    public DBConnector(String jdbcUrl) throws SQLException {
        this(DriverManager.getConnection(jdbcUrl));
    }

    public DBConnector(String jdbcUrl, Properties properties) throws SQLException {
        this(DriverManager.getConnection(jdbcUrl, properties));
    }

    public DBConnector(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public ResultSet select(String sql) throws SQLException {
        return connection.prepareStatement(sql).executeQuery();
    }

    public int count(String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) return result.getInt(1);
            }
        }
        return 0;
    }

    public boolean create(String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.execute();
        }
    }

    public boolean exist(String table) throws SQLException {
        try (ResultSet rs = connection.getMetaData().getTables(null, null, table, null)) {
            while (rs.next()) {
                String name = rs.getString("TABLE_NAME");
                if (name != null && name.equals(table)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int insert(String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        }
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                connection = null;
            }
        }
    }

}
