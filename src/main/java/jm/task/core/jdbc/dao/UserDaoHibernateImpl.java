package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS  %s ( id BIGINT not NULL AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT, PRIMARY KEY ( id ))";
    private static final String DROP_USERS_TABLE = "drop table if exists ";
    private static final String CLEAR_USERS_TABLE = "DELETE FROM ";
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSession();) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(String.format(CREATE_USERS_TABLE, user.getClass().getSimpleName())).executeUpdate();
            transaction.commit();
            System.out.println("Created table in given database...");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSession();) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(DROP_USERS_TABLE + user.getClass().getSimpleName()).executeUpdate();
            transaction.commit();
            System.out.println("Dropped  table in given database...");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSession();) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User with name: \"" + name + " " + lastName + "\" added to database...");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSession();) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Deleted user in given database, id " + id + "...");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public List < User > getAllUsers() {
        List < User > result = new ArrayList< >();
        Transaction transaction = null;
        try (Session session = Util.getSession();) {
            transaction = session.beginTransaction();
            User user = new User();
            result.addAll(session.createQuery("FROM " + user.getClass().getSimpleName()).getResultList());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSession();) {
            User user = new User();
            transaction = session.beginTransaction();
            session.createNativeQuery(CLEAR_USERS_TABLE + user.getClass().getSimpleName()).executeUpdate();
            transaction.commit();
            System.out.println("Cleared table in given database...");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

}
