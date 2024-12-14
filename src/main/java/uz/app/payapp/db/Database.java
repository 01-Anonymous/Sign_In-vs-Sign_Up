package uz.app.payapp.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.SneakyThrows;

import java.sql.*;

public class Database {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;

    private Database() {
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/pay_app",
                    "postgres",
                    "root123"
            );

            entityManagerFactory = Persistence.createEntityManagerFactory("unit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public Connection getConnection() {
        return connection;
    }

    @SneakyThrows
    public Statement getStatement() {
        return connection.createStatement();
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
