package org.example;

import java.sql.*;

public class Conexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/bd_biblioteca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "daniel31415";

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                createTables();
                System.out.println("Conexión establecida con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return conn;
    }

    private static void createTables() {
        String[] createTableSQL = {
                // Tabla biblioteca
                "CREATE TABLE IF NOT EXISTS biblioteca (" +
                        "id SERIAL PRIMARY KEY, " +
                        "nombre VARCHAR(100) NOT NULL, " +
                        "tamanio DOUBLE PRECISION NOT NULL)",

                // Tabla armario
                "CREATE TABLE IF NOT EXISTS armario (" +
                        "id SERIAL PRIMARY KEY, " +
                        "codigo VARCHAR(10) NOT NULL, " +
                        "tipo VARCHAR(10) CHECK (tipo IN ('madera', 'metalico')) NOT NULL, " +
                        "biblioteca_id INTEGER REFERENCES biblioteca(id))",

                // Tabla libro
                "CREATE TABLE IF NOT EXISTS libro (" +
                        "id SERIAL PRIMARY KEY, " +
                        "nombre VARCHAR(100) NOT NULL, " +
                        "autor VARCHAR(100) NOT NULL, " +
                        "editorial VARCHAR(100) NOT NULL, " +
                        "anio INTEGER NOT NULL, " +
                        "armario_id INTEGER REFERENCES armario(id))"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : createTableSQL) {
                stmt.execute(sql);
            }
            System.out.println("Tablas creadas");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
