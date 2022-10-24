package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserDaoJDBCImpl implements UserDao {

    private static final Logger log = Logger.getLogger(String.valueOf(UserDaoJDBCImpl.class));
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS `test`.`users`
                    (id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50) NOT NULL,
                    lastName VARCHAR(50) NOT NULL,
                    age TINYINT)""";
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
                connection.commit();
                log.info("Ok. table creating...");
            } catch (SQLException ex) {
                if (connection != null) {
                    connection.rollback();
                }
                log.warning("Missing create table failing..." + ex.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            String sql = "DROP TABLE IF EXISTS `test`.`users`";
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
                connection.commit();
                log.info("Ok. table deleting...");
            } catch (SQLException ex) {
                if (connection != null) {
                    connection.rollback();
                }
                log.warning("Missing delete table failing..." + ex.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            String sql = "INSERT INTO `test`.`users` (name, lastName, age) VALUES (?, ?, ?)";
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException ex) {
                if (connection != null) {
                    connection.rollback();
                }
                log.warning("Missing save user in table is failing..." + ex.getMessage());
            }
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            String sql = "DELETE FROM `test`.`users` WHERE id = ?";
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                connection.commit();
                log.info("Ok. User remove by id success...");
            } catch (SQLException ex) {
                if (connection != null) {
                    connection.rollback();
                }
                log.warning("Missing remove user by id table is failing..." + ex.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            String sql = "SELECT * FROM `test`.`users`";
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery(sql);
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    userList.add(user);
                }
                connection.commit();
                log.info("Ok. all users add in the table...");
            } catch (SQLException ex) {
                if (connection != null) {
                    connection.rollback();
                }
                log.warning("Missing get all users add is failing..." + ex.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            String sql = "DELETE FROM `test`.`users`";
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
                connection.commit();
                log.info("Ok. table is a cleaning...");
            } catch (SQLException ex) {
                if (connection != null) {
                    connection.rollback();
                }
                log.warning("Missing table is a clean failing..." + ex.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
