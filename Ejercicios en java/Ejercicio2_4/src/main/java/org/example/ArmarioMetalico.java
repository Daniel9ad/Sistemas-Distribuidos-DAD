package org.example;

import java.sql.*;

class ArmarioMetalico extends Armario {
    public ArmarioMetalico(String codigo) {
        super(codigo);
    }

    @Override
    public void guardarEnBD(Connection conn, int bibliotecaId) throws SQLException {
        String sql = "INSERT INTO armario (codigo, tipo, biblioteca_id) VALUES (?, 'metalico', ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.setInt(2, bibliotecaId);
            pstmt.execute();
        }
    }
}
