package org.example;

import java.sql.*;
import java.util.List;

class Libro {
    private String nombre;
    private String autor;
    private String editorial;
    private int anio;

    public Libro(String nombre, String autor, String editorial, int anio) {
        this.nombre = nombre;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void guardarEnBD(Connection conn, String codigo) throws SQLException {
        String sql1 = "SELECT id, codigo, tipo FROM armario WHERE codigo = '"+codigo+"'";
        System.out.println(sql1);
        int armarioId = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    armarioId = rs.getInt("id");
                }
            }
        }

        String sql2 = "INSERT INTO libro (nombre, autor, editorial, anio, armario_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, autor);
            pstmt.setString(3, editorial);
            pstmt.setInt(4, anio);
            pstmt.setInt(5, armarioId);
            pstmt.executeUpdate();
        }
    }
}
