package org.example;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

class Biblioteca {
    private String nombre;
    private double tamanio;
    private List<Armario> armarios;

    public Biblioteca(String nombre, double tamanio, Connection conn) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        try {
            this.armarios = obtenerTodosLosArmarios(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void agregarArmario(Armario armario) {
        armarios.add(armario);
    }

    public List<Armario> getArmarios() {
        return armarios;
    }

    public List<Armario> obtenerTodosLosArmarios(Connection conn) throws SQLException {
        List<Armario> todosLosArmarios = new ArrayList<>();

        String sql = "SELECT id, codigo, tipo FROM armario WHERE biblioteca_id = 1";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int armarioId = rs.getInt("id");
                    String codigo = rs.getString("codigo");
                    String tipo = rs.getString("tipo");

                    Armario armario;
                    if (tipo.equals("madera")) {
                        armario = new ArmarioMadera(codigo);
                    } else {
                        armario = new ArmarioMetalico(codigo);
                    }
                    List<Libro> libros = obtenerTodosLosLibros(conn, armarioId);
                    armario.setLibros(libros);
                    todosLosArmarios.add(armario);
                }
            }
        }
        return todosLosArmarios;
    }

    public List<Libro> obtenerTodosLosLibros(Connection conn, int id) throws SQLException {
        List<Libro> todosLosLibros = new ArrayList<>();

        String sql = "SELECT id, nombre, autor, editorial, anio FROM libro WHERE armario_id = "+id;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String autor = rs.getString("autor");
                    String editorial = rs.getString("editorial");
                    int anio = Integer.parseInt(rs.getString("anio"));

                    Libro libro = new Libro(nombre, autor, editorial, anio);
                    todosLosLibros.add(libro);
                }
            }
        }
        return todosLosLibros;
    }

}
